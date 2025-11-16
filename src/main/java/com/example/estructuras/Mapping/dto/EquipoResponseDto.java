package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoEquipo;

public class EquipoResponseDto {
    private String idEquipo;
    private int integrantesDisponibles;
    private TipoEquipo tipoEquipo;
    private UbicacionResponseDto ubicacion;

    public EquipoResponseDto() {}

    public EquipoResponseDto(String idEquipo, int integrantesDisponibles, TipoEquipo tipoEquipo, UbicacionResponseDto ubicacion) {
        this.idEquipo = idEquipo;
        this.integrantesDisponibles = integrantesDisponibles;
        this.tipoEquipo = tipoEquipo;
        this.ubicacion = ubicacion;
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
    public UbicacionResponseDto getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(UbicacionResponseDto ubicacion) {
        this.ubicacion = ubicacion;
    }

}
