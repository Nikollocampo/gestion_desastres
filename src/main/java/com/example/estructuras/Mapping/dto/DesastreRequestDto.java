package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoDesastre;

import java.time.LocalDate;
import java.util.List;

public class DesastreRequestDto {
    private int magnitud;
    private String nombre;
    private String idDesastre;
    private TipoDesastre tipoDesastre;
    private int personasAfectadas;
    private LocalDate fecha;
    private String idUbicacion;
    private List<String> equiposIds;

    public DesastreRequestDto() {}

    public DesastreRequestDto(int magnitud, String nombre, String idDesastre,
                              TipoDesastre tipoDesastre, int personasAfectadas,
                              LocalDate fecha, String idUbicacion, List<String> equiposIds) {
        this.magnitud = magnitud;
        this.nombre = nombre;
        this.idDesastre = idDesastre;
        this.tipoDesastre = tipoDesastre;
        this.personasAfectadas = personasAfectadas;
        this.fecha = fecha;
        this.idUbicacion = idUbicacion;
        this.equiposIds = equiposIds;
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
    public String getIdUbicacion() {
        return idUbicacion;
    }
    public void setIdUbicacion(String idUbicacion) {
        this.idUbicacion = idUbicacion;
    }
    public List<String> getEquiposIds() {
        return equiposIds;
    }
    public void setEquiposIds(List<String> equiposIds) {
        this.equiposIds = equiposIds;
    }
}
