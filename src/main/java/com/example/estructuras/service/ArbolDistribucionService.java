package com.example.estructuras.service;

import com.example.estructuras.model.ArbolDistribucion;
import com.example.estructuras.model.NodoDistribucion;
import com.example.estructuras.model.Recurso;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.Mapping.dto.ArbolDistribucionRequestDto;
import com.example.estructuras.Mapping.dto.ArbolDistribucionResponseDto;
import com.example.estructuras.Mapping.dto.NodoDistribucionResponseDto;
import com.example.estructuras.repository.ArbolDistribucionJsonRepository;
import com.example.estructuras.repository.NodoDistribucionJsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArbolDistribucionService {
    @Autowired
    private ArbolDistribucionJsonRepository arbolRepo;

    @Autowired
    private NodoDistribucionJsonRepository nodoRepo;

    @Autowired
    private NodoDistribucionService nodoService;

    /**
     * Crea un nuevo árbol de distribución
     */
    public ArbolDistribucionResponseDto crear(ArbolDistribucionRequestDto request) throws IOException {
        String id = UUID.randomUUID().toString();
        ArbolDistribucion arbol = new ArbolDistribucion(id, request.getNombre(), request.getRaizId());
        arbolRepo.save(arbol);
        return toDto(arbol);
    }

    /**
     * Obtiene todos los árboles de distribución
     */
    public List<ArbolDistribucionResponseDto> getAll() throws IOException {
        List<ArbolDistribucion> arboles = arbolRepo.findAll();
        return arboles.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Obtiene un árbol por ID
     */
    public ArbolDistribucionResponseDto getById(String id) throws IOException {
        ArbolDistribucion arbol = arbolRepo.findById(id).orElse(null);
        return arbol != null ? toDto(arbol) : null;
    }

    /**
     * Actualiza la raíz de un árbol
     */
    public ArbolDistribucionResponseDto actualizarRaiz(String arbolId, String nuevoRaizId) throws IOException {
        ArbolDistribucion arbol = arbolRepo.findById(arbolId).orElse(null);
        if (arbol == null) return null;

        arbol.setRaizId(nuevoRaizId);
        arbolRepo.save(arbol);
        return toDto(arbol);
    }

    /**
     * Elimina un árbol
     */
    public boolean eliminar(String id) throws IOException {
        return arbolRepo.deleteById(id);
    }

    /**
     * Asigna recursos usando el árbol de distribución (lógica que estaba en el modelo)
     */
    public Map<TipoRecurso, Integer> asignarRecursos(String arbolId, List<Recurso> recursosDisponibles) throws IOException {
        ArbolDistribucion arbol = arbolRepo.findById(arbolId).orElse(null);
        if (arbol == null || arbol.getRaizId() == null) {
            System.out.println("Árbol vacío o sin raíz.");
            return new HashMap<>();
        }

        System.out.println("=== Inicio asignación por Árbol de distribución ===");
        Map<TipoRecurso, Integer> resultado = nodoService.asignarRecursosDisponibles(
            arbol.getRaizId(),
            recursosDisponibles
        );
        System.out.println("=== Fin asignación por Árbol ===");

        return resultado;
    }

    /**
     * Obtiene la estructura jerárquica del árbol (para visualización)
     */
    public Map<String, Object> obtenerEstructura(String arbolId) throws IOException {
        ArbolDistribucion arbol = arbolRepo.findById(arbolId).orElse(null);
        if (arbol == null) return null;

        Map<String, Object> estructura = new HashMap<>();
        estructura.put("id", arbol.getId());
        estructura.put("nombre", arbol.getNombre());

        if (arbol.getRaizId() != null) {
            NodoDistribucionResponseDto raiz = nodoService.getByUbicacionId(arbol.getRaizId());
            estructura.put("raiz", raiz);
        }

        return estructura;
    }

    /**
     * Imprime la estructura del árbol (para logging/debug)
     */
    public String imprimirEstructura(String arbolId) throws IOException {
        ArbolDistribucion arbol = arbolRepo.findById(arbolId).orElse(null);
        if (arbol == null || arbol.getRaizId() == null) {
            return "Árbol vacío o sin raíz.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Estructura del Árbol: ").append(arbol.getNombre()).append(" ===\n");

        NodoDistribucionResponseDto raiz = nodoService.getByUbicacionId(arbol.getRaizId());
        if (raiz != null) {
            imprimirNodoRecursivo(raiz, 0, sb);
        }

        return sb.toString();
    }

    // Métodos auxiliares
    private void imprimirNodoRecursivo(NodoDistribucionResponseDto nodo, int nivel, StringBuilder sb) {
        if (nodo == null) return;

        String prefijo = "  ".repeat(Math.max(0, nivel));
        sb.append(prefijo)
          .append("- ")
          .append(nodo.getUbicacionNombre())
          .append(" necesidades: ")
          .append(nodo.getNecesidades())
          .append("\n");

        if (nodo.getHijos() != null) {
            for (NodoDistribucionResponseDto hijo : nodo.getHijos()) {
                imprimirNodoRecursivo(hijo, nivel + 1, sb);
            }
        }
    }

    private ArbolDistribucionResponseDto toDto(ArbolDistribucion arbol) {
        ArbolDistribucionResponseDto dto = new ArbolDistribucionResponseDto();
        dto.setId(arbol.getId());
        dto.setNombre(arbol.getNombre());

        if (arbol.getRaizId() != null) {
            NodoDistribucionResponseDto raiz = nodoService.getByUbicacionId(arbol.getRaizId());
            dto.setRaiz(raiz);
        }

        return dto;
    }
}

