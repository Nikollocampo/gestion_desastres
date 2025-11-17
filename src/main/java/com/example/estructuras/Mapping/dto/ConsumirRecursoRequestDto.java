package com.example.estructuras.Mapping.dto;

public class ConsumirRecursoRequestDto {
    private String tipoRecurso; // nombre del enum TipoRecurso
    private int cantidad;

    public ConsumirRecursoRequestDto() {}

    public ConsumirRecursoRequestDto(String tipoRecurso, int cantidad) {
        this.tipoRecurso = tipoRecurso;
        this.cantidad = cantidad;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

