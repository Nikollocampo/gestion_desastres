package com.example.estructuras.Mapping.dto;

import java.util.Map;

public class InventarioUbicacionResponseDto {
    private String ubicacionId;
    private String ubicacionNombre;
    private Map<String, RecursoResponseDto> recursos; // TipoRecurso como String

    public InventarioUbicacionResponseDto() {}

    public InventarioUbicacionResponseDto(String ubicacionId, String ubicacionNombre, Map<String, RecursoResponseDto> recursos) {
        this.ubicacionId = ubicacionId;
        this.ubicacionNombre = ubicacionNombre;
        this.recursos = recursos;
    }

    public String getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(String ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getUbicacionNombre() {
        return ubicacionNombre;
    }

    public void setUbicacionNombre(String ubicacionNombre) {
        this.ubicacionNombre = ubicacionNombre;
    }

    public Map<String, RecursoResponseDto> getRecursos() {
        return recursos;
    }

    public void setRecursos(Map<String, RecursoResponseDto> recursos) {
        this.recursos = recursos;
    }
}
