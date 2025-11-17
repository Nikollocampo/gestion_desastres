package com.example.estructuras.repository;

import com.example.estructuras.model.Equipo;
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
public class EquipoJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/equipos.json").toFile();

    public synchronized List<Equipo> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Equipo> list = mapper.readValue(file, new TypeReference<List<Equipo>>() {});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized Optional<Equipo> findById(String idEquipo) throws IOException {
        return findAll().stream()
                .filter(e -> idEquipo.equals(e.getIdEquipo()))
                .findFirst();
    }

    public synchronized void add(Equipo equipo) throws IOException {
        List<Equipo> equipos = findAll();
        equipos.add(equipo);
        saveAll(equipos);
    }

    public synchronized void saveAll(List<Equipo> equipos) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, equipos);
    }

    public synchronized void eliminar(String idEquipo) throws IOException {
        List<Equipo> equipos = findAll();
        equipos.removeIf(e -> idEquipo.equals(e.getIdEquipo()));
        saveAll(equipos);
    }

}
