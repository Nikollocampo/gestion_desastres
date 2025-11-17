package com.example.estructuras.Mapping.dto;

import java.util.List;
import java.util.Map;

public class NodoDistribucionResponseDto {
    private String ubicacionId;
    private String ubicacionNombre;
    private Map<String, Integer> necesidades; // TipoRecurso como String
    private List<NodoDistribucionResponseDto> hijos;

    public NodoDistribucionResponseDto() {}

    public String getUbicacionId() { return ubicacionId; }
    public void setUbicacionId(String ubicacionId) { this.ubicacionId = ubicacionId; }

    public String getUbicacionNombre() { return ubicacionNombre; }
    public void setUbicacionNombre(String ubicacionNombre) { this.ubicacionNombre = ubicacionNombre; }

    public Map<String, Integer> getNecesidades() { return necesidades; }
    public void setNecesidades(Map<String, Integer> necesidades) { this.necesidades = necesidades; }

    public List<NodoDistribucionResponseDto> getHijos() { return hijos; }
    public void setHijos(List<NodoDistribucionResponseDto> hijos) { this.hijos = hijos; }
}

