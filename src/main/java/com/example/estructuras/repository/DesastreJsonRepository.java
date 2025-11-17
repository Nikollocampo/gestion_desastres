package com.example.estructuras.repository;

import com.example.estructuras.model.Desastre;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DesastreJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final File file = Paths.get("src/main/resources/json/desastres.json").toFile();

    public synchronized List<Desastre> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            List<Desastre> list = mapper.readValue(file, new TypeReference<List<Desastre>>() {});
            return list == null ? new ArrayList<>() : list;
        } catch (Exception e) {
            // Si el archivo está vacío o tiene contenido inválido, retorna lista vacía
            return new ArrayList<>();
        }
    }

    public synchronized Optional<Desastre> findById(String idDesastre) throws IOException {
        return findAll().stream()
                .filter(d -> idDesastre.equals(d.getIdDesastre()))
                .findFirst();
    }

    public synchronized void add(Desastre desastre) throws IOException {
        List<Desastre> desastres = findAll();
        desastres.add(desastre);
        saveAll(desastres);
    }

    public synchronized void saveAll(List<Desastre> desastres) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, desastres);
    }

    public synchronized boolean deleteById(String idDesastre) throws IOException {
        List<Desastre> desastres = findAll();
        boolean removed = desastres.removeIf(d -> idDesastre.equals(d.getIdDesastre()));
        if (removed) {
            saveAll(desastres);
        }
        return removed;
    }
}
