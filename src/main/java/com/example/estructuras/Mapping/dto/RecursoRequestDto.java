package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoRecurso;

public class RecursoRequestDto {
    private String id;
    private String nombre;
    private TipoRecurso tipo; // Enum correcto
    private int cantidad;
    private UbicacionResponseDto ubicacion;

    public RecursoRequestDto() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoRecurso getTipo() { return tipo; }
    public void setTipo(TipoRecurso tipo) { this.tipo = tipo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public UbicacionResponseDto getUbicacion() { return ubicacion; }
    public void setUbicacion(UbicacionResponseDto ubicacion) { this.ubicacion = ubicacion; }
}
