package com.example.estructuras.Mapping.dto;

import java.util.List;
import java.util.Map;

public class NodoDistribucionRequestDto {
    private String ubicacionId;
    private Map<String, Integer> necesidades; // TipoRecurso como String
    private List<String> hijosIds;

    public NodoDistribucionRequestDto() {}

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

