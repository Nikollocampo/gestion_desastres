package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoUbicacion;

public class UbicacionResponseDto {

    private String id;
    private String nombre;
    private String calle;
    private String carrera;
    private TipoUbicacion tipoUbicacion;

    private Double lat;   // ← NUEVO
    private Double lng;   // ← NUEVO

    public UbicacionResponseDto() {}

    public UbicacionResponseDto(String id, String nombre, String calle, String carrera,
                                TipoUbicacion tipoUbicacion, Double lat, Double lng) {
        this.id = id;
        this.nombre = nombre;
        this.calle = calle;
        this.carrera = carrera;
        this.tipoUbicacion = tipoUbicacion;
        this.lat = lat;
        this.lng = lng;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoUbicacion getTipoUbicacion() {
        return tipoUbicacion;
    }

    public void setTipoUbicacion(TipoUbicacion tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
