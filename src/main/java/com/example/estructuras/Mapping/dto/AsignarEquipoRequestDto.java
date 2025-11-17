package com.example.estructuras.Mapping.dto;

public class AsignarEquipoRequestDto {
    private String desastreId;
    private String equipoId;

    public AsignarEquipoRequestDto() {}
    public AsignarEquipoRequestDto(String desastreId, String equipoId) {
        this.desastreId = desastreId;
        this.equipoId = equipoId;
    }
    public String getDesastreId() { return desastreId; }
    public void setDesastreId(String desastreId) { this.desastreId = desastreId; }
    public String getEquipoId() { return equipoId; }
    public void setEquipoId(String equipoId) { this.equipoId = equipoId; }
}

