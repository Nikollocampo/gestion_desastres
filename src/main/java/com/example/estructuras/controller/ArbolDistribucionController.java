package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.ArbolDistribucionRequestDto;
import com.example.estructuras.Mapping.dto.ArbolDistribucionResponseDto;
import com.example.estructuras.Mapping.dto.RecursoRequestDto;
import com.example.estructuras.model.Recurso;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.service.ArbolDistribucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/arbol-distribucion")
public class ArbolDistribucionController {
    @Autowired
    private ArbolDistribucionService arbolService;

    @GetMapping
    public ResponseEntity<List<ArbolDistribucionResponseDto>> getAll() {
        try {
            List<ArbolDistribucionResponseDto> arboles = arbolService.getAll();
            return ResponseEntity.ok(arboles);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbolDistribucionResponseDto> getById(@PathVariable String id) {
        try {
            ArbolDistribucionResponseDto arbol = arbolService.getById(id);
            if (arbol == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(arbol);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<ArbolDistribucionResponseDto> crear(@RequestBody ArbolDistribucionRequestDto request) {
        try {
            ArbolDistribucionResponseDto arbol = arbolService.crear(request);
            return ResponseEntity.ok(arbol);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}/raiz/{raizId}")
    public ResponseEntity<ArbolDistribucionResponseDto> actualizarRaiz(
            @PathVariable String id,
            @PathVariable String raizId) {
        try {
            ArbolDistribucionResponseDto arbol = arbolService.actualizarRaiz(id, raizId);
            if (arbol == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(arbol);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        try {
            boolean eliminado = arbolService.eliminar(id);
            if (eliminado) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/id/asignar-recursos")
    public ResponseEntity<Map<String, Integer>> asignarRecursos(
            @PathVariable String id,
            @RequestBody List<RecursoRequestDto> recursosDto) {
        try {
            List<Recurso> recursos = recursosDto.stream()
                .map(this::toRecurso)
                .collect(Collectors.toList());

            Map<TipoRecurso, Integer> resultado = arbolService.asignarRecursos(id, recursos);

            // Convertir a Map<String, Integer> para el JSON
            Map<String, Integer> resultadoDto = new HashMap<>();
            resultado.forEach((k, v) -> resultadoDto.put(k.name(), v));

            return ResponseEntity.ok(resultadoDto);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/id/estructura")
    public ResponseEntity<Map<String, Object>> obtenerEstructura(@PathVariable String id) {
        try {
            Map<String, Object> estructura = arbolService.obtenerEstructura(id);
            if (estructura == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(estructura);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/id/imprimir")
    public ResponseEntity<String> imprimirEstructura(@PathVariable String id) {
        try {
            String estructura = arbolService.imprimirEstructura(id);
            return ResponseEntity.ok(estructura);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private Recurso toRecurso(RecursoRequestDto dto) {
        return new Recurso(
            dto.getId(),
            dto.getNombre(),
            TipoRecurso.valueOf(dto.getTipo()),
            dto.getCantidad()
        );
    }
}

