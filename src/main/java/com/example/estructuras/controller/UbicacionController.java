package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.UbicacionRequestDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.service.UbicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {
    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @PostMapping("/crear")
    public ResponseEntity<UbicacionResponseDto> crear(@RequestBody UbicacionRequestDto dto) throws IOException {
        UbicacionResponseDto response = ubicacionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UbicacionResponseDto>> listar() throws IOException {
        return ResponseEntity.ok(ubicacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionResponseDto> obtenerPorId(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(ubicacionService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionResponseDto> actualizar(@PathVariable String id, @RequestBody UbicacionRequestDto dto) throws IOException {
        return ResponseEntity.ok(ubicacionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) throws IOException {
        ubicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
