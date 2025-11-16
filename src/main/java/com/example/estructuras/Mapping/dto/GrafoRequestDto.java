package com.example.estructuras.Mapping.dto;

import java.util.List;

public class GrafoRequestDto {
    private List<UbicacionRequestDto> ubicaciones;
    private List<RutaRequestDto> rutas;

    public GrafoRequestDto() {}

    public GrafoRequestDto(List<UbicacionRequestDto> ubicaciones, List<RutaRequestDto> rutas) {
        this.ubicaciones = ubicaciones;
        this.rutas = rutas;
    }

    public List<UbicacionRequestDto> getUbicaciones() {
        return ubicaciones;
    }
    public void setUbicaciones(List<UbicacionRequestDto> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
    public List<RutaRequestDto> getRutas() {
        return rutas;
    }
    public void setRutas(List<RutaRequestDto> rutas) {
        this.rutas = rutas;
    }
}
