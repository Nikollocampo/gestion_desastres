package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.*;
import com.example.estructuras.service.AdministradorService;
import com.example.estructuras.service.DesastreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdministradorController {

    private final AdministradorService administradorService;
    private final DesastreService desastreService;

    public AdministradorController(AdministradorService administradorService, DesastreService desastreService) {
        this.administradorService = administradorService;
        this.desastreService = desastreService;
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
    public ResponseEntity<List<AsignacionRecursoDto>> asignarRecursosPrioridad() throws IOException {
        return ResponseEntity.ok(administradorService.asignarRecursosPrioridad());
    }

    @GetMapping("/desastres/prioridad")
    public ResponseEntity<List<DesastreResponseDto>> listarDesastresPorPrioridad() throws IOException {
        return ResponseEntity.ok(desastreService.obtenerDesastresPorPrioridad());
    }

    @GetMapping("/reporte")
    public ResponseEntity<OperacionSimpleResponseDto> generarReporte() throws IOException {
        return ResponseEntity.ok(administradorService.generarReporte());
    }
}
