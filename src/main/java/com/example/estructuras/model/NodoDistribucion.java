package com.example.estructuras.model;

import java.util.*;

/**
 * Modelo POJO para NodoDistribucion.
 * Solo contiene datos, sin lógica de negocio.
 * La lógica está en NodoDistribucionService.
 */
public class NodoDistribucion {
    private String id;
    private String ubicacionId;
    private Map<String, Integer> necesidades; // TipoRecurso como String -> cantidad
    private List<String> hijosIds; // IDs de los hijos

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

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(String ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public Map<String, Integer> getNecesidades() {
        return necesidades;
    }

    public void setNecesidades(Map<String, Integer> necesidades) {
        this.necesidades = necesidades;
    }

    public List<String> getHijosIds() {
        return hijosIds;
    }

    public void setHijosIds(List<String> hijosIds) {
        this.hijosIds = hijosIds;
    }
}
