package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoEquipo;

public class EquipoRequestDto {
    private String idEquipo;
    private int integrantesDisponibles;
    private TipoEquipo tipoEquipo;
    private String ubicacionId;

    public EquipoRequestDto() {}

    public EquipoRequestDto(String idEquipo, int integrantesDisponibles, TipoEquipo tipoEquipo, String ubicacionId) {
        this.idEquipo = idEquipo;
        this.integrantesDisponibles = integrantesDisponibles;
        this.tipoEquipo = tipoEquipo;
        this.ubicacionId = ubicacionId;
    }

    public String getIdEquipo() {
        return idEquipo;
    }
    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }
    public int getIntegrantesDisponibles() {
        return integrantesDisponibles;
    }
    public void setIntegrantesDisponibles(int integrantesDisponibles) {
        this.integrantesDisponibles = integrantesDisponibles;
    }
    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }
    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }
    public String getUbicacionId() {
        return ubicacionId;
    }
    public void setUbicacionId(String ubicacionId) {
        this.ubicacionId = ubicacionId;
    }


}
