package com.example.estructuras.Mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecursoAsignadoDto {
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("cantidad")
    private int cantidad;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
