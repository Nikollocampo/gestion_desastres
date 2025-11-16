package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.UbicacionRequestDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UbicacionService {
    private final UbicacionJsonRepository repository;

    public UbicacionService(UbicacionJsonRepository repository) {
        this.repository = repository;
    }

    public UbicacionResponseDto crear(UbicacionRequestDto dto) throws IOException {
        String id = UUID.randomUUID().toString();
        Ubicacion ubicacion = new Ubicacion(id, dto.getNombre(), dto.getCalle(), dto.getCarrera(), dto.getTipoUbicacion());
        repository.add(ubicacion);
        return toResponseDto(ubicacion);
    }

    public List<UbicacionResponseDto> listar() throws IOException {
        return repository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public UbicacionResponseDto obtenerPorId(String id) throws IOException {
        Optional<Ubicacion> ubicacion = repository.findById(id);
        if (ubicacion.isEmpty()) {
            throw new IllegalArgumentException("Ubicación no encontrada con ID: " + id);
        }
        return toResponseDto(ubicacion.get());
    }

    public UbicacionResponseDto actualizar(String id, UbicacionRequestDto dto) throws IOException {
        Ubicacion actualizada = new Ubicacion(id, dto.getNombre(), dto.getCalle(), dto.getCarrera(), dto.getTipoUbicacion());
        repository.update(actualizada);
        return toResponseDto(actualizada);
    }

    public void eliminar(String id) throws IOException {
        repository.delete(id);
    }

    private UbicacionResponseDto toResponseDto(Ubicacion ubicacion) {
        return new UbicacionResponseDto(
                ubicacion.getId(),
                ubicacion.getNombre(),
                ubicacion.getCalle(),
                ubicacion.getCarrera(),
                ubicacion.getTipoUbicacion()
        );
    }

}
