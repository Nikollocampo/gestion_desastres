package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.ObtenerRecursoPorIdRequestDto;
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

    @PostMapping("/crear")
    public ResponseEntity<RecursoResponseDto> crear(@RequestBody RecursoRequestDto dto) throws IOException {
        RecursoResponseDto response = recursoService.crearYRetornarResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RecursoRequestDto>> listar() throws IOException {
        return ResponseEntity.ok(recursoService.listar());
    }


    @PostMapping("/id")
    public ResponseEntity<RecursoResponseDto> obtenerPorId(@RequestBody ObtenerRecursoPorIdRequestDto request) throws IOException {
        try {
            RecursoResponseDto response = recursoService.obtenerResponsePorId(request.getId());
            if (response == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<RecursoRequestDto> actualizar(@PathVariable String id, @RequestBody RecursoRequestDto dto) throws IOException {
        dto.setId(id); // Asegura que el ID del path se use en la actualización
        return ResponseEntity.ok(recursoService.actualizar(dto));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) throws IOException {
        recursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/agregar/{id}")
    public ResponseEntity<RecursoRequestDto> agregarCantidad(@PathVariable String id, @RequestParam int cantidad) throws IOException {
        return ResponseEntity.ok(recursoService.agregarCantidad(id, cantidad));
    }

    @PatchMapping("/consumir/{id}")
    public ResponseEntity<String> consumir(@PathVariable String id, @RequestParam int cantidad) throws IOException {
        boolean resultado = recursoService.consumir(id, cantidad);
        if (resultado) {
            return ResponseEntity.ok("Consumo realizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cantidad insuficiente para consumir");
        }
    }

}
