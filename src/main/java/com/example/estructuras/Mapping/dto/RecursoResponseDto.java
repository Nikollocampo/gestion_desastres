package com.example.estructuras.Mapping.dto;

public class RecursoResponseDto {
    private String id;
    private String nombre;
    private String tipo; // nombre del enum TipoRecurso
    private int cantidad;
    private UbicacionDto ubicacion;

    public RecursoResponseDto() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public UbicacionDto getUbicacion() { return ubicacion; }
    public void setUbicacion(UbicacionDto ubicacion) { this.ubicacion = ubicacion; }
}
