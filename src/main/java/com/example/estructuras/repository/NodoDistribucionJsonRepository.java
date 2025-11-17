package com.example.estructuras.repository;

import com.example.estructuras.model.NodoDistribucion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NodoDistribucionJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/nodosDistribucion.json").toFile();

    public synchronized List<NodoDistribucion> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<NodoDistribucion> list = mapper.readValue(file, new TypeReference<List<NodoDistribucion>>() {});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized void saveAll(List<NodoDistribucion> nodos) throws IOException {
        mapper.writeValue(file, nodos);
    }
}

