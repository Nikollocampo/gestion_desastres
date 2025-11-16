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

    // Transportar recursos siguiendo la ruta más corta
    public boolean transportarRecursos(Ubicacion origen, Ubicacion destino,
                                       TipoRecurso tipo, int cantidad) {
        List<Ubicacion> ruta = grafoRutas.calcularRutaMasCorta(origen, destino);

        if (ruta == null || ruta.size() < 2) {
            System.out.println("No existe ruta entre " + origen.getNombre() +
                    " y " + destino.getNombre());
            return false;
        }

        System.out.println("\n=== Transporte de Recursos ===");
        System.out.println("Ruta: " + ruta.stream()
                .map(Ubicacion::getNombre)
                .reduce((a, b) -> a + " -> " + b).orElse(""));

        return mapaRecursos.transferirRecurso(origen, destino, tipo, cantidad);
    }
}
