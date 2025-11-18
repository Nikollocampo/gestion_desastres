package com.example.estructuras.service;

import com.example.estructuras.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class DijkstraServiceTest {
    private DijkstraService dijkstraService;
    private GrafoNoDirigido grafo;
    private Ubicacion a, b, c, d;
    private Ruta ab, bc, cd, ad;

    @BeforeEach
    void setUp() {
        dijkstraService = new DijkstraService();
        grafo = new GrafoNoDirigido();
        a = new Ubicacion("1", "A", "Calle 1", "Carrera 1", TipoUbicacion.REFUGIO);
        b = new Ubicacion("2", "B", "Calle 2", "Carrera 2", TipoUbicacion.REFUGIO);
        c = new Ubicacion("3", "C", "Calle 3", "Carrera 3", TipoUbicacion.CENTRO_AYUDA);
        d = new Ubicacion("4", "D", "Calle 4", "Carrera 4", TipoUbicacion.CENTRO_AYUDA);
        ab = new Ruta(a, b, 2f);
        bc = new Ruta(b, c, 3f);
        cd = new Ruta(c, d, 4f);
        ad = new Ruta(a, d, 10f);
        grafo.agregarRuta(ab);
        grafo.agregarRuta(bc);
        grafo.agregarRuta(cd);
        grafo.agregarRuta(ad);
    }

    @Test
    void testCalcularDistancias() {
        Map<Ubicacion, Float> distancias = dijkstraService.calcularDistancias(grafo, a);
        assertEquals(0f, distancias.get(a));
        assertEquals(2f, distancias.get(b));
        assertEquals(5f, distancias.get(c));
        assertEquals(9f, distancias.get(d));
    }

    @Test
    void testCaminoMasCorto() {
        List<Ubicacion> camino = dijkstraService.caminoMasCorto(grafo, a, d);
        assertNotNull(camino);
        assertEquals(List.of(a, b, c, d), camino);
    }

    @Test
    void testCaminoSinConexion() {
        Ubicacion e = new Ubicacion("5", "E", "Calle 5", "Carrera 5", TipoUbicacion.REFUGIO);
        grafo.agregarUbicacion(e);
        List<Ubicacion> camino = dijkstraService.caminoMasCorto(grafo, a, e);
        assertNotNull(camino);
        assertTrue(camino.isEmpty());
    }

    @Test
    void testDistanciasConNodoSinConexion() {
        Ubicacion e = new Ubicacion("5", "E", "Calle 5", "Carrera 5", TipoUbicacion.REFUGIO);
        grafo.agregarUbicacion(e);
        Map<Ubicacion, Float> distancias = dijkstraService.calcularDistancias(grafo, a);
        assertEquals(Float.POSITIVE_INFINITY, distancias.get(e));
    }
}

