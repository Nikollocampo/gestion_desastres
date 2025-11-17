package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.DesastreRequestDto;
import com.example.estructuras.Mapping.dto.DesastreResponseDto;
import com.example.estructuras.Mapping.dto.EquipoResponseDto;
import com.example.estructuras.model.Desastre;
import com.example.estructuras.model.Equipo;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.EquipoJsonRepository;
import com.example.estructuras.repository.UbicacionJsonRepository;
import com.example.estructuras.service.DesastreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/desastres")
public class DesastreController {
    private final DesastreService desastreService;
    private final EquipoJsonRepository equipoRepository;
    private final UbicacionJsonRepository ubicacionRepository;

    public DesastreController(DesastreService desastreService, EquipoJsonRepository equipoRepository, UbicacionJsonRepository ubicacionRepository) {
        this.desastreService = desastreService;
        this.equipoRepository = equipoRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    // Obtener todos los desastres
    @GetMapping("/get")
    public ResponseEntity<List<DesastreResponseDto>> obtenerTodos() throws IOException {
        List<DesastreResponseDto> response = desastreService.obtenerTodos();
        return ResponseEntity.ok(response);
    }

    // Obtener un desastre por ID usando POST y body
    @PostMapping("/id")
    public ResponseEntity<DesastreResponseDto> obtenerPorId(@RequestBody Map<String, String> body) throws IOException {
        String id = body.get("id");
        DesastreResponseDto response = desastreService.obtenerPorId(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    // Registrar un nuevo desastre
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody DesastreRequestDto dto) throws IOException {
        // Buscar ubicación
        Ubicacion ubicacion = ubicacionRepository.findById(dto.getIdUbicacion()).orElse(null);
        if (ubicacion == null) {
            return ResponseEntity.badRequest().build();
        }
        // Buscar equipos
        List<Equipo> equipos = new ArrayList<>();
        if (dto.getEquiposIds() != null) {
            for (String idEquipo : dto.getEquiposIds()) {
                equipoRepository.findById(idEquipo).ifPresent(equipos::add);
            }
        }
        // Crear el desastre
        Desastre desastre = new Desastre(
            dto.getMagnitud(),
            dto.getNombre(),
            dto.getIdDesastre(),
            dto.getTipoDesastre(),
            dto.getPersonasAfectadas(),
            dto.getFecha(),
            ubicacion
        );
        desastre.setEquiposAsignados(equipos);
        desastreService.agregarDesastre(desastre);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Eliminar un desastre por ID
    @DeleteMapping("/id")
    public ResponseEntity<Void> eliminar(@PathVariable String id) throws IOException {
        boolean eliminado = desastreService.eliminarDesastre(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener equipos asignados a un desastre
    @GetMapping("/{id}/equipos")
    public ResponseEntity<List<EquipoResponseDto>> obtenerEquiposAsignados(@PathVariable String id) throws IOException {
        DesastreResponseDto desastre = desastreService.obtenerPorId(id);
        if (desastre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(desastre.getEquiposAsignados());
    }

    @GetMapping("/prioridad")
    public ResponseEntity<List<DesastreResponseDto>> obtenerDesastresPorPrioridad() throws IOException {
        List<DesastreResponseDto> desastres = desastreService.obtenerDesastresPorPrioridad();
        return ResponseEntity.ok(desastres);
    }

    /**
     * Obtiene el desastre más urgente (mayor prioridad).
     */
    @GetMapping("/mas-urgente")
    public ResponseEntity<DesastreResponseDto> obtenerDesastreMasUrgente() throws IOException {
        DesastreResponseDto desastre = desastreService.obtenerDesastreMasUrgente();
        if (desastre == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(desastre);
    }

    /**
     * Obtiene los N desastres más urgentes.
     * @param cantidad Número de desastres a retornar
     */
    @GetMapping("/top-urgentes/{cantidad}")
    public ResponseEntity<List<DesastreResponseDto>> obtenerTopDesastresUrgentes(@PathVariable int cantidad) throws IOException {
        List<DesastreResponseDto> desastres = desastreService.obtenerTopDesastresUrgentes(cantidad);
        return ResponseEntity.ok(desastres);
    }

    /**
     * Obtiene desastres filtrados por nivel de prioridad.
     * @param nivel Nivel de prioridad: "CRITICO", "ALTO", "MEDIO", "BAJO"
     */
    @GetMapping("/nivel-prioridad/{nivel}")
    public ResponseEntity<List<DesastreResponseDto>> obtenerDesastresPorNivelPrioridad(@PathVariable String nivel) throws IOException {
        List<DesastreResponseDto> desastres = desastreService.obtenerDesastresPorNivelPrioridad(nivel);
        return ResponseEntity.ok(desastres);
    }
}
