package com.example.estructuras.Mapping.dto;

import java.time.LocalDateTime;

public class EvacuacionResponseDto {
    private String idEvacuacion;
    private String desastreId;
    private String desastreNombre;
    private String ubicacionId;
    private String ubicacionNombre;
    private String prioridadDesastre;
    private String riesgoZona;
    private int personasAfectadas;
    private int personasEvacuadas;
    private int magnitud;
    private LocalDateTime fechaRegistro;

    public EvacuacionResponseDto() {}

    public EvacuacionResponseDto(String idEvacuacion, String desastreId, String desastreNombre,
                                 String ubicacionId, String ubicacionNombre, String prioridadDesastre,
                                 String riesgoZona, int personasAfectadas, int personasEvacuadas,
                                 int magnitud, LocalDateTime fechaRegistro) {
        this.idEvacuacion = idEvacuacion;
        this.desastreId = desastreId;
        this.desastreNombre = desastreNombre;
        this.ubicacionId = ubicacionId;
        this.ubicacionNombre = ubicacionNombre;
        this.prioridadDesastre = prioridadDesastre;
        this.riesgoZona = riesgoZona;
        this.personasAfectadas = personasAfectadas;
        this.personasEvacuadas = personasEvacuadas;
        this.magnitud = magnitud;
        this.fechaRegistro = fechaRegistro;
    }

    public String getIdEvacuacion() { return idEvacuacion; }
    public void setIdEvacuacion(String idEvacuacion) { this.idEvacuacion = idEvacuacion; }

    public String getDesastreId() { return desastreId; }
    public void setDesastreId(String desastreId) { this.desastreId = desastreId; }

    public String getDesastreNombre() { return desastreNombre; }
    public void setDesastreNombre(String desastreNombre) { this.desastreNombre = desastreNombre; }

    public String getUbicacionId() { return ubicacionId; }
    public void setUbicacionId(String ubicacionId) { this.ubicacionId = ubicacionId; }

    public String getUbicacionNombre() { return ubicacionNombre; }
    public void setUbicacionNombre(String ubicacionNombre) { this.ubicacionNombre = ubicacionNombre; }

    public String getPrioridadDesastre() { return prioridadDesastre; }
    public void setPrioridadDesastre(String prioridadDesastre) { this.prioridadDesastre = prioridadDesastre; }

    public String getRiesgoZona() { return riesgoZona; }
    public void setRiesgoZona(String riesgoZona) { this.riesgoZona = riesgoZona; }

    public int getPersonasAfectadas() { return personasAfectadas; }
    public void setPersonasAfectadas(int personasAfectadas) { this.personasAfectadas = personasAfectadas; }

    public int getPersonasEvacuadas() { return personasEvacuadas; }
    public void setPersonasEvacuadas(int personasEvacuadas) { this.personasEvacuadas = personasEvacuadas; }

    public int getMagnitud() { return magnitud; }
    public void setMagnitud(int magnitud) { this.magnitud = magnitud; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}

