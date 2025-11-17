package com.example.estructuras.service;

import com.example.estructuras.model.NodoDistribucion;
import com.example.estructuras.model.Recurso;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.Mapping.dto.NodoDistribucionRequestDto;
import com.example.estructuras.Mapping.dto.NodoDistribucionResponseDto;
import com.example.estructuras.repository.NodoDistribucionJsonRepository;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NodoDistribucionService {
    @Autowired
    private NodoDistribucionJsonRepository nodoRepo;
    @Autowired
    private UbicacionJsonRepository ubicacionRepo;

    public List<NodoDistribucionResponseDto> getAll() {
        List<NodoDistribucion> nodos = findAll();
        return nodos.stream().map(this::toDto).collect(Collectors.toList());
    }

    public NodoDistribucionResponseDto getByUbicacionId(String ubicacionId) {
        NodoDistribucion nodo = findAll().stream()
            .filter(n -> n.getUbicacionId().equals(ubicacionId))
            .findFirst()
            .orElse(null);
        return nodo != null ? toDto(nodo) : null;
    }

    public NodoDistribucionResponseDto create(NodoDistribucionRequestDto request) {
        Ubicacion ubicacion = getUbicacion(request.getUbicacionId());
        if (ubicacion == null) return null;

        String id = UUID.randomUUID().toString();
        NodoDistribucion nodo = new NodoDistribucion(id, request.getUbicacionId());

        if (request.getNecesidades() != null) {
            nodo.setNecesidades(request.getNecesidades());
        }

        List<NodoDistribucion> nodos = findAll();
        nodos.add(nodo);

        if (request.getHijosIds() != null && !request.getHijosIds().isEmpty()) {
            nodo.setHijosIds(request.getHijosIds());
        }

        saveAll(nodos);
        return toDto(nodo);
    }

    public boolean deleteByUbicacionId(String ubicacionId) {
        List<NodoDistribucion> nodos = findAll();
        boolean removed = nodos.removeIf(n -> n.getUbicacionId().equals(ubicacionId));
        if (removed) saveAll(nodos);
        return removed;
    }

    public NodoDistribucionResponseDto updateByUbicacionId(String ubicacionId, NodoDistribucionRequestDto request) {
        List<NodoDistribucion> nodos = findAll();
        NodoDistribucion nodo = nodos.stream()
            .filter(n -> n.getUbicacionId().equals(ubicacionId))
            .findFirst()
            .orElse(null);

        if (nodo == null) return null;

        if (request.getNecesidades() != null) {
            nodo.setNecesidades(request.getNecesidades());
        }

        if (request.getHijosIds() != null) {
            nodo.setHijosIds(request.getHijosIds());
        }

        saveAll(nodos);
        return toDto(nodo);
    }

    public NodoDistribucionResponseDto agregarNecesidad(String ubicacionId, TipoRecurso tipo, int cantidad) {
        List<NodoDistribucion> nodos = findAll();
        NodoDistribucion nodo = nodos.stream()
            .filter(n -> n.getUbicacionId().equals(ubicacionId))
            .findFirst()
            .orElse(null);

        if (nodo == null) return null;

        Map<String, Integer> necesidades = nodo.getNecesidades();
        String tipoStr = tipo.name();
        necesidades.put(tipoStr, necesidades.getOrDefault(tipoStr, 0) + cantidad);

        saveAll(nodos);
        return toDto(nodo);
    }

    public NodoDistribucionResponseDto agregarHijo(String ubicacionPadreId, String ubicacionHijoId) {
        List<NodoDistribucion> nodos = findAll();
        NodoDistribucion padre = nodos.stream()
            .filter(n -> n.getUbicacionId().equals(ubicacionPadreId))
            .findFirst()
            .orElse(null);

        NodoDistribucion hijo = nodos.stream()
            .filter(n -> n.getUbicacionId().equals(ubicacionHijoId))
            .findFirst()
            .orElse(null);

        if (padre == null || hijo == null) return null;

        if (!padre.getHijosIds().contains(hijo.getId())) {
            padre.getHijosIds().add(hijo.getId());
        }

        saveAll(nodos);
        return toDto(padre);
    }

    public Map<TipoRecurso, Integer> asignarRecursosDisponibles(String ubicacionId, List<Recurso> recursosDisponibles) {
        List<NodoDistribucion> nodos = findAll();
        NodoDistribucion nodo = nodos.stream()
            .filter(n -> n.getUbicacionId().equals(ubicacionId))
            .findFirst()
            .orElse(null);

        if (nodo == null) return new HashMap<>();

        Map<TipoRecurso, Integer> resultado = asignarRecursosRecursivo(nodo, recursosDisponibles, nodos);
        saveAll(nodos);
        return resultado;
    }

    private Map<TipoRecurso, Integer> asignarRecursosRecursivo(NodoDistribucion nodo, List<Recurso> recursosDisponibles, List<NodoDistribucion> todosNodos) {
        for (String hijoId : nodo.getHijosIds()) {
            NodoDistribucion hijo = todosNodos.stream()
                .filter(n -> n.getId().equals(hijoId))
                .findFirst()
                .orElse(null);

            if (hijo != null) {
                asignarRecursosRecursivo(hijo, recursosDisponibles, todosNodos);
            }
        }

        Map<TipoRecurso, Integer> asignadoNodo = new HashMap<>();

        Map<String, Integer> necesidades = new HashMap<>(nodo.getNecesidades());
        for (Map.Entry<String, Integer> entry : necesidades.entrySet()) {
            TipoRecurso tipo = TipoRecurso.valueOf(entry.getKey());
            int faltante = entry.getValue();
            if (faltante <= 0) continue;

            for (Recurso recurso : recursosDisponibles) {
                if (recurso.getTipo() == tipo && faltante > 0) {
                    int disponible = recurso.getCantidad();
                    if (disponible <= 0) continue;
                    int aConsumir = Math.min(disponible, faltante);
                    recurso.setCantidad(recurso.getCantidad() - aConsumir);
                    faltante -= aConsumir;
                    asignadoNodo.put(tipo, asignadoNodo.getOrDefault(tipo, 0) + aConsumir);
                }
            }
            nodo.getNecesidades().put(entry.getKey(), faltante);
        }

        System.out.println("Nodo " + nodo.getUbicacionId() + " - asignado: " + asignadoNodo);
        return asignadoNodo;
    }

    private List<NodoDistribucion> findAll() {
        try {
            return nodoRepo.findAll();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void saveAll(List<NodoDistribucion> nodos) {
        try {
            nodoRepo.saveAll(nodos);
        } catch (IOException e) {
            // log error
        }
    }

    private Ubicacion getUbicacion(String id) {
        try {
            return ubicacionRepo.findById(id).orElse(null);
        } catch (IOException e) {
            return null;
        }
    }

    private NodoDistribucionResponseDto toDto(NodoDistribucion nodo) {
        NodoDistribucionResponseDto dto = new NodoDistribucionResponseDto();

        Ubicacion ubicacion = getUbicacion(nodo.getUbicacionId());
        dto.setUbicacionId(nodo.getUbicacionId());
        dto.setUbicacionNombre(ubicacion != null ? ubicacion.getNombre() : "Desconocido");
        dto.setNecesidades(nodo.getNecesidades());

        List<NodoDistribucionResponseDto> hijosDto = new ArrayList<>();
        List<NodoDistribucion> todosNodos = findAll();

        for (String hijoId : nodo.getHijosIds()) {
            NodoDistribucion hijo = todosNodos.stream()
                .filter(n -> n.getId().equals(hijoId))
                .findFirst()
                .orElse(null);

            if (hijo != null) {
                hijosDto.add(toDto(hijo));
            }
        }

        dto.setHijos(hijosDto);
        return dto;
    }
}
