package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.RegisterRequestDto;
import com.example.estructuras.Mapping.dto.UsuarioResponseDto;
import com.example.estructuras.Mapping.dto.EvacuacionResponseDto;
import com.example.estructuras.exception.UserAlreadyExistsException;
import com.example.estructuras.model.*;
import com.example.estructuras.repository.UserJsonRepository;
import com.example.estructuras.repository.DesastreJsonRepository;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperadorEmergenciaService {
    private final UserJsonRepository userRepo;
    private final DesastreJsonRepository desastreRepo;
    private final UbicacionJsonRepository ubicacionRepo;

    public OperadorEmergenciaService(UserJsonRepository userRepo,
                                     DesastreJsonRepository desastreRepo,
                                     UbicacionJsonRepository ubicacionRepo) {
        this.userRepo = userRepo;
        this.desastreRepo = desastreRepo;
        this.ubicacionRepo = ubicacionRepo;
    }

    // Listar todos los operadores de emergencia
    public List<UsuarioResponseDto> listAll() throws IOException {
        return userRepo.findAll().stream()
                .filter(u -> u.getRol() == Rol.OPERADOR_EMERGENCIA)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Obtener operador por id
    public UsuarioResponseDto getById(String id) throws IOException {
        Optional<Usuario> opt = userRepo.findAll().stream()
                .filter(u -> u.getRol() == Rol.OPERADOR_EMERGENCIA && u.getId().equals(id))
                .findFirst();
        return opt.map(this::toDto).orElse(null);
    }

    // Crear operador de emergencia (forzando rol)
    public UsuarioResponseDto create(RegisterRequestDto dto) throws IOException {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email requerido");
        }
        boolean emailExists = userRepo.findAll().stream().anyMatch(u -> u.getEmail().equals(dto.getEmail()));
        if (emailExists) {
            throw new UserAlreadyExistsException("Usuario ya existe con ese email");
        }
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID().toString());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(Rol.OPERADOR_EMERGENCIA);
        userRepo.add(usuario);
        return toDto(usuario);
    }

    // Actualizar operador de emergencia (solo nombre y contraseña)
    public UsuarioResponseDto update(String id, RegisterRequestDto dto) throws IOException {
        List<Usuario> usuarios = userRepo.findAll();
        Usuario usuario = usuarios.stream()
                .filter(u -> u.getId().equals(id) && u.getRol() == Rol.OPERADOR_EMERGENCIA)
                .findFirst()
                .orElse(null);
        if (usuario == null) return null;
        // Si cambia email, verificar duplicado
        if (dto.getEmail() != null && !dto.getEmail().equals(usuario.getEmail())) {
            boolean emailExists = usuarios.stream().anyMatch(u -> u.getEmail().equals(dto.getEmail()));
            if (emailExists) {
                throw new UserAlreadyExistsException("Usuario ya existe con ese email");
            }
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getContrasena() != null) usuario.setContrasena(dto.getContrasena());
        // Mantener rol
        userRepo.saveAll(usuarios);
        return toDto(usuario);
    }

    // Eliminar operador por id
    public boolean delete(String id) throws IOException {
        List<Usuario> usuarios = userRepo.findAll();
        boolean removed = usuarios.removeIf(u -> u.getRol() == Rol.OPERADOR_EMERGENCIA && u.getId().equals(id));
        if (removed) userRepo.saveAll(usuarios);
        return removed;
    }

    private UsuarioResponseDto toDto(Usuario u) {
        return new UsuarioResponseDto(u.getId(), u.getEmail(), u.getNombre(), u.getRol());
    }

    // ========== MÉTODOS DE NEGOCIO DEL OPERADOR DE EMERGENCIA ==========

    /**
     * Monitorear ubicaciones - muestra información de ubicaciones disponibles
     */
    public List<String> monitorearUbicaciones() throws IOException {
        List<Ubicacion> ubicaciones = ubicacionRepo.findAll();
        List<String> resultado = new ArrayList<>();
        resultado.add("Monitoreando ubicaciones:");
        for (Ubicacion u : ubicaciones) {
            resultado.add(u.getNombre() + " (" + u.getCalle() + " y " + u.getCarrera() + ")");
        }
        return resultado;
    }

    /**
     * Actualizar situación de un desastre
     */
    public String actualizarSituacion(String idDesastre, int nuevasPersonasAfectadas, int nuevaMagnitud) throws IOException {
        List<Desastre> desastres = desastreRepo.findAll();
        Desastre desastre = desastres.stream()
                .filter(d -> d.getIdDesastre().equals(idDesastre))
                .findFirst()
                .orElse(null);

        if (desastre == null) {
            throw new IllegalArgumentException("Desastre no encontrado con ID: " + idDesastre);
        }

        desastre.setPersonasAfectadas(nuevasPersonasAfectadas);
        desastre.setMagnitud(nuevaMagnitud);
        desastreRepo.saveAll(desastres);

        return "Situación actualizada del desastre " + desastre.getNombre() +
                " | Magnitud: " + nuevaMagnitud +
                " | Personas afectadas: " + nuevasPersonasAfectadas;
    }

    /**
     * Gestionar evacuaciones por prioridad y riesgo de zona
     */
    public List<String> gestionarEvacuaciones() throws IOException {
        List<Desastre> desastres = desastreRepo.findAll();
        List<String> resultado = new ArrayList<>();

        if (desastres == null || desastres.isEmpty()) {
            resultado.add("No hay desastres para evacuar.");
            return resultado;
        }

        // Calcular el riesgo (prioridad) más alto por zona
        Map<Ubicacion, String> riesgoPorZona = new HashMap<>();
        for (Desastre d : desastres) {
            if (d.getUbicacion() == null) continue;
            String prioridadActual = d.asignarPrioridad();
            String prioridadPrevia = riesgoPorZona.get(d.getUbicacion());

            if (prioridadPrevia == null ||
                    prioridadValor(prioridadActual) > prioridadValor(prioridadPrevia)) {
                riesgoPorZona.put(d.getUbicacion(), prioridadActual);
            }
        }

        // Comparador: Riesgo de zona > Personas afectadas > Magnitud
        Comparator<Desastre> cmp = (a, b) -> {
            String riesgoA = riesgoPorZona.getOrDefault(a.getUbicacion(), "Baja");
            String riesgoB = riesgoPorZona.getOrDefault(b.getUbicacion(), "Baja");
            int cmpRiesgo = Integer.compare(
                    prioridadValor(riesgoB),
                    prioridadValor(riesgoA)
            );
            if (cmpRiesgo != 0) return cmpRiesgo;

            int cmpPersonas = Integer.compare(
                    b.getPersonasAfectadas(),
                    a.getPersonasAfectadas()
            );
            if (cmpPersonas != 0) return cmpPersonas;

            return Integer.compare(b.getMagnitud(), a.getMagnitud());
        };

        ColaPrioridad<Desastre> cola = new ColaPrioridad<>(cmp);
        cola.encolarTodos(desastres);

        resultado.add("=== GESTIÓN DE EVACUACIÓN POR RIESGO DE ZONA ===");
        while (!cola.estaVacia()) {
            Desastre d = cola.atenderSiguiente();
            if (d == null) break;

            String riesgoZona = riesgoPorZona.getOrDefault(d.getUbicacion(), "Baja");
            resultado.add("→ Evacuando: " + d.getNombre()
                    + " | Zona: " + (d.getUbicacion() != null ? d.getUbicacion().getNombre() : "N/D")
                    + " | Riesgo Zona: " + riesgoZona
                    + " | Prioridad Desastre: " + d.asignarPrioridad()
                    + " | Personas: " + d.getPersonasAfectadas()
                    + " | Magnitud: " + d.getMagnitud());
        }
        resultado.add("Evacuación finalizada.");
        return resultado;
    }

    /**
     * Gestionar evacuaciones por prioridad y riesgo de zona, retornando detalle
     */
    public List<EvacuacionResponseDto> gestionarEvacuacionesDetallado() throws IOException {
        List<Desastre> desastres = desastreRepo.findAll();
        List<EvacuacionResponseDto> resultado = new ArrayList<>();
        if (desastres == null || desastres.isEmpty()) {
            return resultado;
        }
        Map<Ubicacion, String> riesgoPorZona = new HashMap<>();
        for (Desastre d : desastres) {
            if (d.getUbicacion() == null) continue;
            String prioridadActual = d.asignarPrioridad();
            String prioridadPrevia = riesgoPorZona.get(d.getUbicacion());
            if (prioridadPrevia == null || prioridadValor(prioridadActual) > prioridadValor(prioridadPrevia)) {
                riesgoPorZona.put(d.getUbicacion(), prioridadActual);
            }
        }
        Comparator<Desastre> cmp = (a, b) -> {
            String riesgoA = riesgoPorZona.getOrDefault(a.getUbicacion(), "Baja");
            String riesgoB = riesgoPorZona.getOrDefault(b.getUbicacion(), "Baja");
            int cmpRiesgo = Integer.compare(prioridadValor(riesgoB), prioridadValor(riesgoA));
            if (cmpRiesgo != 0) return cmpRiesgo;
            int cmpPersonas = Integer.compare(b.getPersonasAfectadas(), a.getPersonasAfectadas());
            if (cmpPersonas != 0) return cmpPersonas;
            return Integer.compare(b.getMagnitud(), a.getMagnitud());
        };
        ColaPrioridad<Desastre> cola = new ColaPrioridad<>(cmp);
        cola.encolarTodos(desastres);
        while (!cola.estaVacia()) {
            Desastre d = cola.atenderSiguiente();
            if (d == null) break;
            String riesgoZona = riesgoPorZona.getOrDefault(d.getUbicacion(), "Baja");
            int personasEvacuadas = Math.min(d.getPersonasAfectadas(), calcularPersonasEvacuadasEstimadas(d));
            resultado.add(new EvacuacionResponseDto(
                    UUID.randomUUID().toString(),
                    d.getIdDesastre(),
                    d.getNombre(),
                    d.getUbicacion() != null ? d.getUbicacion().getId() : null,
                    d.getUbicacion() != null ? d.getUbicacion().getNombre() : null,
                    d.asignarPrioridad(),
                    riesgoZona,
                    d.getPersonasAfectadas(),
                    personasEvacuadas,
                    d.getMagnitud(),
                    java.time.LocalDateTime.now()
            ));
        }
        return resultado;
    }

    private int calcularPersonasEvacuadasEstimadas(Desastre d) {
        // Regla simple: porcentaje según prioridad
        String prioridad = d.asignarPrioridad();
        double factor = switch (prioridad) {
            case "Alta" -> 0.8;
            case "Media" -> 0.5;
            case "Baja" -> 0.3;
            default -> 0.2;
        };
        return (int) Math.round(d.getPersonasAfectadas() * factor);
    }

    private int prioridadValor(String prioridad) {
        return switch (prioridad) {
            case "Alta" -> 3;
            case "Media" -> 2;
            case "Baja" -> 1;
            default -> 0;
        };
    }
}
