package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.*;
import com.example.estructuras.service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PostMapping("/ruta/definir")
    public ResponseEntity<RutaDetalleResponseDto> definirRuta(@RequestBody DefinirRutaRequestDto dto) throws IOException {
        return ResponseEntity.ok(administradorService.definirRuta(dto));
    }

    @PostMapping("/equipo/asignar")
    public ResponseEntity<AsignarEquipoResponseDto> asignarEquipo(@RequestBody AsignarEquipoRequestDto dto) throws IOException {
        return ResponseEntity.ok(administradorService.asignarEquipo(dto));
    }

    @PostMapping("/recursos/prioridad")
    public ResponseEntity<OperacionSimpleResponseDto> asignarRecursosPrioridad() throws IOException {
        return ResponseEntity.ok(administradorService.asignarRecursosPrioridad());
    }

    @GetMapping("/reporte")
    public ResponseEntity<OperacionSimpleResponseDto> generarReporte() throws IOException {
        return ResponseEntity.ok(administradorService.generarReporte());
    }
}

