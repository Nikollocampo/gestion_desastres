package com.example.estructuras.model;
import java.util.ArrayList;
import java.util.List;

public class Departamento {
    private String nombre;
    private List<Ciudad> ciudades;

    public Departamento(String nombre, List<Ciudad> ciudades) {
        this.nombre = nombre;

        this.ciudades = ciudades;
        this.ciudades = new ArrayList<>();
    }

    public Departamento() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public void agregarCiudad(Ciudad ciudad) {
        this.ciudades.add(ciudad);
    }

    public void mostrarCiudades() {
        System.out.println("Ciudades del departamento " + nombre + ":");
        for (Ciudad c : ciudades) {
            System.out.println("- " + c.getNombre());
        }
    }
}