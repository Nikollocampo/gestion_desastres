package com.example.estructuras.model;

import java.util.ArrayList;
import java.util.List;

public class Ciudad {
    private String nombre;
    private Departamento departamento;
    private List<Barrio> barrios;

    public Ciudad(String nombre, Departamento departamento, List<Barrio> barrios) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.barrios = new ArrayList<>();
    }

    // Constructor por defecto
    public Ciudad() {
    }
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(List<Barrio> barrios) {
        this.barrios = barrios;
    }

    public void agregarBarrio(Barrio barrio) {
        this.barrios.add(barrio);
    }

    public void mostrarBarrios() {
        System.out.println("Barrios de la ciudad " + nombre + ":");
        for (Barrio b : barrios) {
            System.out.println("- " + b.getNombre());
        }
    }
}
