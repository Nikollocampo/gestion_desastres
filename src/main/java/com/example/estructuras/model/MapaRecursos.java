package com.example.estructuras.model;

import java.util.*;

public class MapaRecursos {

    private Map<Ubicacion, Map<TipoRecurso, Recurso>> mapaRecursos;

    public MapaRecursos() {
        this.mapaRecursos = new HashMap<>();
    }

    // Agregar una ubicación al mapa
    public void agregarUbicacion(Ubicacion ubicacion) {
        if (!mapaRecursos.containsKey(ubicacion)) {
            mapaRecursos.put(ubicacion, new HashMap<>());
        }
    }

    // Agregar recurso a una ubicación
    public void agregarRecurso(Ubicacion ubicacion, Recurso recurso) {
        agregarUbicacion(ubicacion);
        Map<TipoRecurso, Recurso> recursos = mapaRecursos.get(ubicacion);

        if (recursos.containsKey(recurso.getTipo())) {
            // Si ya existe el tipo, incrementar cantidad
            recursos.get(recurso.getTipo()).agregarCantidad(recurso.getCantidad());
        } else {
            // Si no existe, agregar nuevo
            recursos.put(recurso.getTipo(), recurso);
        }
    }

    // Obtener recurso específico de una ubicación
    public Recurso obtenerRecurso(Ubicacion ubicacion, TipoRecurso tipo) {
        if (!mapaRecursos.containsKey(ubicacion)) return null;
        return mapaRecursos.get(ubicacion).get(tipo);
    }

    // Obtener todos los recursos de una ubicación
    public Map<TipoRecurso, Recurso> obtenerRecursos(Ubicacion ubicacion) {
        return mapaRecursos.getOrDefault(ubicacion, new HashMap<>());
    }

    // Consumir recurso de una ubicación
    public boolean consumirRecurso(Ubicacion ubicacion, TipoRecurso tipo, int cantidad) {
        Recurso recurso = obtenerRecurso(ubicacion, tipo);
        if (recurso == null) return false;

        boolean resultado = recurso.consumir(cantidad);

        if (resultado) {
            // Obtener todos los recursos actualizados del mapa
            List<Recurso> todosLosRecursos = new ArrayList<>();
            for (Map<TipoRecurso, Recurso> recursosUbicacion : mapaRecursos.values()) {
                todosLosRecursos.addAll(recursosUbicacion.values());
            }

            // Guardar en el archivo
            RegistroArchivo.guardarRecursos(todosLosRecursos);
        }

        return resultado;
    }

    // Transferir recurso entre ubicaciones
    public boolean transferirRecurso(Ubicacion origen, Ubicacion destino,
                                     TipoRecurso tipo, int cantidad) {
        if (consumirRecurso(origen, tipo, cantidad)) {
            Recurso recursoTransferido = obtenerRecurso(origen, tipo);
            if (recursoTransferido != null) {
                Recurso nuevo = new Recurso(
                        recursoTransferido.getId(),
                        recursoTransferido.getNombre(),
                        tipo,
                        cantidad
                );
                agregarRecurso(destino, nuevo);

                // Guardar cambios en archivo
                List<Recurso> todosLosRecursos = new ArrayList<>();
                for (Map<TipoRecurso, Recurso> recursosUbicacion : mapaRecursos.values()) {
                    todosLosRecursos.addAll(recursosUbicacion.values());
                }
                RegistroArchivo.guardarRecursos(todosLosRecursos);

                return true;
            }
        }
        return false;
    }


    // Mostrar inventario de una ubicación
    public void mostrarInventario(Ubicacion ubicacion) {
        System.out.println("\n=== Inventario de " + ubicacion.getNombre() + " ===");
        Map<TipoRecurso, Recurso> recursos = obtenerRecursos(ubicacion);
        if (recursos.isEmpty()) {
            System.out.println("No hay recursos disponibles");
        } else {
            recursos.values().forEach(r -> System.out.println("  - " + r));
        }
    }

    // Obtener todas las ubicaciones
    public Set<Ubicacion> obtenerUbicaciones() {
        return mapaRecursos.keySet();
    }
}
