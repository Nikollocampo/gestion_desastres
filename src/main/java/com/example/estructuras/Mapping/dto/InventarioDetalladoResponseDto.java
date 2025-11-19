package com.example.estructuras.Mapping.dto;

import java.util.List;

/** Respuesta compuesta para inventario detallado (lista ubicaciones + resumen global). */
public class InventarioDetalladoResponseDto {
    private List<InventarioUbicacionResponseDto> detalleUbicaciones;
    private InventarioResumenDto resumenGlobal;

    public InventarioDetalladoResponseDto() {}

    public InventarioDetalladoResponseDto(List<InventarioUbicacionResponseDto> detalleUbicaciones, InventarioResumenDto resumenGlobal) {
        this.detalleUbicaciones = detalleUbicaciones;
        this.resumenGlobal = resumenGlobal;
    }

    public List<InventarioUbicacionResponseDto> getDetalleUbicaciones() { return detalleUbicaciones; }
    public void setDetalleUbicaciones(List<InventarioUbicacionResponseDto> detalleUbicaciones) { this.detalleUbicaciones = detalleUbicaciones; }

    public InventarioResumenDto getResumenGlobal() { return resumenGlobal; }
    public void setResumenGlobal(InventarioResumenDto resumenGlobal) { this.resumenGlobal = resumenGlobal; }
}

