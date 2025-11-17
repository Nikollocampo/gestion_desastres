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
    public OperacionSimpleResponseDto asignarRecursosPrioridad() throws IOException {
        List<Desastre> desastres = desastreJsonRepository.findAll();
        List<Recurso> recursos = recursoJsonRepository.findAll();
        OperacionSimpleResponseDto res = new OperacionSimpleResponseDto();
        if (desastres.isEmpty() || recursos.isEmpty()) {
            res.setExito(false);
            res.setMensaje("No hay desastres o recursos disponibles");
            return res;
        }
        ColaPrioridad<Desastre> cola = new ColaPrioridad<>();
        desastres.forEach(cola::encolar);
        boolean huboAsignacion = false;
        while (!cola.estaVacia()) {
            Desastre desastre = cola.atenderSiguiente();
            if (desastre == null) break;
            for (Recurso recurso : recursos) {
                int cantidadNecesaria = switch (recurso.getTipo()) {
                    case ALIMENTO -> desastre.getPersonasAfectadas() * 3;
                    case MEDICAMENTO -> desastre.getPersonasAfectadas();
                    default -> 0;
                };
                if (cantidadNecesaria > 0) {
                    if (recurso.getCantidad() >= cantidadNecesaria) {
                        recurso.setCantidad(recurso.getCantidad() - cantidadNecesaria);
                        huboAsignacion = true;
                    } else if (recurso.getCantidad() > 0) {
                        int disponible = recurso.getCantidad();
                        recurso.setCantidad(0);
                        huboAsignacion = true;
                    }
                }
            }
        }
        recursoJsonRepository.saveAll(recursos);
        res.setExito(huboAsignacion);
        res.setMensaje(huboAsignacion ? "Asignación de recursos procesada" : "No se pudo asignar recursos");
        return res;
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
            writer.write("═══════════════════════════════════════════════════════════════\n");
            writer.write("                  REPORTE DE DESASTRES ATENDIDOS\n");
            writer.write("═══════════════════════════════════════════════════════════════\n\n");
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

