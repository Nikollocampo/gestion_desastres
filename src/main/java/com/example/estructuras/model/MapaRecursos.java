package com.example.estructuras.model;

import java.util.*;

/**
 * Modelo POJO para MapaRecursos.
 * Solo contiene datos, sin lógica de negocio.
 * La lógica está en MapaRecursosService.
 */
public class MapaRecursos {
    private String id;
    private String nombre;
    private Map<String, Map<String, Recurso>> mapaRecursos; // ubicacionId -> (tipoRecurso -> Recurso)

    public MapaRecursos() {
        this.mapaRecursos = new HashMap<>();
    }

    public MapaRecursos(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.mapaRecursos = new HashMap<>();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String, Map<String, Recurso>> getMapaRecursos() {
        return mapaRecursos;
    }

    public void setMapaRecursos(Map<String, Map<String, Recurso>> mapaRecursos) {
        this.mapaRecursos = mapaRecursos;
    }
}
