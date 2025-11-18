package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.*;
import com.example.estructuras.model.*;
import com.example.estructuras.repository.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones del rol Administrador.
 * Toda la lógica de negocio está aquí, no en el modelo Administrador.
 */
@Service
public class AdministradorService {
    private final GrafoJsonRepository grafoJsonRepository;
    private final UbicacionJsonRepository ubicacionJsonRepository;
    private final RutaJsonRepository rutaJsonRepository;
    private final DesastreJsonRepository desastreJsonRepository;
    private final EquipoJsonRepository equipoJsonRepository;
    private final RecursoJsonRepository recursoJsonRepository;

    public AdministradorService(GrafoJsonRepository grafoJsonRepository,
                                UbicacionJsonRepository ubicacionJsonRepository,
                                RutaJsonRepository rutaJsonRepository,
                                DesastreJsonRepository desastreJsonRepository,
                                EquipoJsonRepository equipoJsonRepository,
                                RecursoJsonRepository recursoJsonRepository) {
        this.grafoJsonRepository = grafoJsonRepository;
        this.ubicacionJsonRepository = ubicacionJsonRepository;
        this.rutaJsonRepository = rutaJsonRepository;
        this.desastreJsonRepository = desastreJsonRepository;
        this.equipoJsonRepository = equipoJsonRepository;
        this.recursoJsonRepository = recursoJsonRepository;
    }

    /**
     * Define la ruta óptima entre dos ubicaciones y la persiste si no existe.
     */
    public RutaDetalleResponseDto definirRuta(DefinirRutaRequestDto dto) throws IOException {
        Optional<Ubicacion> origenOpt = ubicacionJsonRepository.findById(dto.getOrigenId());
        Optional<Ubicacion> destinoOpt = ubicacionJsonRepository.findById(dto.getDestinoId());
        if (origenOpt.isEmpty() || destinoOpt.isEmpty()) {
            return RutaDetalleResponseDto.error("Origen o destino no encontrado");
        }
        Ubicacion origen = origenOpt.get();
        Ubicacion destino = destinoOpt.get();
        GrafoNoDirigido grafo = grafoJsonRepository.cargarGrafo();
        // Calcular ruta óptima
        Map<Ubicacion, Float> distancias = Dijkstra.calcularDistancias(grafo, origen);
        Float distanciaMasCorta = distancias.get(destino);
        if (distanciaMasCorta == null || distanciaMasCorta.isInfinite()) {
            return RutaDetalleResponseDto.error("No existe una ruta entre las ubicaciones indicadas");
        }
        Ruta ruta = new Ruta(origen, destino, distanciaMasCorta);
        // Persistir si no existe
        boolean existe = rutaJsonRepository.findAll().stream().anyMatch(r ->
                r.getOrigen().getId().equals(origen.getId()) &&
                        r.getDestino().getId().equals(destino.getId()) &&
                        r.getDistancia() == ruta.getDistancia());
        if (!existe) {
            rutaJsonRepository.add(ruta);
        }
        // Camino y peso
        List<Ubicacion> camino = Dijkstra.caminoMasCorto(grafo, origen, destino);
        List<String> caminoNombres = camino == null ? new ArrayList<>() : camino.stream().map(Ubicacion::getNombre).collect(Collectors.toList());
        int peso = ruta.calcularPeso();
        return RutaDetalleResponseDto.ok(origen, destino, ruta.getDistancia(), peso, caminoNombres, "Ruta definida correctamente");
    }

