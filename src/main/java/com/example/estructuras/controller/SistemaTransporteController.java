package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.TransporteRecursosRequestDto;
import com.example.estructuras.Mapping.dto.TransporteRecursosResponseDto;
import com.example.estructuras.service.SistemaTransporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transporte")
public class SistemaTransporteController {
    private final SistemaTransporteService transporteService;

    public SistemaTransporteController(SistemaTransporteService transporteService) {
        this.transporteService = transporteService;
    }

    @PostMapping("/transportar")
    public ResponseEntity<TransporteRecursosResponseDto> transportarRecursos(@RequestBody TransporteRecursosRequestDto dto) throws IOException {
        TransporteRecursosResponseDto response = transporteService.transportarRecursos(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ruta")
    public ResponseEntity<List<String>> obtenerRutaMasCorta(@RequestParam String origenId, @RequestParam String destinoId) throws IOException {
        List<String> ruta = transporteService.obtenerRutaMasCorta(origenId, destinoId);
        return ResponseEntity.ok(ruta);
    }

    @GetMapping("/distancias/origenId")
    public ResponseEntity<java.util.Map<String, Float>> calcularDistanciasDesde(@PathVariable String origenId) throws IOException {
        java.util.Map<String, Float> distancias = transporteService.calcularDistanciasDesde(origenId);
        return ResponseEntity.ok(distancias);
    }
}
