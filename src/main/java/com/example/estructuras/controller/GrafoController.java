package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.GrafoResponseDto;
import com.example.estructuras.service.GrafoNoDirigidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/grafo")
public class GrafoController {
    private final GrafoNoDirigidoService grafoService;

    public GrafoController(GrafoNoDirigidoService  grafoService) {
        this.grafoService = grafoService;
    }

    @GetMapping
    public ResponseEntity<GrafoResponseDto> obtenerGrafo() {
        try {
            GrafoResponseDto grafo = grafoService.obtenerGrafo();
            return ResponseEntity.ok(grafo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
