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
        this.ubicacion = new Ubicacion("U1", "Ubicacion recurso", "5", "10",TipoUbicacion.CENTRO_AYUDA);
    }
    public Recurso() {}

    public Ubicacion getUbicacion() { return this.ubicacion; }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public TipoRecurso getTipo() { return tipo; }
    public int getCantidad() { return cantidad; }

    public void agregarCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public boolean consumir(int cantidad) {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
            return true;
        }

        return false;
    }
    @Override
    public String toString() {
        return nombre + " (" + tipo + "): " + cantidad + " unidades";
    }
}
