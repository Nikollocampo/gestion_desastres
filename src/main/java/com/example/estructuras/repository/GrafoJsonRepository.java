package com.example.estructuras.repository;

import com.example.estructuras.model.GrafoNoDirigido;
import com.example.estructuras.model.Ruta;
import com.example.estructuras.model.Ubicacion;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class GrafoJsonRepository {
    private final RutaJsonRepository rutaRepository;
    private final UbicacionJsonRepository ubicacionRepository;

    public GrafoJsonRepository(RutaJsonRepository rutaRepository, UbicacionJsonRepository ubicacionRepository) {
        this.rutaRepository = rutaRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public GrafoNoDirigido cargarGrafo() throws IOException {
        GrafoNoDirigido grafo = new GrafoNoDirigido();

        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        for (Ubicacion u : ubicaciones) {
            grafo.agregarUbicacion(u);
        }

        List<Ruta> rutas = rutaRepository.findAll();
        for (Ruta r : rutas) {
            grafo.agregarRuta(r);
        }

        return grafo;
    }
}
