package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.GrafoRequestDto;
import com.example.estructuras.Mapping.dto.GrafoResponseDto;
import com.example.estructuras.Mapping.dto.RutaResponseDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.GrafoNoDirigido;
import com.example.estructuras.model.Ruta;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.GrafoJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrafoNoDirigidoService {
    private final GrafoJsonRepository grafoJsonRepository;
    private final RutaService rutaService;

    public GrafoNoDirigidoService(GrafoJsonRepository grafoJsonRepository, RutaService rutaService) {
        this.grafoJsonRepository = grafoJsonRepository;
        this.rutaService = rutaService;
    }

    public GrafoResponseDto obtenerGrafo() throws IOException {
        GrafoNoDirigido grafo = grafoJsonRepository.cargarGrafo();

        Map<String, List<RutaResponseDto>> adyacenciasLegible = new LinkedHashMap<>();

        for (Ubicacion origen : grafo.obtenerUbicaciones()) {
            UbicacionResponseDto origenDto = mapearUbicacion(origen);

            List<RutaResponseDto> rutasDto = grafo.obtenerRutasDesde(origen).stream()
                    .map(this::mapearRutaConPeso)
                    .collect(Collectors.toList());

            // Solo incluir nodos que tengan rutas, si quieres
            if (!rutasDto.isEmpty()) {
                String clave = origenDto.getNombre() + " (" + origenDto.getId() + ")";
                adyacenciasLegible.put(clave, rutasDto);
            }
        }

        // Devolver un DTO con claves más legibles
        return new GrafoResponseDto(adyacenciasLegible);
    }

    private UbicacionResponseDto mapearUbicacion(Ubicacion ubicacion) {
        return new UbicacionResponseDto(
                ubicacion.getId(),
                ubicacion.getNombre(),
                ubicacion.getCalle(),
                ubicacion.getCarrera(),
                ubicacion.getTipoUbicacion(),
                ubicacion.getLat(),   // ← NUEVO
                ubicacion.getLng()    // ← NUEVO
        );
    }

    private RutaResponseDto mapearRutaConPeso(Ruta ruta) {
        int peso = rutaService.calcularPeso(ruta.getDistancia());
        return new RutaResponseDto(
                mapearUbicacion(ruta.getOrigen()),
                mapearUbicacion(ruta.getDestino()),
                ruta.getDistancia(),
                peso
        );
    }




}
