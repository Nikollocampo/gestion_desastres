package com.example.estructuras.model;

import java.util.List;

public class ArbolDistribucion {
    private NodoDistribucion raiz;

    public ArbolDistribucion(NodoDistribucion raiz) {
        this.raiz = raiz;
    }

    public void asignarRecursos(List<Recurso> recursosDisponibles) {
        if (raiz == null) {
            System.out.println("Arbol vacío.");
            return;
        }
        System.out.println("=== Inicio asignación por Árbol de distribución ===");
        raiz.asignarRecursosDisponibles(recursosDisponibles);
        System.out.println("=== Fin asignación por Árbol ===");
    }

    public void imprimirEstructura() {
        imprimirNodo(raiz, 0);
    }

    private void imprimirNodo(NodoDistribucion nodo, int nivel) {
        if (nodo == null) return;
        String pref = "  ".repeat(Math.max(0, nivel));
        System.out.println(pref + "- " + nodo.getUbicacion().getNombre() + " necesidades: " + nodo.getNecesidades());
        for (NodoDistribucion hijo : nodo.getHijos()) {
            imprimirNodo(hijo, nivel + 1);
        }
    }

    public NodoDistribucion getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoDistribucion raiz) {
        this.raiz = raiz;
    }
}
