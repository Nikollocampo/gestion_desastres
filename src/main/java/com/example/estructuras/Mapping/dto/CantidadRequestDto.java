package com.example.estructuras.Mapping.dto;

public class CantidadRequestDto {
    private Integer cantidad;
    public CantidadRequestDto() {}
    public CantidadRequestDto(Integer cantidad) { this.cantidad = cantidad; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}