    /**
     * Asigna un equipo a un desastre si hay personal y ruta disponible.
     */
    public AsignarEquipoResponseDto asignarEquipo(AsignarEquipoRequestDto dto) throws IOException {
        Optional<Desastre> desastreOpt = desastreJsonRepository.findById(dto.getDesastreId());
        Optional<Equipo> equipoOpt = equipoJsonRepository.findById(dto.getEquipoId());
        AsignarEquipoResponseDto res = new AsignarEquipoResponseDto();
        if (desastreOpt.isEmpty() || equipoOpt.isEmpty()) {
            res.setExito(false);
            res.setMensaje("Desastre o equipo no encontrado");
            return res;
        }
        Desastre desastre = desastreOpt.get();
        Equipo equipo = equipoOpt.get();
        String prioridad = desastre.asignarPrioridad();
        int personalRequerido = switch (prioridad) {
            case "Alta" -> 20;
            case "Media" -> 10;
            case "Baja" -> 5;
            default -> 0;
        };
        GrafoNoDirigido grafo = grafoJsonRepository.cargarGrafo();
        // Calcular ruta y distancia
        Ubicacion origen = equipo.getUbicacion();
        Ubicacion destino = desastre.getUbicacion();
        List<Ubicacion> camino = Dijkstra.caminoMasCorto(grafo, origen, destino);
        Map<Ubicacion, Float> distancias = Dijkstra.calcularDistancias(grafo, origen);
        Float distanciaTotal = distancias.get(destino);
        if (camino == null || camino.isEmpty() || distanciaTotal == null || distanciaTotal.isInfinite()) {
            res.setExito(false);
            res.setMensaje("No existe ruta disponible para llegar al desastre");
            res.setPrioridadDesastre(prioridad);
            res.setPersonalRequerido(personalRequerido);
            return res;
        }
        // Verificar disponibilidad de personal
        if (equipo.getIntegrantesDisponibles() < personalRequerido) {
            res.setExito(false);
            res.setMensaje("Equipo sin personal suficiente: requiere " + personalRequerido + ", disponibles " + equipo.getIntegrantesDisponibles());
            res.setPrioridadDesastre(prioridad);
            res.setPersonalRequerido(personalRequerido);
            res.setDistanciaRuta(distanciaTotal);
            res.setCamino(camino.stream().map(Ubicacion::getNombre).collect(Collectors.toList()));
            return res;
        }
        // Asignar
        int antes = equipo.getIntegrantesDisponibles();
        equipo.setIntegrantesDisponibles(antes - personalRequerido);
        if (desastre.getEquiposAsignados() == null) {
            desastre.setEquiposAsignados(new ArrayList<>());
        }
        desastre.getEquiposAsignados().add(equipo);
        actualizarEquipo(equipo);
        actualizarDesastre(desastre);
        res.setExito(true);
        res.setMensaje("Equipo asignado correctamente");
        res.setIntegrantesDesplegados(personalRequerido);
        res.setIntegrantesRestantes(equipo.getIntegrantesDisponibles());
        float velocidadPromedioKmH = 40f;
        float tiempoEstimadoMin = (distanciaTotal / velocidadPromedioKmH) * 60f;
        res.setPrioridadDesastre(prioridad);
        res.setPersonalRequerido(personalRequerido);
        res.setDistanciaRuta(distanciaTotal);
        res.setTiempoEstimadoMinutos(tiempoEstimadoMin);
        res.setCamino(camino.stream().map(Ubicacion::getNombre).collect(Collectors.toList()));
        return res;
    }

