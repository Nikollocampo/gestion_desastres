package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.*;
import com.example.estructuras.model.Recurso;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.service.MapaRecursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mapa-recursos")
public class MapaRecursosController {

    @Autowired
    private MapaRecursosService mapaRecursosService;

    @PostMapping("/inicializar")
    public ResponseEntity<String> inicializar() {
        try {
            mapaRecursosService.inicializarMapa();
            return ResponseEntity.ok("Mapa de recursos inicializado correctamente");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al inicializar el mapa: " + e.getMessage());
        }
    }

    @GetMapping("/ubicacion/ubicacionId")
    public ResponseEntity<InventarioUbicacionResponseDto> obtenerRecursosPorUbicacion(@PathVariable String ubicacionId) {
        Map<TipoRecurso, Recurso> recursos = mapaRecursosService.obtenerRecursosPorUbicacion(ubicacionId);

        Map<String, RecursoResponseDto> recursosDto = new HashMap<>();
        recursos.forEach((tipo, recurso) -> {
            RecursoResponseDto dto = convertirARecursoDto(recurso);
            recursosDto.put(tipo.name(), dto);
        });

        InventarioUbicacionResponseDto response = new InventarioUbicacionResponseDto(
            ubicacionId,
            "Ubicación " + ubicacionId, // Podrías obtener el nombre real de la ubicación
            recursosDto
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ubicacion/ubicacionId/recurso/{tipoRecurso}")
    public ResponseEntity<RecursoResponseDto> obtenerRecursoEspecifico(
            @PathVariable String ubicacionId,
            @PathVariable String tipoRecurso) {
        TipoRecurso tipo = TipoRecurso.valueOf(tipoRecurso);
        Recurso recurso = mapaRecursosService.obtenerRecursoEspecifico(ubicacionId, tipo);

        if (recurso == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertirARecursoDto(recurso));
    }

    @PostMapping("/ubicacion/{ubicacionId}/recurso")
    public ResponseEntity<String> agregarRecurso(
            @PathVariable String ubicacionId,
            @RequestBody RecursoRequestDto recursoDto) {
        Recurso recurso = convertirARecurso(recursoDto);
        boolean resultado = mapaRecursosService.agregarRecurso(ubicacionId, recurso);

        if (resultado) {
            return ResponseEntity.ok("Recurso agregado correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo agregar el recurso. Ubicación no encontrada.");
        }
    }

    @PostMapping("/ubicacion/ubicacionId/consumir")
    public ResponseEntity<String> consumirRecurso(
            @PathVariable String ubicacionId,
            @RequestBody ConsumirRecursoRequestDto request) {
        TipoRecurso tipo = TipoRecurso.valueOf(request.getTipoRecurso());
        boolean resultado = mapaRecursosService.consumirRecurso(ubicacionId, tipo, request.getCantidad());

        if (resultado) {
            return ResponseEntity.ok("Recurso consumido correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo consumir el recurso");
        }
    }

    @PostMapping("/transferir")
    public ResponseEntity<String> transferirRecurso(@RequestBody TransferirRecursoRequestDto request) {
        TipoRecurso tipo = TipoRecurso.valueOf(request.getTipoRecurso());
        boolean resultado = mapaRecursosService.transferirRecurso(
            request.getOrigenId(),
            request.getDestinoId(),
            tipo,
            request.getCantidad()
        );

        if (resultado) {
            return ResponseEntity.ok("Recurso transferido correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo transferir el recurso");
        }
    }

    @GetMapping("/ubicaciones")
    public ResponseEntity<List<UbicacionResponseDto>> obtenerUbicaciones() {
        Set<Ubicacion> ubicaciones = mapaRecursosService.obtenerTodasLasUbicaciones();
        List<UbicacionResponseDto> ubicacionesDto = ubicaciones.stream()
            .map(this::convertirAUbicacionDto)
            .collect(Collectors.toList());

        return ResponseEntity.ok(ubicacionesDto);
    }

    @GetMapping("/inventario-completo")
    public ResponseEntity<List<InventarioUbicacionResponseDto>> obtenerInventarioCompleto() {
        Map<String, Map<TipoRecurso, Recurso>> inventario = mapaRecursosService.obtenerInventarioCompleto();

        List<InventarioUbicacionResponseDto> inventarioDto = new ArrayList<>();
        inventario.forEach((ubicacionId, recursos) -> {
            Map<String, RecursoResponseDto> recursosDto = new HashMap<>();
            recursos.forEach((tipo, recurso) -> {
                recursosDto.put(tipo.name(), convertirARecursoDto(recurso));
            });

            inventarioDto.add(new InventarioUbicacionResponseDto(
                ubicacionId,
                "Ubicación " + ubicacionId,
                recursosDto
            ));
        });

        return ResponseEntity.ok(inventarioDto);
    }

    @GetMapping("/buscar/tipoRecurso")
    public ResponseEntity<Map<String, Integer>> buscarRecursoPorTipo(@PathVariable String tipoRecurso) {
        TipoRecurso tipo = TipoRecurso.valueOf(tipoRecurso);
        Map<String, Integer> resultado = mapaRecursosService.buscarRecursoPorTipo(tipo);

        return ResponseEntity.ok(resultado);
    }

    // Métodos auxiliares de conversión
    private RecursoResponseDto convertirARecursoDto(Recurso recurso) {
        RecursoResponseDto dto = new RecursoResponseDto();
        dto.setId(recurso.getId());
        dto.setNombre(recurso.getNombre());
        dto.setTipo(recurso.getTipo().name());
        dto.setCantidad(recurso.getCantidad());
        if (recurso.getUbicacion() != null) {
            dto.setUbicacion(convertirAUbicacionDto(recurso.getUbicacion()));
        }
        return dto;
    }

    private Recurso convertirARecurso(RecursoRequestDto dto) {
        return new Recurso(
            dto.getId(),
            dto.getNombre(),
            dto.getTipo(), // Usar el enum directamente
            dto.getCantidad()
        );
    }

    private UbicacionResponseDto convertirAUbicacionDto(Ubicacion ubicacion) {
        return new UbicacionResponseDto(
                ubicacion.getId(),
                ubicacion.getNombre(),
                ubicacion.getCalle(),
                ubicacion.getCarrera(),
                ubicacion.getTipoUbicacion(),
                ubicacion.getLat(),
                ubicacion.getLng()
        );
    }
}
