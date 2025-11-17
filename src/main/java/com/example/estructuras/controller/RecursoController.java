package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.ObtenerRecursoPorIdRequestDto;
import com.example.estructuras.Mapping.dto.RecursoRequestDto;
import com.example.estructuras.Mapping.dto.RecursoResponseDto;
import com.example.estructuras.Mapping.dto.CantidadRequestDto;
import com.example.estructuras.service.RecursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/recurso")
public class RecursoController {

    private final RecursoService recursoService;
    private static final Logger logger = LoggerFactory.getLogger(RecursoController.class);

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

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<RecursoRequestDto> actualizar(@PathVariable String id, @RequestBody RecursoRequestDto dto) throws IOException {
        logger.info("Llamada a /api/recurso/actualizar/{}", id);
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        dto.setId(id); // Asegura que el id de la ruta se use
        try {
            return ResponseEntity.ok(recursoService.actualizar(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) throws IOException {
        recursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/agregar/{id}")
    public ResponseEntity<RecursoRequestDto> agregarCantidad(@PathVariable String id, @RequestBody CantidadRequestDto cantidadDto) throws IOException {
        if (cantidadDto == null || cantidadDto.getCantidad() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(recursoService.agregarCantidad(id, cantidadDto.getCantidad()));
    }


    @PutMapping("/consumir/{id}")
    public ResponseEntity<String> consumir(@PathVariable String id, @RequestBody CantidadRequestDto cantidadDto) throws IOException {
        if (cantidadDto == null || cantidadDto.getCantidad() == null) {
            return ResponseEntity.badRequest().body("Cantidad no especificada");
        }
        boolean resultado = recursoService.consumir(id, cantidadDto.getCantidad());
        if (resultado) {
            return ResponseEntity.ok("Consumo realizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cantidad insuficiente para consumir");
        }
    }

}
