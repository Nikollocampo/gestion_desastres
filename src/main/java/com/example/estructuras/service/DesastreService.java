package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.DesastreResponseDto;
import com.example.estructuras.Mapping.dto.EquipoResponseDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.ColaPrioridad;
import com.example.estructuras.model.Desastre;
import com.example.estructuras.model.Equipo;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.DesastreJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesastreService {
    private final DesastreJsonRepository desastreRepository;

    public DesastreService(DesastreJsonRepository desastreRepository) {
        this.desastreRepository = desastreRepository;
    }

    // Obtener todos los desastres en formato DTO
    public List<DesastreResponseDto> obtenerTodos() throws IOException {
        List<Desastre> desastres = desastreRepository.findAll();
        return desastres.stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    // Buscar un desastre por ID
    public DesastreResponseDto obtenerPorId(String idDesastre) throws IOException {
        return desastreRepository.findById(idDesastre)
                .map(this::convertirADto)
                .orElse(null);
    }

    // Agregar un nuevo desastre
    public void agregarDesastre(Desastre desastre) throws IOException {
        desastreRepository.add(desastre);
    }

    // Eliminar un desastre por ID
    public boolean eliminarDesastre(String idDesastre) throws IOException {
        return desastreRepository.deleteById(idDesastre);
    }

    // Conversión de entidad a DTO
    private DesastreResponseDto convertirADto(Desastre desastre) {
        Ubicacion ubicacion = desastre.getUbicacion();
        UbicacionResponseDto ubicacionDto = new UbicacionResponseDto(
                ubicacion.getId(),
                ubicacion.getNombre(),
                ubicacion.getCalle(),
                ubicacion.getCarrera(),
                ubicacion.getTipoUbicacion()
        );

        List<EquipoResponseDto> equiposDto = desastre.getEquiposAsignados().stream()
                .map(e -> new EquipoResponseDto(
                        e.getIdEquipo(),
                        e.getIntegrantesDisponibles(),
                        e.getTipoEquipo(),
                        new UbicacionResponseDto(
                                e.getUbicacion().getId(),
                                e.getUbicacion().getNombre(),
                                e.getUbicacion().getCalle(),
                                e.getUbicacion().getCarrera(),
                                e.getUbicacion().getTipoUbicacion()
                        )
                ))
                .collect(Collectors.toList());
        return new DesastreResponseDto(
                desastre.getIdDesastre(),
                desastre.getNombre(),
                desastre.getTipoDesastre(),
                desastre.getMagnitud(),
                desastre.getPersonasAfectadas(),
                desastre.getFecha(),
                ubicacionDto,
                equiposDto,
                desastre.asignarPrioridad(),
                desastre.asignarNivelPrioridad()
        );
    }
    public List<EquipoResponseDto> obtenerEquiposAsignados(Desastre desastre) {
        if (desastre.getEquiposAsignados() == null || desastre.getEquiposAsignados().isEmpty()) {
            return List.of(); // lista vacía si no hay equipos
        }

        return desastre.getEquiposAsignados().stream()
                .map(e -> new EquipoResponseDto(
                        e.getIdEquipo(),
                        e.getIntegrantesDisponibles(),
                        e.getTipoEquipo(),
                        new UbicacionResponseDto(
                                e.getUbicacion().getId(),
                                e.getUbicacion().getNombre(),
                                e.getUbicacion().getCalle(),
                                e.getUbicacion().getCarrera(),
                                e.getUbicacion().getTipoUbicacion()
                        )
                ))
                .collect(Collectors.toList());
    }

    // ==================== MÉTODOS CON COLA DE PRIORIDAD ====================

    /**
     * Obtiene todos los desastres ordenados por prioridad (de mayor a menor).
     * Usa ColaPrioridad internamente para ordenar.
     */
    public List<DesastreResponseDto> obtenerDesastresPorPrioridad() throws IOException {
        List<Desastre> desastres = desastreRepository.findAll();
        Comparator<Desastre> comparadorPrioridad = Comparator.comparingInt(Desastre::asignarNivelPrioridad);
        ColaPrioridad<Desastre> cola = new ColaPrioridad<>(comparadorPrioridad);
        cola.encolarTodos(desastres);
        List<DesastreResponseDto> resultado = new ArrayList<>();
        while (!cola.estaVacia()) {
            resultado.add(convertirADto(cola.atenderSiguiente()));
        }
        return resultado;
    }

    /**
     * Obtiene el desastre más urgente (mayor prioridad) sin eliminarlo.
     */
    public DesastreResponseDto obtenerDesastreMasUrgente() throws IOException {
        List<Desastre> desastres = desastreRepository.findAll();
        if (desastres.isEmpty()) return null;
        Comparator<Desastre> comparadorPrioridad = Comparator.comparingInt(Desastre::asignarNivelPrioridad);
        ColaPrioridad<Desastre> cola = new ColaPrioridad<>(comparadorPrioridad);
        cola.encolarTodos(desastres);
        Desastre masUrgente = cola.verSiguiente();
        return masUrgente != null ? convertirADto(masUrgente) : null;
    }

    /**
     * Obtiene los N desastres más urgentes.
     */
    public List<DesastreResponseDto> obtenerTopDesastresUrgentes(int cantidad) throws IOException {
        List<Desastre> desastres = desastreRepository.findAll();
        Comparator<Desastre> comparadorPrioridad = Comparator.comparingInt(Desastre::asignarNivelPrioridad);
        ColaPrioridad<Desastre> cola = new ColaPrioridad<>(comparadorPrioridad);
        cola.encolarTodos(desastres);
        List<DesastreResponseDto> resultado = new ArrayList<>();
        int count = 0;
        while (!cola.estaVacia() && count < cantidad) {
            resultado.add(convertirADto(cola.atenderSiguiente()));
            count++;
        }
        return resultado;
    }

    /**
     * Obtiene desastres agrupados por nivel de prioridad.
     * @param nivelPrioridad "Alta", "Media" o "Baja"
     */
    public List<DesastreResponseDto> obtenerDesastresPorNivelPrioridad(String nivelPrioridad) throws IOException {
        List<Desastre> desastres = desastreRepository.findAll();
        return desastres.stream()
                .filter(d -> d.asignarPrioridad().equalsIgnoreCase(nivelPrioridad))
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

}
