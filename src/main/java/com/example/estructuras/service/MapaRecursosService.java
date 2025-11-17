package com.example.estructuras.service;

import com.example.estructuras.model.MapaRecursos;
import com.example.estructuras.model.Recurso;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.MapaRecursosJsonRepository;
import com.example.estructuras.repository.UbicacionJsonRepository;
import com.example.estructuras.repository.RecursoJsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MapaRecursosService {

    @Autowired
    private MapaRecursosJsonRepository mapaRecursosRepo;

    @Autowired
    private UbicacionJsonRepository ubicacionRepo;

    @Autowired
    private RecursoJsonRepository recursoRepo;

    /**
     * Crea un nuevo mapa de recursos
     */
    public MapaRecursos crearMapa(String id, String nombre) throws IOException {
        MapaRecursos mapa = new MapaRecursos(id, nombre);
        mapaRecursosRepo.save(mapa);
        return mapa;
    }

    /**
     * Obtiene un mapa por ID
     */
    public MapaRecursos obtenerMapaPorId(String id) throws IOException {
        return mapaRecursosRepo.findById(id).orElse(null);
    }

    /**
     * Agrega una ubicación al mapa
     */
    public boolean agregarUbicacion(String mapaId, String ubicacionId) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return false;

        Map<String, Map<String, Recurso>> mapaRecursos = mapa.getMapaRecursos();
        if (!mapaRecursos.containsKey(ubicacionId)) {
            mapaRecursos.put(ubicacionId, new HashMap<>());
            mapaRecursosRepo.save(mapa);
            return true;
        }
        return false;
    }

    /**
     * Agrega un recurso a una ubicación específica
     */
    public boolean agregarRecurso(String mapaId, String ubicacionId, Recurso recurso) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return false;

        Map<String, Map<String, Recurso>> mapaRecursos = mapa.getMapaRecursos();

        // Asegurar que la ubicación existe
        if (!mapaRecursos.containsKey(ubicacionId)) {
            mapaRecursos.put(ubicacionId, new HashMap<>());
        }

        Map<String, Recurso> recursosUbicacion = mapaRecursos.get(ubicacionId);
        String tipoRecurso = recurso.getTipo().name();

        if (recursosUbicacion.containsKey(tipoRecurso)) {
            // Si ya existe, actualizar cantidad
            Recurso existente = recursosUbicacion.get(tipoRecurso);
            existente.setCantidad(existente.getCantidad() + recurso.getCantidad());
        } else {
            // Si no existe, agregar nuevo
            recursosUbicacion.put(tipoRecurso, recurso);
        }

        mapaRecursosRepo.save(mapa);
        return true;
    }

    /**
     * Obtiene todos los recursos de una ubicación
     */
    public Map<String, Recurso> obtenerRecursosPorUbicacion(String mapaId, String ubicacionId) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return new HashMap<>();

        return mapa.getMapaRecursos().getOrDefault(ubicacionId, new HashMap<>());
    }

    /**
     * Obtiene un recurso específico de una ubicación
     */
    public Recurso obtenerRecursoEspecifico(String mapaId, String ubicacionId, TipoRecurso tipo) throws IOException {
        Map<String, Recurso> recursos = obtenerRecursosPorUbicacion(mapaId, ubicacionId);
        return recursos.get(tipo.name());
    }

    /**
     * Consume una cantidad de recurso de una ubicación
     */
    public boolean consumirRecurso(String mapaId, String ubicacionId, TipoRecurso tipo, int cantidad) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return false;

        Map<String, Recurso> recursos = mapa.getMapaRecursos().get(ubicacionId);
        if (recursos == null) return false;

        Recurso recurso = recursos.get(tipo.name());
        if (recurso == null) return false;

        if (recurso.getCantidad() >= cantidad) {
            recurso.setCantidad(recurso.getCantidad() - cantidad);
            mapaRecursosRepo.save(mapa);
            return true;
        }

        return false;
    }

    /**
     * Transfiere recursos entre dos ubicaciones
     */
    public boolean transferirRecurso(String mapaId, String origenId, String destinoId, TipoRecurso tipo, int cantidad) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return false;

        Map<String, Map<String, Recurso>> mapaRecursos = mapa.getMapaRecursos();

        // Verificar que ambas ubicaciones existen
        if (!mapaRecursos.containsKey(origenId) || !mapaRecursos.containsKey(destinoId)) {
            return false;
        }

        Map<String, Recurso> recursosOrigen = mapaRecursos.get(origenId);
        Map<String, Recurso> recursosDestino = mapaRecursos.get(destinoId);

        Recurso recursoOrigen = recursosOrigen.get(tipo.name());
        if (recursoOrigen == null || recursoOrigen.getCantidad() < cantidad) {
            return false;
        }

        // Consumir del origen
        recursoOrigen.setCantidad(recursoOrigen.getCantidad() - cantidad);

        // Agregar al destino
        Recurso recursoDestino = recursosDestino.get(tipo.name());
        if (recursoDestino != null) {
            recursoDestino.setCantidad(recursoDestino.getCantidad() + cantidad);
        } else {
            Recurso nuevo = new Recurso(
                recursoOrigen.getId() + "_transferred",
                recursoOrigen.getNombre(),
                tipo,
                cantidad
            );
            recursosDestino.put(tipo.name(), nuevo);
        }

        mapaRecursosRepo.save(mapa);
        return true;
    }

    /**
     * Obtiene todas las ubicaciones de un mapa
     */
    public Set<String> obtenerUbicaciones(String mapaId) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return new HashSet<>();

        return mapa.getMapaRecursos().keySet();
    }

    /**
     * Obtiene el inventario completo del mapa
     */
    public Map<String, Map<String, Recurso>> obtenerInventarioCompleto(String mapaId) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return new HashMap<>();

        return mapa.getMapaRecursos();
    }

    /**
     * Busca recursos disponibles de un tipo específico en todas las ubicaciones
     */
    public Map<String, Integer> buscarRecursoPorTipo(String mapaId, TipoRecurso tipo) throws IOException {
        MapaRecursos mapa = obtenerMapaPorId(mapaId);
        if (mapa == null) return new HashMap<>();

        Map<String, Integer> resultado = new HashMap<>();

        for (Map.Entry<String, Map<String, Recurso>> entry : mapa.getMapaRecursos().entrySet()) {
            String ubicacionId = entry.getKey();
            Recurso recurso = entry.getValue().get(tipo.name());

            if (recurso != null && recurso.getCantidad() > 0) {
                resultado.put(ubicacionId, recurso.getCantidad());
            }
        }

        return resultado;
    }

    /**
     * Elimina un mapa
     */
    public boolean eliminarMapa(String mapaId) throws IOException {
        return mapaRecursosRepo.deleteById(mapaId);
    }
}

