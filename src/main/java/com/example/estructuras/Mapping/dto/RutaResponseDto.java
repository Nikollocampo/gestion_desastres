package com.example.estructuras.Mapping.dto;

public class RutaResponseDto {
    private UbicacionResponseDto origen;
    private UbicacionResponseDto destino;
    private float distancia;
    private int peso;

    public RutaResponseDto() {}

    public RutaResponseDto(UbicacionResponseDto origen, UbicacionResponseDto destino, float distancia, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.peso = peso;
    }

    public UbicacionResponseDto getOrigen() {
        return origen;
    }

    public void setOrigen(UbicacionResponseDto origen) {
        this.origen = origen;
    }

    public UbicacionResponseDto getDestino() {
        return destino;
    }

    public void setDestino(UbicacionResponseDto destino) {
        this.destino = destino;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

}
