package com.example.estructuras.Mapping.dto;

public class DefinirRutaRequestDto {
    private String origenId;
    private String destinoId;

    public DefinirRutaRequestDto() {}
    public DefinirRutaRequestDto(String origenId, String destinoId) {
        this.origenId = origenId;
        this.destinoId = destinoId;
    }
    public String getOrigenId() { return origenId; }
    public void setOrigenId(String origenId) { this.origenId = origenId; }
    public String getDestinoId() { return destinoId; }
    public void setDestinoId(String destinoId) { this.destinoId = destinoId; }
}

