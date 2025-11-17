package com.example.estructuras.Mapping.dto;

public class ArbolDistribucionResponseDto {
    private String id;
    private String nombre;
    private NodoDistribucionResponseDto raiz; // Nodo raíz completo con hijos

    public ArbolDistribucionResponseDto() {}

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

    public NodoDistribucionResponseDto getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoDistribucionResponseDto raiz) {
        this.raiz = raiz;
    }
}

