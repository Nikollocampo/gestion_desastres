package com.example.estructuras.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperadorEmergencia extends Usuario {

    /*public OperadorEmergencia(String nombre, String id, String email, String contrasena) {
        super(nombre, id, email, contrasena);
    }

    public OperadorEmergencia() {}


    public void monitorearUbicaciones(List<Ubicacion> ubicaciones) {
        System.out.println("Monitoreando ubicaciones:");
        for (Ubicacion u : ubicaciones) {
            System.out.println(u.getNombre() + " (" + u.getCalle() + " y " + u.getCarrera() + ")");
        }
    }

    public void actualizarSituacion(Desastre desastre, int nuevasPersonasAfectadas, int nuevaMagnitud) {
        desastre.setPersonasAfectadas(nuevasPersonasAfectadas);
        desastre.setMagnitud(nuevaMagnitud);
        System.out.println("Situación actualizada del desastre " + desastre.getNombre() +
                " | Magnitud: " + nuevaMagnitud +
                " | Personas afectadas: " + nuevasPersonasAfectadas);
    }
    public void gestionarEvacuaciones(List<Desastre> desastres) {
        if (desastres == null || desastres.isEmpty()) {
            System.out.println("No hay desastres para evacuar.");
            return;
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

        System.out.println("=== GESTIÓN DE EVACUACIÓN POR RIESGO DE ZONA ===");
        while (!cola.estaVacia()) {
            Desastre d = cola.atenderSiguiente();
            if (d == null) break;

            String riesgoZona = riesgoPorZona.getOrDefault(d.getUbicacion(), "Baja");
            System.out.println("→ Evacuando: " + d.getNombre()
                    + " | Zona: " + (d.getUbicacion() != null ? d.getUbicacion().getNombre() : "N/D")
                    + " | Riesgo Zona: " + riesgoZona
                    + " | Prioridad Desastre: " + d.asignarPrioridad()
                    + " | Personas: " + d.getPersonasAfectadas()
                    + " | Magnitud: " + d.getMagnitud());
        }
        System.out.println("Evacuación finalizada.");
    }

    private int prioridadValor(String prioridad) {
        return switch (prioridad) {
            case "Alta" -> 3;
            case "Media" -> 2;
            case "Baja" -> 1;
            default -> 0;
        };
    }*/
    }
