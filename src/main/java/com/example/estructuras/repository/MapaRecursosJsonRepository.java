package com.example.estructuras.repository;

import com.example.estructuras.model.MapaRecursos;
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
public class MapaRecursosJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/mapaRecursos.json").toFile();

    public synchronized List<MapaRecursos> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<MapaRecursos> list = mapper.readValue(file, new TypeReference<List<MapaRecursos>>() {});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized Optional<MapaRecursos> findById(String id) throws IOException {
        return findAll().stream()
                .filter(m -> id.equals(m.getId()))
                .findFirst();
    }

    public synchronized void save(MapaRecursos mapaRecursos) throws IOException {
        List<MapaRecursos> mapas = findAll();
        mapas.removeIf(m -> m.getId().equals(mapaRecursos.getId()));
        mapas.add(mapaRecursos);
        saveAll(mapas);
    }

    public synchronized void saveAll(List<MapaRecursos> mapas) throws IOException {
        mapper.writeValue(file, mapas);
    }

    public synchronized boolean deleteById(String id) throws IOException {
        List<MapaRecursos> mapas = findAll();
        boolean removed = mapas.removeIf(m -> id.equals(m.getId()));
        if (removed) {
            saveAll(mapas);
        }
        return removed;
    }
}

