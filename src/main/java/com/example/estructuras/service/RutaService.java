package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.RutaRequestDto;
import com.example.estructuras.Mapping.dto.RutaResponseDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.Ruta;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RutaService {
    private final UbicacionJsonRepository ubicacionRepository;
    private final List<Ruta> rutas = new ArrayList<>();

    public RutaService(UbicacionJsonRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    public RutaResponseDto crear(RutaRequestDto dto) throws IOException {
        Ubicacion origen = obtenerUbicacion(dto.getOrigenId());
        Ubicacion destino = obtenerUbicacion(dto.getDestinoId());

        Ruta ruta = new Ruta(origen, destino, dto.getDistancia());
        rutas.add(ruta);

        return toResponseDto(ruta);
    }

    public List<RutaResponseDto> listar() {
        List<RutaResponseDto> lista = new ArrayList<>();
        for (Ruta ruta : rutas) {
            lista.add(toResponseDto(ruta));
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

    private RutaResponseDto toResponseDto(Ruta ruta) {
        return new RutaResponseDto(
                toUbicacionDto(ruta.getOrigen()),
                toUbicacionDto(ruta.getDestino()),
                ruta.getDistancia(),
                calcularPeso(ruta.getDistancia())
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

    private int calcularPeso(float distancia) {
        int peso = 0;
        int comparador = 5;
        if (distancia <= 0) return peso;
        for (int i = 1; i <= 10; i++) {
            if (distancia >= comparador) {
                peso = i;
                comparador += 5;
            } else {
                break;
            }
        }
        return peso;
    }

}
