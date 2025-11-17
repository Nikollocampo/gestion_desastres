package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.NodoDistribucionRequestDto;
import com.example.estructuras.Mapping.dto.NodoDistribucionResponseDto;
import com.example.estructuras.Mapping.dto.RecursoRequestDto;
import com.example.estructuras.model.Recurso;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.service.NodoDistribucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nodo-distribucion")
public class NodoDistribucionController {
    @Autowired
    private NodoDistribucionService service;

    @GetMapping
    public ResponseEntity<List<NodoDistribucionResponseDto>> getAll() {
        List<NodoDistribucionResponseDto> dtos = service.getAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{ubicacionId}")
    public ResponseEntity<NodoDistribucionResponseDto> getByUbicacionId(@PathVariable String ubicacionId) {
        NodoDistribucionResponseDto dto = service.getByUbicacionId(ubicacionId);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<NodoDistribucionResponseDto> crear(@RequestBody NodoDistribucionRequestDto request) {
        NodoDistribucionResponseDto dto = service.create(request);
        if (dto == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{ubicacionId}")
    public ResponseEntity<NodoDistribucionResponseDto> actualizar(@PathVariable String ubicacionId, @RequestBody NodoDistribucionRequestDto request) {
        NodoDistribucionResponseDto dto = service.updateByUbicacionId(ubicacionId, request);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{ubicacionId}")
    public ResponseEntity<Void> eliminar(@PathVariable String ubicacionId) {
        boolean eliminado = service.deleteByUbicacionId(ubicacionId);
        if (eliminado) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    // Agregar necesidad a un nodo existente
    @PostMapping("/{ubicacionId}/necesidad")
    public ResponseEntity<NodoDistribucionResponseDto> agregarNecesidad(
            @PathVariable String ubicacionId,
            @RequestParam String tipoRecurso,
            @RequestParam int cantidad) {
        TipoRecurso tipo = TipoRecurso.valueOf(tipoRecurso);
        NodoDistribucionResponseDto dto = service.agregarNecesidad(ubicacionId, tipo, cantidad);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // Agregar un hijo a un nodo existente
    @PostMapping("/{ubicacionPadreId}/hijo/{ubicacionHijoId}")
    public ResponseEntity<NodoDistribucionResponseDto> agregarHijo(
            @PathVariable String ubicacionPadreId,
            @PathVariable String ubicacionHijoId) {
        NodoDistribucionResponseDto dto = service.agregarHijo(ubicacionPadreId, ubicacionHijoId);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // Asignar recursos disponibles a un nodo y sus hijos
    @PostMapping("/{ubicacionId}/asignar-recursos")
    public ResponseEntity<Map<String, Integer>> asignarRecursosDisponibles(
            @PathVariable String ubicacionId,
            @RequestBody List<RecursoRequestDto> recursosDto) {
        List<Recurso> recursos = recursosDto.stream().map(this::toRecurso).collect(Collectors.toList());
        Map<TipoRecurso, Integer> resultado = service.asignarRecursosDisponibles(ubicacionId, recursos);
        Map<String, Integer> resultadoDto = new HashMap<>();
        resultado.forEach((k, v) -> resultadoDto.put(k.name(), v));
        return ResponseEntity.ok(resultadoDto);
    }

    // Conversión a Recurso
    private Recurso toRecurso(RecursoRequestDto dto) {
        return new Recurso(dto.getId(), dto.getNombre(), TipoRecurso.valueOf(dto.getTipo()), dto.getCantidad());
    }
}

