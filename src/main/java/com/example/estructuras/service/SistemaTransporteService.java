package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.TransporteRecursosRequestDto;
import com.example.estructuras.Mapping.dto.TransporteRecursosResponseDto;
import com.example.estructuras.model.*;
import com.example.estructuras.repository.UbicacionJsonRepository;
import com.example.estructuras.repository.GrafoJsonRepository;
import com.example.estructuras.repository.MapaRecursosJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SistemaTransporteService {
    private final UbicacionJsonRepository ubicacionRepo;
    private final GrafoJsonRepository grafoRepo;
    private final MapaRecursosJsonRepository mapaRecursosRepo;
    private final MapaRecursosService mapaRecursosService;

    public SistemaTransporteService(UbicacionJsonRepository ubicacionRepo,
                                    GrafoJsonRepository grafoRepo,
                                    MapaRecursosJsonRepository mapaRecursosRepo,
                                    MapaRecursosService mapaRecursosService) {
        this.ubicacionRepo = ubicacionRepo;
        this.grafoRepo = grafoRepo;
        this.mapaRecursosRepo = mapaRecursosRepo;
        this.mapaRecursosService = mapaRecursosService;
    }

    // Transportar recursos siguiendo la ruta más corta
    public TransporteRecursosResponseDto transportarRecursos(TransporteRecursosRequestDto dto) throws IOException {
        Ubicacion origen = ubicacionRepo.findById(dto.getOrigenId()).orElse(null);
        Ubicacion destino = ubicacionRepo.findById(dto.getDestinoId()).orElse(null);
        GrafoNoDirigido grafo = grafoRepo.cargarGrafo();
        TransporteRecursosResponseDto response = new TransporteRecursosResponseDto();
        if (origen == null || destino == null) {
            response.setExito(false);
            response.setMensaje("Ubicación de origen o destino no encontrada");
            return response;
        }
        List<Ubicacion> ruta = grafo.calcularRutaMasCorta(origen, destino);
        if (ruta == null || ruta.size() < 2) {
            response.setExito(false);
            response.setMensaje("No existe ruta entre " + origen.getNombre() + " y " + destino.getNombre());
            return response;
        }
        boolean exito = mapaRecursosService.transferirRecurso(origen.getId(), destino.getId(), dto.getTipo(), dto.getCantidad());
        response.setExito(exito);
        response.setRuta(ruta.stream().map(Ubicacion::getNombre).collect(Collectors.toList()));
        response.setMensaje(exito ? "Transporte realizado correctamente" : "No se pudo transferir el recurso");
        return response;
    }

    // Obtener la ruta más corta entre dos ubicaciones
    public List<String> obtenerRutaMasCorta(String origenId, String destinoId) throws IOException {
        Ubicacion origen = ubicacionRepo.findById(origenId).orElse(null);
        Ubicacion destino = ubicacionRepo.findById(destinoId).orElse(null);
        GrafoNoDirigido grafo = grafoRepo.cargarGrafo();
        if (origen == null || destino == null) return List.of();
        List<Ubicacion> ruta = grafo.calcularRutaMasCorta(origen, destino);
        if (ruta == null) return List.of();
        return ruta.stream().map(Ubicacion::getNombre).collect(Collectors.toList());
    }

    // Calcula las distancias desde una ubicación a todas las demás
    public java.util.Map<String, Float> calcularDistanciasDesde(String origenId) throws IOException {
        Ubicacion origen = ubicacionRepo.findById(origenId).orElse(null);
        GrafoNoDirigido grafo = grafoRepo.cargarGrafo();
        java.util.Map<String, Float> resultado = new java.util.HashMap<>();
        if (origen == null) return resultado;
        java.util.Map<Ubicacion, Float> distancias = Dijkstra.calcularDistancias(grafo, origen);
        for (java.util.Map.Entry<Ubicacion, Float> entry : distancias.entrySet()) {
            resultado.put(entry.getKey().getId(), entry.getValue());
        }
        return resultado;
    }

}
