package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.RecursoRequestDto;
import com.example.estructuras.Mapping.dto.RecursoResponseDto;
import com.example.estructuras.service.RecursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/recurso")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    public ResponseEntity<RecursoRequestDto> crear(@RequestBody RecursoRequestDto dto) throws IOException {
        RecursoRequestDto response = recursoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RecursoRequestDto>> listar() throws IOException {
        return ResponseEntity.ok(recursoService.listar());
    }

    @GetMapping("/id")
    public ResponseEntity<RecursoRequestDto> obtenerPorId(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(recursoService.obtenerPorId(id));
    }

    @PutMapping("/id")
    public ResponseEntity<RecursoRequestDto> actualizar(@PathVariable String id, @RequestBody RecursoRequestDto dto) throws IOException {
        dto.setId(id); // Asegura que el ID del path se use en la actualización
        return ResponseEntity.ok(recursoService.actualizar(dto));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> eliminar(@PathVariable String id) throws IOException {
        recursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/agregar")
    public ResponseEntity<RecursoRequestDto> agregarCantidad(@PathVariable String id, @RequestParam int cantidad) throws IOException {
        return ResponseEntity.ok(recursoService.agregarCantidad(id, cantidad));
    }

    @PatchMapping("/consumir")
    public ResponseEntity<String> consumir(@PathVariable String id, @RequestParam int cantidad) throws IOException {
        boolean resultado = recursoService.consumir(id, cantidad);
        if (resultado) {
            return ResponseEntity.ok("Consumo realizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cantidad insuficiente para consumir");
        }
    }

}
