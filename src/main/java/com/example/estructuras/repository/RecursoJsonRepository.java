package com.example.estructuras.repository;

import com.example.estructuras.model.Recurso;
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
public class RecursoJsonRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/recurso.json").toFile();

    public synchronized List<Recurso> findAll() throws IOException {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        List<Recurso> list = mapper.readValue(file, new TypeReference<List<Recurso>>() {});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized Optional<Recurso> findById(String id) throws IOException {
        return findAll()
                .stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public synchronized void add(Recurso recurso) throws IOException {
        List<Recurso> recursos = findAll();
        recursos.add(recurso);
        saveAll(recursos);
    }

    public synchronized void update(Recurso recurso) throws IOException {
        List<Recurso> recursos = findAll();
        boolean found = false;
        for (int i = 0; i < recursos.size(); i++) {
            if (recursos.get(i).getId().equals(recurso.getId())) {
                recursos.set(i, recurso);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Recurso no encontrado: " + recurso.getId());
        }
        saveAll(recursos);
    }

    public synchronized void delete(String id) throws IOException {
        List<Recurso> recursos = findAll();
        boolean removed = recursos.removeIf(r -> r.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Recurso no encontrado: " + id);
        }
        saveAll(recursos);
    }

    public synchronized void saveAll(List<Recurso> recursos) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, recursos);
    }
}
