package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.RutaRequestDto;
import com.example.estructuras.Mapping.dto.RutaResponseDto;
import com.example.estructuras.service.RutaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {
    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<RutaResponseDto> crear(@RequestBody RutaRequestDto dto) throws IOException {
        RutaResponseDto response = rutaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RutaResponseDto>> listar() {
        return ResponseEntity.ok(rutaService.listar());
    }
}
