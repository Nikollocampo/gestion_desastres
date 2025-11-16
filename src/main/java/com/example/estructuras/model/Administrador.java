package com.example.estructuras.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Administrador extends Usuario {
   /* private GrafoNoDirigido grafoRutas;

    public Administrador(String nombre, String id, String email, String contrasena) {
        super(nombre, id, email, contrasena);
        this.grafoRutas = new GrafoNoDirigido();

    }

    public Administrador() {
        super();
    }

    public void cargarGrafoRutas(GrafoNoDirigido grafo) {
        this.grafoRutas = grafo;
    }

    public void asignarRecursosConArbol(ArbolDistribucion arbol, List<Recurso> recursosDisponibles) {
        if (arbol == null) {
            System.out.println("Arbol de distribución nulo.");
            return;
        }
        arbol.imprimirEstructura();
        arbol.asignarRecursos(recursosDisponibles);
        System.out.println("Recursos restantes:");
        for (Recurso r : recursosDisponibles) {
            System.out.println(" - " + r.getNombre() + ": " + r.getCantidad());
        }
    }

    public void asignarRecursos(ColaPrioridad<Desastre> colaPrioridad, List<Recurso> recursosDisponibles) {
        System.out.println("=== ASIGNACIÓN DE RECURSOS SEGÚN PRIORIDAD ===");

        while (!colaPrioridad.estaVacia()) {
            Desastre desastre = colaPrioridad.atenderSiguiente();
            if (desastre == null) break;

            System.out.println("\nAtendiendo desastre: " + desastre.getNombre() +
                    " | Prioridad: " + desastre.asignarPrioridad() +
                    " | Personas afectadas: " + desastre.getPersonasAfectadas());

            for (Recurso recurso : recursosDisponibles) {
                int cantidadNecesaria = 0;

                switch (recurso.getTipo()) {
                    case ALIMENTO:
                        cantidadNecesaria = desastre.getPersonasAfectadas() * 3;
                        break;
                    case MEDICAMENTO:
                        cantidadNecesaria = desastre.getPersonasAfectadas();
                        break;
                    default:
                        cantidadNecesaria = 0;
                        break;
                }

                if (cantidadNecesaria > 0) {
                    if (recurso.getCantidad() >= cantidadNecesaria) {
                        recurso.consumir(cantidadNecesaria);
                        System.out.println(" Asignado " + cantidadNecesaria + " de " + recurso.getNombre());
                    } else if (recurso.getCantidad() > 0) {
                        int disponible = recurso.getCantidad();
                        recurso.consumir(disponible);
                        System.out.println(" Solo se pudieron asignar " + disponible + " de " + recurso.getNombre()
                                + " (faltan " + (cantidadNecesaria - disponible) + ")");
                    } else {
                        System.out.println("No hay disponibilidad de " + recurso.getNombre());
                    }
                }
            }

            System.out.println("→ Asignación completada para el desastre: " + desastre.getNombre());
        }
    }

    //metodo para asignarle un equipo al desastre
    public void asignarEquipo(Equipo equipo, Desastre desastre) {
        List<Equipo> equiposAsignados = desastre.getEquiposAsignados();

        // 1. Calcular personal requerido según prioridad
        String prioridad = desastre.asignarPrioridad();
        int personalRequerido = switch (prioridad) {
            case "Alta" -> 20;
            case "Media" -> 10;
            case "Baja" -> 5;
            default -> 0;
        };

        // 2. Mostrar información del desastre y del equipo
        System.out.println("\nINFORMACIÓN DEL DESASTRE:");
        System.out.println("   • Desastre: " + desastre.getNombre());
        System.out.println("   • Ubicación: " + desastre.getUbicacion().getNombre());
        System.out.println("   • Prioridad: " + prioridad);
        System.out.println("   • Personal requerido: " + personalRequerido + " integrantes");

        System.out.println("\nINFORMACIÓN DEL EQUIPO:");
        System.out.println("   • Tipo: " + equipo.getTipoEquipo());
        System.out.println("   • Disponibles: " + equipo.getIntegrantesDisponibles() + " integrantes");

        // 3. Verificar disponibilidad de personal
        if (equipo.getIntegrantesDisponibles() < personalRequerido) {
            System.out.println("\nASIGNACIÓN RECHAZADA:");
            System.out.println("   No hay suficientes integrantes en el equipo " + equipo.getTipoEquipo() +
                    " para atender el desastre " + desastre.getNombre());
            System.out.println("   Requiere: " + personalRequerido + " | Disponibles: " + equipo.getIntegrantesDisponibles());
            return;
        }

        // 4. Calcular ruta más corta usando Dijkstra
        Ubicacion origen = equipo.getUbicacion();
        Ubicacion destino = desastre.getUbicacion();
        List<Ubicacion> rutaMasCorta = Dijkstra.caminoMasCorto(grafoRutas, origen, destino);

        if (rutaMasCorta == null || rutaMasCorta.isEmpty()) {
            System.out.println("\nERROR DE RUTA:");
            System.out.println("   No existe ruta disponible desde " + origen.getNombre() + " hasta " + destino.getNombre());
            System.out.println("   No se puede asignar el equipo sin ruta de acceso");
            return;
        }

        // 5. Calcular distancia total y tiempo estimado
        Map<Ubicacion, Float> distancias = Dijkstra.calcularDistancias(grafoRutas, origen);
        Float distanciaTotal = distancias.get(destino);

        if (distanciaTotal == null) {
            System.out.println("\nERROR DE DISTANCIA:");
            System.out.println("   No se pudo calcular la distancia entre " + origen.getNombre() + " y " + destino.getNombre());
            return;
        }

        float velocidadPromedioKmH = 40.0f;
        float tiempoEstimadoMin = (distanciaTotal / velocidadPromedioKmH) * 60;

        // 6. Mostrar ruta y tiempo estimado
        System.out.println("\n✓ Ruta encontrada:");
        for (int i = 0; i < rutaMasCorta.size(); i++) {
            System.out.print(rutaMasCorta.get(i).getNombre());
            if (i < rutaMasCorta.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println("\nDistancia total: " + String.format("%.1f", distanciaTotal) + " km");
        System.out.println("Tiempo estimado de llegada: " + String.format("%.0f", tiempoEstimadoMin) + " minutos");

        // 7. Asignar equipo al desastre
        equipo.setIntegrantesDisponibles(equipo.getIntegrantesDisponibles() - personalRequerido);
        equiposAsignados.add(equipo);

        // 8. Confirmación de asignación
        System.out.println("\nASIGNACIÓN EXITOSA:");
        System.out.println("   • Equipo " + equipo.getTipoEquipo() + " asignado al desastre " + desastre.getNombre());
        System.out.println("   • Integrantes desplegados: " + personalRequerido);
        System.out.println("   • Integrantes restantes en base: " + equipo.getIntegrantesDisponibles());
        System.out.println("   • Estado: En ruta hacia la zona del desastre");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
    }

    public Ruta definirRuta(GrafoNoDirigido grafo, Ubicacion origen, Ubicacion destino) {

        Map<Ubicacion, Float> distancias = Dijkstra.calcularDistancias(grafo, origen);
        Float distanciaMasCorta = distancias.get(destino);

        if (distanciaMasCorta == null || distanciaMasCorta.isInfinite()) {
            System.out.println("No existe una ruta entre " + origen.getNombre() + " y " + destino.getNombre());
            return null;
        }
        List<Ubicacion> camino = Dijkstra.caminoMasCorto(grafo, origen, destino);

        Ruta ruta = new Ruta(origen, destino, distanciaMasCorta);

        System.out.println("Ruta óptima creada de " + origen.getNombre() + " a " + destino.getNombre());
        System.out.println("Distancia total: " + distanciaMasCorta);
        System.out.println("Peso: " + ruta.calcularPeso());
        System.out.print("Camino más corto: ");

        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i).getNombre());
            if (i < camino.size() - 1) System.out.print(" → ");
        }

        System.out.println();

        return ruta;
    }

    public void generarReporte(List<Desastre> desastres) {
        // Ruta del archivo
        String ruta = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "reporte_desastres.txt";
        File archivo = new File(ruta);

        try (BufferedWriter writer = Files.newBufferedWriter(
                archivo.toPath(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write("═══════════════════════════════════════════════════════════════\n");
            writer.write("                  REPORTE DE DESASTRES ATENDIDOS\n");
            writer.write("═══════════════════════════════════════════════════════════════\n\n");

            if (desastres == null || desastres.isEmpty()) {
                writer.write("No se han registrado desastres atendidos.\n");
            } else {
                for (Desastre d : desastres) {
                    int equiposAsignados = d.getEquiposAsignados() != null ? d.getEquiposAsignados().size() : 0;
                    String prioridad = d.asignarPrioridad();

                    writer.write(String.format("• Desastre: %s\n", d.getNombre()));
                    writer.write(String.format("  - ID: %s\n", d.getIdDesastre()));
                    writer.write(String.format("  - Tipo: %s\n", d.getTipoDesastre()));
                    writer.write(String.format("  - Ubicación: %s\n", d.getUbicacion().getNombre()));
                    writer.write(String.format("  - Fecha: %s\n", d.getFecha()));
                    writer.write(String.format("  - Personas afectadas: %d\n", d.getPersonasAfectadas()));
                    writer.write(String.format("  - Prioridad: %s\n", prioridad));
                    writer.write(String.format("  - Equipos asignados: %d\n", equiposAsignados));
                    writer.write("---------------------------------------------------------------\n");
                }
            }

            writer.write("\nGenerado por el sistema de gestión de emergencias.\n");
            writer.write("Fecha de generación: " + LocalDate.now() + "\n");

            System.out.println("Reporte generado exitosamente en: " + archivo.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al guardar el reporte: " + e.getMessage());
        }

    }*/
}
