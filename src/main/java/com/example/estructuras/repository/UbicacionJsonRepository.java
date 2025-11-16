package com.example.estructuras.repository;

import com.example.estructuras.model.Ubicacion;
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
public class UbicacionJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/ubicaciones.json").toFile();

    public synchronized List<Ubicacion> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Ubicacion> list = mapper.readValue(file, new TypeReference<List<Ubicacion>>() {});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized Optional<Ubicacion> findById(String id) throws IOException {
        return findAll().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    public synchronized void add(Ubicacion ubicacion) throws IOException {
        List<Ubicacion> ubicaciones = findAll();
        ubicaciones.add(ubicacion);
        saveAll(ubicaciones);
    }

    public synchronized void update(Ubicacion updated) throws IOException {
        List<Ubicacion> ubicaciones = findAll();
        for (int i = 0; i < ubicaciones.size(); i++) {
            if (ubicaciones.get(i).getId().equals(updated.getId())) {
                ubicaciones.set(i, updated);
                saveAll(ubicaciones);
                return;
            }
        }
        throw new IllegalArgumentException("Ubicación no encontrada con ID: " + updated.getId());
    }

    public synchronized void delete(String id) throws IOException {
        List<Ubicacion> ubicaciones = findAll();
        ubicaciones.removeIf(u -> u.getId().equals(id));
        saveAll(ubicaciones);
    }

    public synchronized void saveAll(List<Ubicacion> ubicaciones) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, ubicaciones);
    }

}
