package com.example.estructuras.model;

import java.util.List;

public class SistemaTransporte {
    private MapaRecursos mapaRecursos;
    private GrafoNoDirigido grafoRutas;

    public SistemaTransporte() {
        this.mapaRecursos = new MapaRecursos();
        this.grafoRutas = new GrafoNoDirigido();
    }

    public MapaRecursos getMapaRecursos() { return mapaRecursos; }
    public GrafoNoDirigido getGrafoRutas() { return grafoRutas; }


}
