package com.example.estructuras.repository;

import com.example.estructuras.model.Ruta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RutaJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/rutas.json").toFile();

    public synchronized List<Ruta> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Ruta> list = mapper.readValue(file, new TypeReference<List<Ruta>>() {});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized Optional<Ruta> findByOrigenYDestino(String origenId, String destinoId) throws IOException {
        return findAll().stream()
                .filter(r -> r.getOrigen().getId().equals(origenId) && r.getDestino().getId().equals(destinoId))
                .findFirst();
    }

    public synchronized List<Ruta> buscarPorUbicacion(String ubicacionId) throws IOException {
        List<Ruta> resultado = new ArrayList<>();
        for (Ruta ruta : findAll()) {
            if (ruta.getOrigen().getId().equals(ubicacionId) || ruta.getDestino().getId().equals(ubicacionId)) {
                resultado.add(ruta);
            }
        }
        return resultado;
    }

    public synchronized void add(Ruta ruta) throws IOException {
        List<Ruta> rutas = findAll();
        rutas.add(ruta);
        saveAll(rutas);
    }

    public synchronized void saveAll(List<Ruta> rutas) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, rutas);
    }

}
