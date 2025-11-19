package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.RegisterRequestDto;
import com.example.estructuras.Mapping.dto.UsuarioResponseDto;
import com.example.estructuras.Mapping.dto.EvacuacionDetalladaResponseDto;
import com.example.estructuras.service.OperadorEmergenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operadores")
public class OperadorEmergenciaController {

    private final OperadorEmergenciaService operadorService;

    public OperadorEmergenciaController(OperadorEmergenciaService operadorService) {
        this.operadorService = operadorService;
    }

    // ========== ENDPOINTS CRUD DE OPERADORES ==========

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listAll() throws IOException {
        return ResponseEntity.ok(operadorService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable String id) throws IOException {
        UsuarioResponseDto dto = operadorService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody RegisterRequestDto dto) throws IOException {
        UsuarioResponseDto created = operadorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> update(@PathVariable String id, @RequestBody RegisterRequestDto dto) throws IOException {
        UsuarioResponseDto updated = operadorService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws IOException {
        boolean removed = operadorService.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // ========== ENDPOINTS DE OPERACIONES DE EMERGENCIA ==========

    @GetMapping("/monitorear-ubicaciones")
    public ResponseEntity<List<String>> monitorearUbicaciones() throws IOException {
        List<String> resultado = operadorService.monitorearUbicaciones();
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/desastres/{idDesastre}/actualizar")
    public ResponseEntity<Map<String, String>> actualizarSituacion(
            @PathVariable String idDesastre,
            @RequestBody Map<String, Integer> datos) throws IOException {

        Integer personasAfectadas = datos.get("personasAfectadas");
        Integer magnitud = datos.get("magnitud");

        if (personasAfectadas == null || magnitud == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Se requieren personasAfectadas y magnitud"));
        }

        String mensaje = operadorService.actualizarSituacion(idDesastre, personasAfectadas, magnitud);
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @GetMapping("/gestionar-evacuaciones")
    public ResponseEntity<List<String>> gestionarEvacuaciones() throws IOException {
        List<String> resultado = operadorService.gestionarEvacuaciones();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/gestionar-evacuaciones/detallado")
    public ResponseEntity<EvacuacionDetalladaResponseDto> gestionarEvacuacionesDetallado() {
        try {
            EvacuacionDetalladaResponseDto respuesta = operadorService.obtenerEvacuacionesDetalladasConResumen();
            if (respuesta.getDetalle() == null || respuesta.getDetalle().isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(respuesta);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
