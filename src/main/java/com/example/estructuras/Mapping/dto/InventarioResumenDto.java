package com.example.estructuras.Mapping.dto;

import java.util.Map;

/** Resumen agregado del inventario completo: totales por tipo y total general. */
public class InventarioResumenDto {
    private Map<String, Integer> totalPorTipo; // TipoRecurso -> suma cantidades
    private int totalGeneral; // suma de todos los recursos
    private int ubicacionesConRecursos; // número de ubicaciones que tienen al menos 1 recurso

    public InventarioResumenDto() {}

    public InventarioResumenDto(Map<String, Integer> totalPorTipo, int totalGeneral, int ubicacionesConRecursos) {
        this.totalPorTipo = totalPorTipo;
        this.totalGeneral = totalGeneral;
        this.ubicacionesConRecursos = ubicacionesConRecursos;
    }

    public Map<String, Integer> getTotalPorTipo() { return totalPorTipo; }
    public void setTotalPorTipo(Map<String, Integer> totalPorTipo) { this.totalPorTipo = totalPorTipo; }

    public int getTotalGeneral() { return totalGeneral; }
    public void setTotalGeneral(int totalGeneral) { this.totalGeneral = totalGeneral; }

    public int getUbicacionesConRecursos() { return ubicacionesConRecursos; }
    public void setUbicacionesConRecursos(int ubicacionesConRecursos) { this.ubicacionesConRecursos = ubicacionesConRecursos; }
}

