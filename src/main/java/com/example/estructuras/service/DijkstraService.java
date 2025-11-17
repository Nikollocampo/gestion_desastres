package com.example.estructuras.service;

import com.example.estructuras.model.GrafoNoDirigido;
import com.example.estructuras.model.Nodo;
import com.example.estructuras.model.Ruta;
import com.example.estructuras.model.Ubicacion;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DijkstraService {

    public Map<Ubicacion, Float> calcularDistancias(GrafoNoDirigido grafo, Ubicacion origen) {
        Map<Ubicacion, Float> distancias = new HashMap<>();
        for (Ubicacion u : grafo.obtenerUbicaciones()) {
            distancias.put(u, Float.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0f);

        PriorityQueue<AbstractMap.SimpleEntry<Ubicacion, Float>> cola =
                new PriorityQueue<>((a, b) -> Float.compare(a.getValue(), b.getValue()));
        cola.add(new AbstractMap.SimpleEntry<>(origen, 0f));

        while (!cola.isEmpty()) {
            AbstractMap.SimpleEntry<Ubicacion, Float> entrada = cola.poll();
            Ubicacion actual = entrada.getKey();
            float distanciaExtraida = entrada.getValue();

            // Si la entrada extraída está obsoleta, se ignora
            if (distanciaExtraida > distancias.getOrDefault(actual, Float.POSITIVE_INFINITY)) {
                continue;
            }

            List<Ruta> rutas = grafo.obtenerRutasDesde(actual);
            if (rutas == null) {
                continue;
            }

            for (Ruta ruta : rutas) {
                Ubicacion vecino = ruta.getDestino();
                float nuevaDistancia = distancias.get(actual) + ruta.getDistancia();

                if (nuevaDistancia < distancias.getOrDefault(vecino, Float.POSITIVE_INFINITY)) {
                    distancias.put(vecino, nuevaDistancia);
                    cola.add(new AbstractMap.SimpleEntry<>(vecino, nuevaDistancia));
                }
            }
        }

        return distancias;
    }

    /**
     * Calcula el camino más corto entre origen y destino. Devuelve lista vacía si no hay camino.
     * Reutiliza la misma lógica de cola con descarte de entradas obsoletas y guarda predecesores para reconstruir el camino.
     */
    public List<Ubicacion> caminoMasCorto(GrafoNoDirigido grafo, Ubicacion origen, Ubicacion destino) {
        Map<Ubicacion, Float> distancias = new HashMap<>();
        Map<Ubicacion, Ubicacion> previo = new HashMap<>();

        for (Ubicacion u : grafo.obtenerUbicaciones()) {
            distancias.put(u, Float.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0f);

        PriorityQueue<AbstractMap.SimpleEntry<Ubicacion, Float>> cola =
                new PriorityQueue<>((a, b) -> Float.compare(a.getValue(), b.getValue()));
        cola.add(new AbstractMap.SimpleEntry<>(origen, 0f));

        while (!cola.isEmpty()) {
            AbstractMap.SimpleEntry<Ubicacion, Float> entrada = cola.poll();
            Ubicacion actual = entrada.getKey();
            float distanciaExtraida = entrada.getValue();

            if (distanciaExtraida > distancias.getOrDefault(actual, Float.POSITIVE_INFINITY)) {
                continue;
            }

            if (actual.equals(destino)) {
                break;
            }

            List<Ruta> rutas = grafo.obtenerRutasDesde(actual);
            if (rutas == null) {
                continue;
            }

            for (Ruta ruta : rutas) {
                Ubicacion vecino = ruta.getDestino();
                float nuevaDistancia = distancias.get(actual) + ruta.getDistancia();

                if (nuevaDistancia < distancias.getOrDefault(vecino, Float.POSITIVE_INFINITY)) {
                    distancias.put(vecino, nuevaDistancia);
                    previo.put(vecino, actual);
                    cola.add(new AbstractMap.SimpleEntry<>(vecino, nuevaDistancia));
                }
            }
        }

        // Si el destino no fue alcanzado, devolver lista vacía
        if (distancias.getOrDefault(destino, Float.POSITIVE_INFINITY) == Float.POSITIVE_INFINITY) {
            return new ArrayList<>();
        }

        List<Ubicacion> camino = new ArrayList<>();
        for (Ubicacion at = destino; at != null; at = previo.get(at)) {
            camino.add(at);
        }
        Collections.reverse(camino);

        // Verificación adicional: si el primer elemento no es el origen, no hay camino válido
        if (camino.isEmpty() || !camino.get(0).equals(origen)) {
            return new ArrayList<>();
        }

        return camino;
    }

}
