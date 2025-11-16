package com.example.estructuras.repository;

import com.example.estructuras.model.Usuario;
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
public class UserJsonRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = Paths.get("src/main/resources/json/users.json").toFile();

    public synchronized List<Usuario> findAll() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Usuario> list = mapper.readValue(file, new TypeReference<List<Usuario>>(){});
        return list == null ? new ArrayList<>() : list;
    }

    public synchronized Optional<Usuario> findByUsername(String username) throws IOException {
        return findAll().stream().filter(u -> username.equals(u.getEmail()) || username.equals(u.getNombre()) || username.equals(u.getId())).findFirst();
    }

    public synchronized void add(Usuario usuario) throws IOException {
        List<Usuario> usuarios = findAll();
        usuarios.add(usuario);
        saveAll(usuarios);
    }

    public synchronized void saveAll(List<Usuario> usuarios) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, usuarios);
    }
}
