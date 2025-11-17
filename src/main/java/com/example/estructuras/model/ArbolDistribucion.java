package com.example.estructuras.model;

/**
 * Modelo POJO para ArbolDistribucion.
 * Solo contiene datos, sin lógica de negocio.
 * La lógica está en ArbolDistribucionService.
 * raizId hace referencia al ID (campo id) de un NodoDistribucion, no al id de la ubicación.
 */
public class ArbolDistribucion {
    private String id;
    private String nombre;
    private String raizId; // ID del NodoDistribucion raíz

    public ArbolDistribucion() {}

    public ArbolDistribucion(String id, String nombre, String raizId) {
        this.id = id;
        this.nombre = nombre;
        this.raizId = raizId;
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

    public String getRaizId() {
        return raizId;
    }

    public void setRaizId(String raizId) {
        this.raizId = raizId;
    }
}
