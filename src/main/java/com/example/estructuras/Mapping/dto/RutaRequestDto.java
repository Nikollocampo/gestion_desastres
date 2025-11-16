package com.example.estructuras.Mapping.dto;

public class RutaRequestDto {

    //en este caso solo se envia el identificador de la ubicacion
    private String origenId;
    private String destinoId;
    private float distancia;

    public RutaRequestDto() {}

    public RutaRequestDto(String origenId, String destinoId, float distancia) {
        this.origenId = origenId;
        this.destinoId = destinoId;
        this.distancia = distancia;
    }

    public String getOrigenId() {
        return origenId;
    }

    public void setOrigenId(String origenId) {
        this.origenId = origenId;
    }

    public String getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(String destinoId) {
        this.destinoId = destinoId;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

}
