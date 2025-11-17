package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.EquipoRequestDto;
import com.example.estructuras.Mapping.dto.EquipoResponseDto;
import com.example.estructuras.service.EquipoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/equipos")
public class EquipoController {
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<EquipoResponseDto> crear(@RequestBody EquipoRequestDto dto) throws IOException {
        EquipoResponseDto response = equipoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EquipoResponseDto>> listar() throws IOException {
        return ResponseEntity.ok(equipoService.listar());
    }
}
