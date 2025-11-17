package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoDesastre;

import java.time.LocalDate;
import java.util.List;

public class DesastreResponseDto {
    private String idDesastre;
    private String nombre;
    private TipoDesastre tipoDesastre;
    private int magnitud;
    private int personasAfectadas;
    private LocalDate fecha;
    private UbicacionResponseDto ubicacion;
    private List<EquipoResponseDto> equiposAsignados;
    private String prioridad;
    private int nivelPrioridad;

    public DesastreResponseDto() {}

    public DesastreResponseDto(String idDesastre, String nombre, TipoDesastre tipoDesastre,
                               int magnitud, int personasAfectadas, LocalDate fecha,
                               UbicacionResponseDto ubicacion, List<EquipoResponseDto> equiposAsignados,
                               String prioridad, int nivelPrioridad) {
        this.idDesastre = idDesastre;
        this.nombre = nombre;
        this.tipoDesastre = tipoDesastre;
        this.magnitud = magnitud;
        this.personasAfectadas = personasAfectadas;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.equiposAsignados = equiposAsignados;
        this.prioridad = prioridad;
        this.nivelPrioridad = nivelPrioridad;
    }

    public String getIdDesastre() {
        return idDesastre;
    }
    public void setIdDesastre(String idDesastre) {
        this.idDesastre = idDesastre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public TipoDesastre getTipoDesastre() {
        return tipoDesastre;
    }
    public void setTipoDesastre(TipoDesastre tipoDesastre) {
        this.tipoDesastre = tipoDesastre;
    }
    public int getMagnitud() {
        return magnitud;
    }
    public void setMagnitud(int magnitud) {
        this.magnitud = magnitud;
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
    public UbicacionResponseDto getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(UbicacionResponseDto ubicacion) {
        this.ubicacion = ubicacion;
    }
    public List<EquipoResponseDto> getEquiposAsignados() {
        return equiposAsignados;
    }
    public void setEquiposAsignados(List<EquipoResponseDto> equiposAsignados) {
        this.equiposAsignados = equiposAsignados;
    }
    public String getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    public int getNivelPrioridad() {
        return nivelPrioridad;
    }
    public void setNivelPrioridad(int nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
    }
}
