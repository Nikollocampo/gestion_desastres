package com.example.estructuras.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodoDistribucion {
    private Ubicacion ubicacion;
    private Map<TipoRecurso, Integer> necesidades;
    private List<NodoDistribucion> hijos;

    public NodoDistribucion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
        this.necesidades = new HashMap<>();
        this.hijos = new ArrayList<>();
    }

    public Map<TipoRecurso, Integer> asignarRecursosDisponibles(List<Recurso> recursosDisponibles) {
        // Primero asignar a hijos
        for (NodoDistribucion hijo : hijos) {
            hijo.asignarRecursosDisponibles(recursosDisponibles);
        }

        Map<TipoRecurso, Integer> asignadoNodo = new HashMap<>();

        // Para cada necesidad del nodo, intentar cubrirla con la lista global de recursos
        for (Map.Entry<TipoRecurso, Integer> entry : new HashMap<>(necesidades).entrySet()) {
            TipoRecurso tipo = entry.getKey();
            int faltante = entry.getValue();
            if (faltante <= 0) continue;

            for (Recurso recurso : recursosDisponibles) {
                if (recurso.getTipo() == tipo && faltante > 0) {
                    int disponible = recurso.getCantidad();
                    if (disponible <= 0) continue;
                    int aConsumir = Math.min(disponible, faltante);
                    recurso.consumir(aConsumir);
                    faltante -= aConsumir;
                    asignadoNodo.put(tipo, asignadoNodo.getOrDefault(tipo, 0) + aConsumir);
                }
            }
            necesidades.put(tipo, faltante);
        }

        System.out.println("Nodo " + ubicacion.getNombre() + " - asignado: " + asignadoNodo);
        return asignadoNodo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void agregarHijo(NodoDistribucion hijo) {
        hijos.add(hijo);
    }

    public List<NodoDistribucion> getHijos() {
        return hijos;
    }

    public void agregarNecesidad(TipoRecurso tipo, int cantidad) {
        necesidades.put(tipo, necesidades.getOrDefault(tipo, 0) + cantidad);
    }

    public Map<TipoRecurso, Integer> getNecesidades() {
        return necesidades;
    }
}
