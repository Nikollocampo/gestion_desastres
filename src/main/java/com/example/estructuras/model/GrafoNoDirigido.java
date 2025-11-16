package com.example.estructuras.model;

import java.util.*;

public class GrafoNoDirigido {
    private Map<Ubicacion, List<Ruta>> adyacencias = new HashMap<>();

    public void agregarUbicacion(Ubicacion ubicacion) {
        adyacencias.putIfAbsent(ubicacion, new ArrayList<>());
    }

    public void agregarRuta(Ruta ruta) {
        agregarUbicacion(ruta.getOrigen());
        agregarUbicacion(ruta.getDestino());

        // Agrega la ruta original
        adyacencias.get(ruta.getOrigen()).add(ruta);

        // Agrega la ruta inversa automáticamente
        Ruta inversa = new Ruta(ruta.getDestino(), ruta.getOrigen(), ruta.getDistancia());
        adyacencias.get(ruta.getDestino()).add(inversa);

    }

    public List<Ruta> obtenerRutasDesde(Ubicacion origen) {
        return adyacencias.getOrDefault(origen, new ArrayList<>());
    }

    public Set<Ubicacion> obtenerUbicaciones() {
        return adyacencias.keySet();
    }

    public void imprimirGrafo() {
        for (Ubicacion origen : adyacencias.keySet()) {
            System.out.print(origen + " -> ");
            List<Ruta> rutas = adyacencias.get(origen);
            for (Ruta r : rutas) {
                System.out.print(r.getDestino().getNombre() + "(" + r.getDistancia() + " km) ");
            }
            System.out.println();
        }
    }
    public Ruta obtenerRuta(Ubicacion origen, Ubicacion destino) {
        List<Ruta> rutas = obtenerRutasDesde(origen);
        for (Ruta r : rutas) {
            if (r.getDestino().equals(destino)) {
                return r;
            }
        }
        return null;
    }

    //calcula la ruta mas corta entre dos ubicaciones usando el algoritmo de Dijkstra
    public List<Ubicacion> calcularRutaMasCorta(Ubicacion origen, Ubicacion destino) {
        return Dijkstra.caminoMasCorto(this, origen, destino);
    }

    public Map<Ubicacion, Float> calcularTodasDistancias(Ubicacion origen) {
        return Dijkstra.calcularDistancias(this, origen);
    }

    // Mostrar todas las rutas desde una ubicación
    public void mostrarRutasDesde(Ubicacion origen) {
        System.out.println("\n=== Rutas desde " + origen.getNombre() + " ===");
        List<Ruta> rutas = obtenerRutasDesde(origen);
        if (rutas.isEmpty()) {
            System.out.println("No hay rutas disponibles");
        } else {
            rutas.forEach(r -> System.out.println("  - " + r));
        }
    }
}
