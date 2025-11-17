package com.example.estructuras.Mapping.dto;

import java.util.List;
import java.util.Map;

public class GrafoResponseDto {
    private Map<String, List<RutaResponseDto>> adyacencias;

    public GrafoResponseDto() {}

    public GrafoResponseDto(Map<String, List<RutaResponseDto>> adyacencias) {
        this.adyacencias = adyacencias;
    }

    public Map<String, List<RutaResponseDto>> getAdyacencias() {
        return adyacencias;
    }

    public void setAdyacencias(Map<String, List<RutaResponseDto>> adyacencias) {
        this.adyacencias = adyacencias;
    }
}