    private void actualizarEquipo(Equipo equipo) throws IOException {
        List<Equipo> lista = equipoJsonRepository.findAll();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdEquipo().equals(equipo.getIdEquipo())) {
                lista.set(i, equipo);
                break;
            }
        }
        equipoJsonRepository.saveAll(lista);
    }

    private void actualizarDesastre(Desastre desastre) throws IOException {
        List<Desastre> lista = desastreJsonRepository.findAll();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdDesastre().equals(desastre.getIdDesastre())) {
                lista.set(i, desastre);
                break;
            }
        }
        desastreJsonRepository.saveAll(lista);
    }

    /**
     * Asigna recursos a desastres según prioridad usando una cola de prioridad.
     */
    public List<AsignacionRecursoDto> asignarRecursosPrioridad() throws IOException {
        List<Desastre> desastres = desastreJsonRepository.findAll();
        List<Recurso> recursosOriginales = recursoJsonRepository.findAll();
        List<AsignacionRecursoDto> asignaciones = new ArrayList<>();
        if (desastres.isEmpty() || recursosOriginales.isEmpty()) {
            return asignaciones;
        }
        ColaPrioridad<Desastre> cola = new ColaPrioridad<>();
        desastres.forEach(cola::encolar);
        // Usar una copia de recursos para cada desastre
        while (!cola.estaVacia()) {
            Desastre desastre = cola.atenderSiguiente();
            if (desastre == null) break;
            AsignacionRecursoDto asignacion = new AsignacionRecursoDto();
            asignacion.setDesastre(desastre.getNombre());
            List<RecursoAsignadoDto> recursosAsignados = new ArrayList<>();
            for (Recurso recursoOriginal : recursosOriginales) {
                // Usar la cantidad actual del recurso
                int cantidadDisponible = recursoOriginal.getCantidad();
                int cantidadNecesaria = 0;
                if (recursoOriginal.getTipo() == TipoRecurso.ALIMENTO) {
                    cantidadNecesaria = desastre.getPersonasAfectadas() * 3;
                } else if (recursoOriginal.getTipo() == TipoRecurso.MEDICAMENTO) {
                    cantidadNecesaria = desastre.getPersonasAfectadas();
                }
                int cantidadAsignada = 0;
                if (cantidadNecesaria > 0) {
                    if (cantidadDisponible >= cantidadNecesaria) {
                        cantidadAsignada = cantidadNecesaria;
                        recursoOriginal.setCantidad(cantidadDisponible - cantidadNecesaria);
                    } else if (cantidadDisponible > 0) {
                        cantidadAsignada = cantidadDisponible;
                        recursoOriginal.setCantidad(0);
                    }
                }
                if (cantidadAsignada > 0) {
                    RecursoAsignadoDto recursoDto = new RecursoAsignadoDto();
                    recursoDto.setTipo(recursoOriginal.getTipo().toString());
                    recursoDto.setCantidad(cantidadAsignada);
                    recursosAsignados.add(recursoDto);
                }
            }
            asignacion.setRecursos(recursosAsignados);
            asignaciones.add(asignacion);
        }
        recursoJsonRepository.saveAll(recursosOriginales);
        return asignaciones;
    }

    /**
     * Genera un reporte de desastres atendidos en la carpeta Downloads.
     */
    public OperacionSimpleResponseDto generarReporte() throws IOException {
        List<Desastre> desastres = desastreJsonRepository.findAll();
        String ruta = System.getProperty("user.home") + java.io.File.separator + "Downloads" + java.io.File.separator + "reporte_desastres.txt";
        java.io.File archivo = new java.io.File(ruta);
        try (java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(
                archivo.toPath(),
                java.nio.charset.StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("═══════════════════════════════════════════════���═══════════════\n");
            writer.write("                  REPORTE DE DESASTRES ATENDIDOS\n");
            writer.write("═══════════════════════════════���═══════════════════════════════\n\n");
            if (desastres == null || desastres.isEmpty()) {
                writer.write("No se han registrado desastres atendidos.\n");
            } else {
                for (Desastre d : desastres) {
                    int equiposAsignados = d.getEquiposAsignados() != null ? d.getEquiposAsignados().size() : 0;
                    String prioridad = d.asignarPrioridad();
                    writer.write(String.format("• Desastre: %s\n", d.getNombre()));
                    writer.write(String.format("  - ID: %s\n", d.getIdDesastre()));
                    writer.write(String.format("  - Tipo: %s\n", d.getTipoDesastre()));
                    writer.write(String.format("  - Ubicación: %s\n", d.getUbicacion().getNombre()));
                    writer.write(String.format("  - Fecha: %s\n", d.getFecha()));
                    writer.write(String.format("  - Personas afectadas: %d\n", d.getPersonasAfectadas()));
                    writer.write(String.format("  - Prioridad: %s\n", prioridad));
                    writer.write(String.format("  - Equipos asignados: %d\n", equiposAsignados));
                    writer.write("---------------------------------------------------------------\n");
                }
            }
            writer.write("\nGenerado por el sistema de gestión de desastres.\n");
            writer.write("Fecha de generación: " + java.time.LocalDate.now() + "\n");
        }
        OperacionSimpleResponseDto res = new OperacionSimpleResponseDto();
        res.setExito(true);
        res.setMensaje("Reporte generado en carpeta Downloads");
        return res;
    }
}
