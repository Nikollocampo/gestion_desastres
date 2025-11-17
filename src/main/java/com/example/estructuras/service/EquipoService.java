package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.EquipoRequestDto;
import com.example.estructuras.Mapping.dto.EquipoResponseDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.Equipo;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.EquipoJsonRepository;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {
    private final EquipoJsonRepository equipoRepository;
    private final UbicacionJsonRepository ubicacionRepository;

    public EquipoService(EquipoJsonRepository equipoRepository, UbicacionJsonRepository ubicacionRepository) {
        this.equipoRepository = equipoRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public EquipoResponseDto crear(EquipoRequestDto dto) throws IOException {
        Ubicacion ubicacion = obtenerUbicacion(dto.getUbicacionId());

        Equipo equipo = new Equipo(dto.getIdEquipo(), dto.getIntegrantesDisponibles(), dto.getTipoEquipo());
        equipo.setUbicacion(ubicacion);

        equipoRepository.add(equipo);
        return toResponseDto(equipo);
    }

    public List<EquipoResponseDto> listar() throws IOException {
        List<EquipoResponseDto> lista = new ArrayList<>();
        for (Equipo equipo : equipoRepository.findAll()) {
            lista.add(toResponseDto(equipo));
        }
        return lista;
    }

    private Ubicacion obtenerUbicacion(String id) throws IOException {
        Optional<Ubicacion> ubicacion = ubicacionRepository.findById(id);
        if (ubicacion.isEmpty()) {
            throw new IllegalArgumentException("Ubicación no encontrada con ID: " + id);
        }
        return ubicacion.get();
    }

    private EquipoResponseDto toResponseDto(Equipo equipo) {
        return new EquipoResponseDto(
                equipo.getIdEquipo(),
                equipo.getIntegrantesDisponibles(),
                equipo.getTipoEquipo(),
                toUbicacionDto(equipo.getUbicacion())
        );
    }

    private UbicacionResponseDto toUbicacionDto(Ubicacion u) {
        return new UbicacionResponseDto(
                u.getId(),
                u.getNombre(),
                u.getCalle(),
                u.getCarrera(),
                u.getTipoUbicacion()
        );
    }

}
