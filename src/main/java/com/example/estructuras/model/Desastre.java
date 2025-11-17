package com.example.estructuras.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Desastre implements Comparable<Desastre>{

    private int magnitud;
    private String nombre;
    private String idDesastre;
    private TipoDesastre  tipoDesastre;
    private int personasAfectadas;
    private LocalDate fecha;
    private Ubicacion ubicacion;
    private List<Equipo> equiposAsignados;

    public Desastre(int magnitud, String nombre, String idDesastre, TipoDesastre tipoDesastre,
                    int personasAfectadas, LocalDate fecha,Ubicacion ubicacion) {
        this.magnitud = magnitud;
        this.nombre = nombre;
        this.idDesastre = idDesastre;
        this.tipoDesastre = tipoDesastre;
        this.personasAfectadas = personasAfectadas;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.equiposAsignados = new ArrayList<Equipo>();
    }

    public Desastre(){};

    // metodo para calcular la prioridad del desastre segun la magnitud o la cantidad de personas afectadas
    public String asignarPrioridad(){
        if (magnitud >= 4 || personasAfectadas > 1000) {
            return "Alta";
        } else if (magnitud == 3 || (personasAfectadas >= 300 && personasAfectadas <= 1000)) {
            return "Media";
        } else {
            return "Baja";
        }
    }

    //metodo para asignarle un valor numerico a la prioridad
    public int asignarNivelPrioridad(){
        switch (asignarPrioridad()){
            case "Alta": return 1;
            case "Media": return 2;
            default: return 3;
        }
    }

    //metodo para comparar el desastre por prioridad
    @Override
    public int compareTo(Desastre otro) {
        //los de menos numero tiene mayor prioridad
        return Integer.compare(this.asignarNivelPrioridad(), otro.asignarNivelPrioridad());

    }

    public int getMagnitud() {
        return magnitud;
    }
    public void setMagnitud(int magnitud) {
        this.magnitud = magnitud;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getIdDesastre() {
        return idDesastre;
    }
    public void setIdDesastre(String idDesastre) {
        this.idDesastre = idDesastre;
    }
    public TipoDesastre getTipoDesastre() {
        return tipoDesastre;
    }
    public void setTipoDesastre(TipoDesastre tipoDesastre) {
        this.tipoDesastre = tipoDesastre;
    }
    public int getPersonasAfectadas() {
        return personasAfectadas;
    }
    public void setPersonasAfectadas(int personasAfectadas) {
        this.personasAfectadas = personasAfectadas;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public List<Equipo> getEquiposAsignados() {
        return equiposAsignados;
    }
    public void setEquiposAsignados(List<Equipo> equiposAsignados) {
        this.equiposAsignados = equiposAsignados;
    }

    @Override
    public String toString() {
        return "Desastre{" +
                "magnitud=" + magnitud +
                ", nombre='" + nombre + '\'' +
                ", idDesastre='" + idDesastre + '\'' +
                ", tipoDesastre=" + tipoDesastre +
                ", personasAfectadas=" + personasAfectadas +
                ", fecha=" + fecha +
                ", ubicacion=" + ubicacion +
                ", equiposAsignados=" + equiposAsignados +
                '}';
    }
}
