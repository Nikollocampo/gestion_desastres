package com.example.estructuras.model;

public class Recurso {
    private String id;
    private String nombre;
    private TipoRecurso tipo;
    private int cantidad;
    private Ubicacion ubicacion;

    public Recurso(String id, String nombre, TipoRecurso tipo, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.ubicacion = new Ubicacion("U1", "Ubicacion recurso", "5", "10", TipoUbicacion.CENTRO_AYUDA);
    }
    public Recurso() {}

    public String toString() {
        return nombre + " (" + tipo + "): " + cantidad + " unidades";
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public TipoRecurso getTipo() {
        return tipo;
    }
    public void setTipo(TipoRecurso tipo) {
        this.tipo = tipo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
