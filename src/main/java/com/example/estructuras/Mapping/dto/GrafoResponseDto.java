package com.example.estructuras.Mapping.dto;

import java.util.List;
import java.util.Map;

public class GrafoResponseDto {
    private Map<UbicacionResponseDto, List<RutaResponseDto>> adyacencias;

    public GrafoResponseDto() {}

    public GrafoResponseDto(Map<UbicacionResponseDto, List<RutaResponseDto>> adyacencias) {
        this.adyacencias = adyacencias;
    }
    public Map<UbicacionResponseDto, List<RutaResponseDto>> getAdyacencias() {
        return adyacencias;
    }
    public void setAdyacencias(Map<UbicacionResponseDto, List<RutaResponseDto>> adyacencias) {
        this.adyacencias = adyacencias;
    }
}
