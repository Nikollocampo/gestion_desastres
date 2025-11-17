package com.example.estructuras.repository;

import com.example.estructuras.model.ArbolDistribucion;
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
public class ArbolDistribucionJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/arbolesDistribucion.json").toFile();

    public synchronized List<ArbolDistribucion> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<ArbolDistribucion> list = mapper.readValue(file, new TypeReference<List<ArbolDistribucion>>() {});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized Optional<ArbolDistribucion> findById(String id) throws IOException {
        return findAll().stream()
                .filter(a -> id.equals(a.getId()))
                .findFirst();
    }

    public synchronized void save(ArbolDistribucion arbol) throws IOException {
        List<ArbolDistribucion> arboles = findAll();
        arboles.removeIf(a -> a.getId().equals(arbol.getId()));
        arboles.add(arbol);
        saveAll(arboles);
    }

    public synchronized void saveAll(List<ArbolDistribucion> arboles) throws IOException {
        mapper.writeValue(file, arboles);
    }

    public synchronized boolean deleteById(String id) throws IOException {
        List<ArbolDistribucion> arboles = findAll();
        boolean removed = arboles.removeIf(a -> id.equals(a.getId()));
        if (removed) {
            saveAll(arboles);
        }
        return removed;
    }
}

