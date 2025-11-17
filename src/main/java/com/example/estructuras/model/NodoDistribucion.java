package com.example.estructuras.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa un nodo dentro del árbol de distribución de recursos.
 * Se persiste en JSON, por lo que se usan tipos simples:
 * - necesidades: clave TipoRecurso como String, valor cantidad faltante.
 * - hijosIds: lista de ids (String) de otros nodos.
 */
public class NodoDistribucion {
    private String id;
    private String ubicacionId; // Referencia a Ubicacion por su id
    private Map<String, Integer> necesidades; // TipoRecurso.name() -> cantidad
    private List<String> hijosIds; // IDs de los hijos

    // Constructor vacío para Jackson
    public NodoDistribucion() {
        this.necesidades = new HashMap<>();
        this.hijosIds = new ArrayList<>();
    }

    public NodoDistribucion(String id, String ubicacionId) {
        this.id = id;
        this.ubicacionId = ubicacionId;
        this.necesidades = new HashMap<>();
        this.hijosIds = new ArrayList<>();
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUbicacionId() { return ubicacionId; }
    public void setUbicacionId(String ubicacionId) { this.ubicacionId = ubicacionId; }

    public Map<String, Integer> getNecesidades() { return necesidades; }
    public void setNecesidades(Map<String, Integer> necesidades) {
        this.necesidades = (necesidades != null) ? necesidades : new HashMap<>();
    }

    public List<String> getHijosIds() { return hijosIds; }
    public void setHijosIds(List<String> hijosIds) {
        this.hijosIds = (hijosIds != null) ? hijosIds : new ArrayList<>();
    }

    // Métodos utilitarios
    public void agregarNecesidad(TipoRecurso tipo, int cantidad) {
        if (tipo == null || cantidad <= 0) return;
        necesidades.put(tipo.name(), necesidades.getOrDefault(tipo.name(), 0) + cantidad);
    }

    public void agregarHijo(String hijoId) {
        if (hijoId == null || hijoId.isEmpty()) return;
        if (!hijosIds.contains(hijoId)) {
            hijosIds.add(hijoId);
        }
    }
}
