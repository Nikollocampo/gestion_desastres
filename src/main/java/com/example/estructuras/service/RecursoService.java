package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.RecursoRequestDto;
import com.example.estructuras.Mapping.dto.RecursoResponseDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.Recurso;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.RecursoJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecursoService {
    private final RecursoJsonRepository repository;

    public RecursoService(RecursoJsonRepository repository) {
        this.repository = repository;
    }

    public List<RecursoRequestDto> listar() throws IOException {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RecursoRequestDto crear(RecursoRequestDto dto) throws IOException {
        if (dto == null) {
            throw new IllegalArgumentException("RecursoRequestDto no puede ser nulo");
        }
        String id = dto.getId() != null ? dto.getId().toString().trim() : null;
        String idToUse = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        List<Recurso> recursos = repository.findAll();
        for (Recurso r : recursos) {
            String rid = r.getId() != null ? r.getId().toString().trim() : "";
            if (rid.equals(idToUse)) {
                throw new IllegalArgumentException("Ya existe un recurso con el id: " + idToUse);
            }
        }
        Recurso recurso = toEntity(dto);
        recurso.setId(idToUse); // Usa el id recibido o generado
        repository.add(recurso);
        return toDto(recurso);
    }

    public RecursoResponseDto crearYRetornarResponse(RecursoRequestDto dto) throws IOException {
        String id = dto.getId() != null ? dto.getId().toString().trim() : null;
        String idToUse = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        List<Recurso> recursos = repository.findAll();
        for (Recurso r : recursos) {
            String rid = r.getId() != null ? r.getId().toString().trim() : "";
            if (rid.equals(idToUse)) {
                throw new IllegalArgumentException("Ya existe un recurso con el id: " + idToUse);
            }
        }
        Recurso recurso = toEntity(dto);
        recurso.setId(idToUse);
        repository.add(recurso);
        return toResponseDto(recurso);
    }

    public RecursoRequestDto obtenerPorId(String id) throws IOException {
        final String idToFind = id != null ? id.trim() : null;
        Recurso recurso = repository.findAll()
                .stream()
                .filter(r -> {
                    String rid = r.getId() != null ? r.getId().toString().trim() : "";
                    return rid.equals(idToFind);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Recurso no encontrado: " + id));
        return toDto(recurso);
    }

    public RecursoResponseDto obtenerResponsePorId(String id) throws IOException {
        final String idToFind = id != null ? id.trim() : null;
        Recurso recurso = repository.findAll()
                .stream()
                .filter(r -> {
                    String rid = r.getId() != null ? r.getId().toString().trim() : "";
                    return rid.equals(idToFind);
                })
                .findFirst()
                .orElse(null);
        if (recurso == null) return null;
        return toResponseDto(recurso);
    }

    public RecursoRequestDto actualizar(RecursoRequestDto dto) throws IOException {

        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Recurso o id inválido");
        }
        obtenerPorId(dto.getId());
        Recurso recurso = toEntity(dto);
        repository.update(recurso);

        return toDto(recurso);
    }

    public void eliminar(String id) throws IOException {
        obtenerPorId(id);
        repository.delete(id);
    }

    public RecursoRequestDto agregarCantidad(String id, int cantidad) throws IOException {
        Recurso r = toEntity(obtenerPorId(id));
        r.setCantidad(r.getCantidad() + cantidad);
        repository.update(r);
        return toDto(r);
    }

    public boolean consumir(String id, int cantidad) throws IOException {
        Recurso r = toEntity(obtenerPorId(id));

        if (r.getCantidad() >= cantidad) {
            r.setCantidad(r.getCantidad() - cantidad);
            repository.update(r);
            return true;
        }
        return false;
    }

    public Recurso toEntity(RecursoRequestDto dto) {
        String id = dto.getId() != null ? dto.getId().toString().trim() : null;
        Recurso r = new Recurso(
                id,
                dto.getNombre(),
                dto.getTipo(),
                dto.getCantidad()
        );
        if (dto.getUbicacion() != null) {
            Ubicacion u = new Ubicacion(
                    dto.getUbicacion().getId(),
                    dto.getUbicacion().getNombre(),
                    dto.getUbicacion().getCalle(),
                    dto.getUbicacion().getCarrera(),
                    dto.getUbicacion().getTipoUbicacion()
            );
            r.setUbicacion(u);
        }
        return r;
    }

    public RecursoRequestDto toDto(Recurso r) {
        RecursoRequestDto dto = new RecursoRequestDto();

        dto.setId(r.getId());
        dto.setNombre(r.getNombre());
        dto.setTipo(r.getTipo()); // Usar el enum directamente
        dto.setCantidad(r.getCantidad());

        if (r.getUbicacion() != null) {
            UbicacionResponseDto u = new UbicacionResponseDto(
                    r.getUbicacion().getId(),
                    r.getUbicacion().getNombre(),
                    r.getUbicacion().getCalle(),
                    r.getUbicacion().getCarrera(),
                    r.getUbicacion().getTipoUbicacion()
            );
            dto.setUbicacion(u);
        }

        return dto;
    }

    public RecursoResponseDto toResponseDto(Recurso r) {
        RecursoResponseDto dto = new RecursoResponseDto();
        dto.setId(r.getId());
        dto.setNombre(r.getNombre());
        dto.setTipo(r.getTipo() != null ? r.getTipo().name() : null);
        dto.setCantidad(r.getCantidad());
        if (r.getUbicacion() != null) {
            UbicacionResponseDto u = new UbicacionResponseDto(
                    r.getUbicacion().getId(),
                    r.getUbicacion().getNombre(),
                    r.getUbicacion().getCalle(),
                    r.getUbicacion().getCarrera(),
                    r.getUbicacion().getTipoUbicacion()
            );
            dto.setUbicacion(u);
        }
        return dto;
    }

}
