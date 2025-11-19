package com.example.estructuras.Mapping.dto;

/** Resumen agregado de evacuaciones para reportes. */
public class EvacuacionResumenDto {
    private int totalEventos; // número de desastres procesados
    private int totalPersonasAfectadas;
    private int totalPersonasEvacuadas;
    private double porcentajeEvacuacion; // totalEvacuadas / totalAfectadas

    public EvacuacionResumenDto() {}

    public EvacuacionResumenDto(int totalEventos, int totalPersonasAfectadas, int totalPersonasEvacuadas) {
        this.totalEventos = totalEventos;
        this.totalPersonasAfectadas = totalPersonasAfectadas;
        this.totalPersonasEvacuadas = totalPersonasEvacuadas;
        this.porcentajeEvacuacion = totalPersonasAfectadas > 0 ? (totalPersonasEvacuadas * 100.0 / totalPersonasAfectadas) : 0.0;
    }

    public int getTotalEventos() { return totalEventos; }
    public void setTotalEventos(int totalEventos) { this.totalEventos = totalEventos; }

    public int getTotalPersonasAfectadas() { return totalPersonasAfectadas; }
    public void setTotalPersonasAfectadas(int totalPersonasAfectadas) { this.totalPersonasAfectadas = totalPersonasAfectadas; }

    public int getTotalPersonasEvacuadas() { return totalPersonasEvacuadas; }
    public void setTotalPersonasEvacuadas(int totalPersonasEvacuadas) { this.totalPersonasEvacuadas = totalPersonasEvacuadas; }

    public double getPorcentajeEvacuacion() { return porcentajeEvacuacion; }
    public void setPorcentajeEvacuacion(double porcentajeEvacuacion) { this.porcentajeEvacuacion = porcentajeEvacuacion; }
}

