package com.example.estructuras.Mapping.dto;

import java.util.List;

/** Respuesta compuesta para evacuaciones detalladas: lista + resumen. */
public class EvacuacionDetalladaResponseDto {
    private List<EvacuacionResponseDto> detalle;
    private EvacuacionResumenDto resumen;

    public EvacuacionDetalladaResponseDto() {}

    public EvacuacionDetalladaResponseDto(List<EvacuacionResponseDto> detalle, EvacuacionResumenDto resumen) {
        this.detalle = detalle;
        this.resumen = resumen;
    }

    public List<EvacuacionResponseDto> getDetalle() { return detalle; }
    public void setDetalle(List<EvacuacionResponseDto> detalle) { this.detalle = detalle; }

    public EvacuacionResumenDto getResumen() { return resumen; }
    public void setResumen(EvacuacionResumenDto resumen) { this.resumen = resumen; }
}

