package com.example.estructuras.service;

import com.example.estructuras.model.GrafoNoDirigido;
import com.example.estructuras.model.Nodo;
import com.example.estructuras.model.Ruta;
import com.example.estructuras.model.Ubicacion;

import java.util.*;

public class DijkstraService {
    public Map<Ubicacion, Float> calcularDistancias(GrafoNoDirigido grafo, Ubicacion origen) {
        Map<Ubicacion, Float> distancias = new HashMap<>();
        for (Ubicacion u : grafo.obtenerUbicaciones()) {
            distancias.put(u, Float.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0f);

        PriorityQueue<Nodo> cola = new PriorityQueue<>();
        cola.add(new Nodo(origen, 0f));

        while (!cola.isEmpty()) {
            Nodo nodoActual = cola.poll();
            Ubicacion actual = nodoActual.getUbicacion();

            for (Ruta ruta : grafo.obtenerRutasDesde(actual)) {
                Ubicacion vecino = ruta.getDestino();
                float nuevaDistancia = distancias.get(actual) + ruta.getDistancia();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    cola.add(new Nodo(vecino, nuevaDistancia));
                }
            }
        }

        return distancias;
    }

    public List<Ubicacion> caminoMasCorto(GrafoNoDirigido grafo, Ubicacion origen, Ubicacion destino) {
        Map<Ubicacion, Float> distancias = new HashMap<>();
        Map<Ubicacion, Ubicacion> previo = new HashMap<>();

        for (Ubicacion u : grafo.obtenerUbicaciones()) {
            distancias.put(u, Float.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0f);

        PriorityQueue<Nodo> cola = new PriorityQueue<>();
        cola.add(new Nodo(origen, 0f));

        while (!cola.isEmpty()) {
            Nodo nodoActual = cola.poll();
            Ubicacion actual = nodoActual.getUbicacion();

            if (actual.equals(destino)) break;

            for (Ruta ruta : grafo.obtenerRutasDesde(actual)) {
                Ubicacion vecino = ruta.getDestino();
                float nuevaDistancia = distancias.get(actual) + ruta.getDistancia();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    previo.put(vecino, actual);
                    cola.add(new Nodo(vecino, nuevaDistancia));
                }
            }
        }

        List<Ubicacion> camino = new ArrayList<>();
        for (Ubicacion at = destino; at != null; at = previo.get(at)) {
            camino.add(at);
        }
        Collections.reverse(camino);

        if (camino.size() == 1 && !camino.get(0).equals(origen)) {
            return new ArrayList<>();
        }

        return camino;
    }

}
