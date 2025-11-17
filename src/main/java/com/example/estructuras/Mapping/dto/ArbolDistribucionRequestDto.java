package com.example.estructuras.Mapping.dto;

public class ArbolDistribucionRequestDto {
    private String nombre;
    private String raizId; // ID del nodo raíz

    public ArbolDistribucionRequestDto() {}

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
