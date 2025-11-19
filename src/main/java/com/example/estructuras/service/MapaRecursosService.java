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

    private static final String DEFAULT_MAPA_ID = "default";

    // ================= API ORIGINAL USADA POR EL CONTROLLER =================

    /** Inicializa el mapa por defecto cargando ubicaciones y recursos existentes */
    public void inicializarMapa() throws IOException {
        MapaRecursos mapa = obtenerMapaPrincipal();
        // Cargar ubicaciones y asegurar entrada vacía
        for (Ubicacion u : ubicacionRepo.findAll()) {
            mapa.getMapaRecursos().computeIfAbsent(u.getId(), k -> new HashMap<>());
        }
        // Cargar recursos y ubicarlos según su ubicacion (si existe)
        for (Recurso r : recursoRepo.findAll()) {
            if (r.getUbicacion() != null) {
                String idUb = r.getUbicacion().getId();
                mapa.getMapaRecursos().computeIfAbsent(idUb, k -> new HashMap<>())
                        .put(r.getTipo().name(), r);
            }
        }
        guardarMapa(mapa);
    }

    /** Obtiene recursos por ubicación (mapa por defecto) */
    public Map<TipoRecurso, Recurso> obtenerRecursosPorUbicacion(String ubicacionId) {
        MapaRecursos mapa = obtenerMapaPrincipal();
        Map<String, Recurso> internos = mapa.getMapaRecursos().getOrDefault(ubicacionId, Collections.emptyMap());
        Map<TipoRecurso, Recurso> resultado = new HashMap<>();
        internos.forEach((k, v) -> {
            try { resultado.put(TipoRecurso.valueOf(k), v); } catch (IllegalArgumentException ignored) { }
        });
        return resultado;
    }

    /** Agrega un recurso a una ubicación (crea la ubicación si no existe en el mapa) */
    public boolean agregarRecurso(String ubicacionId, Recurso recurso) {
        MapaRecursos mapa = obtenerMapaPrincipal();
        mapa.getMapaRecursos().computeIfAbsent(ubicacionId, k -> new HashMap<>());
        Map<String, Recurso> recursosUb = mapa.getMapaRecursos().get(ubicacionId);
        String clave = recurso.getTipo().name();
        if (recursosUb.containsKey(clave)) {
            // Sumar cantidades
            Recurso existente = recursosUb.get(clave);
            existente.setCantidad(existente.getCantidad() + recurso.getCantidad());
        } else {
            recursosUb.put(clave, recurso);
        }
        try { guardarMapa(mapa); } catch (IOException ignored) {}
        return true;
    }

    /** Obtiene recurso específico de una ubicación */
    public Recurso obtenerRecursoEspecifico(String ubicacionId, TipoRecurso tipo) {
        return obtenerMapaPrincipal().getMapaRecursos()
                .getOrDefault(ubicacionId, Collections.emptyMap())
                .get(tipo.name());
    }

    /** Consume cantidad de recurso, si hay suficiente */
    public boolean consumirRecurso(String ubicacionId, TipoRecurso tipo, int cantidad) {
        Recurso recurso = obtenerRecursoEspecifico(ubicacionId, tipo);
        if (recurso == null || recurso.getCantidad() < cantidad) return false;
        recurso.setCantidad(recurso.getCantidad() - cantidad);
        try { guardarMapa(obtenerMapaPrincipal()); } catch (IOException ignored) {}
        return true;
    }

    /** Transfiere recurso entre dos ubicaciones */
    public boolean transferirRecurso(String origenId, String destinoId, TipoRecurso tipo, int cantidad) {
        if (!consumirRecurso(origenId, tipo, cantidad)) return false;
        Recurso base = obtenerRecursoEspecifico(origenId, tipo); // después de consumir puede quedar null o con menos
        Recurso nuevo = new Recurso(
                (base != null ? base.getId() : UUID.randomUUID().toString()) + "_transf",
                (base != null ? base.getNombre() : tipo.name()),
                tipo,
                cantidad
        );
        agregarRecurso(destinoId, nuevo);
        return true;
    }

    /** Obtiene todas las ubicaciones del mapa por defecto como objetos (si existen en repositorio) */
    public Set<Ubicacion> obtenerTodasLasUbicaciones() {
        MapaRecursos mapa = obtenerMapaPrincipal();
        Set<Ubicacion> resultado = new HashSet<>();
        for (String idUb : mapa.getMapaRecursos().keySet()) {
            try {
                ubicacionRepo.findById(idUb).ifPresent(resultado::add);
            } catch (IOException ignored) {}
        }
        return resultado;
    }

    /** Inventario completo (ubicacion -> (TipoRecurso -> Recurso)) */
    public Map<String, Map<TipoRecurso, Recurso>> obtenerInventarioCompleto() {
        Map<String, Map<TipoRecurso, Recurso>> inv = new HashMap<>();
        MapaRecursos mapa = obtenerMapaPrincipal();
        mapa.getMapaRecursos().forEach((ubId, recursosStr) -> {
            Map<TipoRecurso, Recurso> conv = new HashMap<>();
            recursosStr.forEach((k, v) -> {
                try { conv.put(TipoRecurso.valueOf(k), v); } catch (IllegalArgumentException ignored) { }
            });
            inv.put(ubId, conv);
        });
        return inv;
    }

    /** Busca disponibilidad total por tipo en todas las ubicaciones */
    public Map<String, Integer> buscarRecursoPorTipo(TipoRecurso tipo) {
        Map<String, Integer> res = new HashMap<>();
        MapaRecursos mapa = obtenerMapaPrincipal();
        mapa.getMapaRecursos().forEach((ubId, recursos) -> {
            Recurso r = recursos.get(tipo.name());
            if (r != null && r.getCantidad() > 0) res.put(ubId, r.getCantidad());
        });
        return res;
    }

    /** Resumen agregado de todo el inventario */
    public Map<String, Object> calcularResumenInventario() {
        Map<String, Map<TipoRecurso, Recurso>> inv = obtenerInventarioCompleto();
        Map<String, Integer> totalPorTipo = new HashMap<>();
        int totalGeneral = 0;
        int ubicacionesConRecursos = 0;
        for (Map<TipoRecurso, Recurso> recursos : inv.values()) {
            if (!recursos.isEmpty()) ubicacionesConRecursos++;
            for (Recurso r : recursos.values()) {
                totalGeneral += r.getCantidad();
                totalPorTipo.merge(r.getTipo().name(), r.getCantidad(), Integer::sum);
            }
        }
        Map<String, Object> res = new HashMap<>();
        res.put("totalPorTipo", totalPorTipo);
        res.put("totalGeneral", totalGeneral);
        res.put("ubicacionesConRecursos", ubicacionesConRecursos);
        return res;
    }

    /** Estadísticas por ubicación: mapa ubicacionId -> (tipo -> cantidad) */
    public Map<String, Map<String, Integer>> calcularEstadisticasPorUbicacion() {
        Map<String, Map<TipoRecurso, Recurso>> inv = obtenerInventarioCompleto();
        Map<String, Map<String, Integer>> resultado = new HashMap<>();
        inv.forEach((ubicacionId, recursos) -> {
            Map<String, Integer> totales = new HashMap<>();
            int subtotal = 0;
            for (Recurso r : recursos.values()) {
                totales.merge(r.getTipo().name(), r.getCantidad(), Integer::sum);
                subtotal += r.getCantidad();
            }
            totales.put("__TOTAL__", subtotal); // marcador total por ubicación
            resultado.put(ubicacionId, totales);
        });
        return resultado;
    }

    /** Filtra el inventario por id o nombre (si se proveen) */
    public Map<String, Map<TipoRecurso, Recurso>> filtrarInventarioPorIdONombre(String ubicacionId, String nombreUbicacion) {
        Map<String, Map<TipoRecurso, Recurso>> inv = obtenerInventarioCompleto();
        if ((ubicacionId == null || ubicacionId.isBlank()) && (nombreUbicacion == null || nombreUbicacion.isBlank())) {
            return inv; // sin filtros
        }
        Map<String, Map<TipoRecurso, Recurso>> filtrado = new HashMap<>();
        String nombreBuscado = nombreUbicacion != null ? nombreUbicacion.trim().toLowerCase() : null;
        for (String id : inv.keySet()) {
            boolean coincideId = ubicacionId != null && !ubicacionId.isBlank() && id.equals(ubicacionId);
            boolean coincideNombre = false;
            if (nombreBuscado != null && !nombreBuscado.isBlank()) {
                try {
                    ubicacionRepo.findById(id).ifPresent(u -> {
                        String nom = u.getNombre() != null ? u.getNombre().trim().toLowerCase() : "";
                        if (nom.equals(nombreBuscado)) {
                            filtrado.put(id, inv.get(id));
                        }
                    });
                } catch (IOException ignored) {}
            }
            if (coincideId) {
                filtrado.put(id, inv.get(id));
            }
        }
        return filtrado.isEmpty() ? inv : filtrado; // si no encontró nada por filtros, devolver completo
    }

    // ================= API EXTENDIDA (multi-mapa) =================
    public MapaRecursos crearMapa(String id, String nombre) throws IOException {
        MapaRecursos mapa = new MapaRecursos(id, nombre);
        mapaRecursosRepo.save(mapa);
        return mapa;
    }
    public MapaRecursos obtenerMapaPorId(String id) throws IOException {return mapaRecursosRepo.findById(id).orElse(null);}
    public boolean eliminarMapa(String mapaId) throws IOException {return mapaRecursosRepo.deleteById(mapaId);}

    // ================= Helpers =================
    private MapaRecursos obtenerMapaPrincipal() {
        try {
            MapaRecursos existente = mapaRecursosRepo.findById(DEFAULT_MAPA_ID).orElse(null);
            if (existente != null) return existente;
            MapaRecursos nuevo = new MapaRecursos(DEFAULT_MAPA_ID, "Mapa Principal");
            mapaRecursosRepo.save(nuevo);
            return nuevo;
        } catch (IOException e) {
            // Si falla repositorio, usar uno en memoria (no persistente)
            return new MapaRecursos(DEFAULT_MAPA_ID, "Mapa Memoria");
        }
    }
    private void guardarMapa(MapaRecursos mapa) throws IOException { mapaRecursosRepo.save(mapa); }
}
