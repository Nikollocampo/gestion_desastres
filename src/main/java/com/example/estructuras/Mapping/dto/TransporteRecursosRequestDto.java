package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoRecurso;

public class TransporteRecursosRequestDto {
    private String origenId;
    private String destinoId;
    private TipoRecurso tipo;
    private int cantidad;

    public String getOrigenId() { return origenId; }
    public void setOrigenId(String origenId) { this.origenId = origenId; }
    public String getDestinoId() { return destinoId; }
    public void setDestinoId(String destinoId) { this.destinoId = destinoId; }
    public TipoRecurso getTipo() { return tipo; }
    public void setTipo(TipoRecurso tipo) { this.tipo = tipo; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}

