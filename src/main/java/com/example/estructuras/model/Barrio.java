package com.example.estructuras.model;

public class Barrio {
    private String nombre;
    private Ciudad ciudad;

    public Barrio(String nombre,Ciudad ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public Barrio() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
