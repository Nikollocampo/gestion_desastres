package com.example.estructuras.Mapping.dto;

public class TransferirRecursoRequestDto {
    private String origenId;
    private String destinoId;
    private String tipoRecurso; // nombre del enum TipoRecurso
    private int cantidad;

    public TransferirRecursoRequestDto() {}

    public TransferirRecursoRequestDto(String origenId, String destinoId, String tipoRecurso, int cantidad) {
        this.origenId = origenId;
        this.destinoId = destinoId;
        this.tipoRecurso = tipoRecurso;
        this.cantidad = cantidad;
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

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

