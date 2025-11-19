package com.example.estructuras.Mapping.dto;

import java.util.Map;

/** Estadísticas por ubicación: totales por tipo y total de la ubicación. */
public class InventarioUbicacionEstadisticaDto {
    private String ubicacionId;
    private String ubicacionNombre;
    private Map<String, Integer> totalPorTipo; // TipoRecurso -> suma cantidades en esa ubicación
    private int totalUbicacion; // suma total de recursos en esa ubicación

    public InventarioUbicacionEstadisticaDto() {}

    public InventarioUbicacionEstadisticaDto(String ubicacionId, String ubicacionNombre, Map<String, Integer> totalPorTipo, int totalUbicacion) {
        this.ubicacionId = ubicacionId;
        this.ubicacionNombre = ubicacionNombre;
        this.totalPorTipo = totalPorTipo;
        this.totalUbicacion = totalUbicacion;
    }

    public String getUbicacionId() { return ubicacionId; }
    public void setUbicacionId(String ubicacionId) { this.ubicacionId = ubicacionId; }

    public String getUbicacionNombre() { return ubicacionNombre; }
    public void setUbicacionNombre(String ubicacionNombre) { this.ubicacionNombre = ubicacionNombre; }

    public Map<String, Integer> getTotalPorTipo() { return totalPorTipo; }
    public void setTotalPorTipo(Map<String, Integer> totalPorTipo) { this.totalPorTipo = totalPorTipo; }

    public int getTotalUbicacion() { return totalUbicacion; }
    public void setTotalUbicacion(int totalUbicacion) { this.totalUbicacion = totalUbicacion; }
}

