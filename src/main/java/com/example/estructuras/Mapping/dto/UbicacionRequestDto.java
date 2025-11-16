package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.TipoUbicacion;

public class UbicacionRequestDto {
    private String nombre;
    private String calle;
    private String carrera;
    private TipoUbicacion tipoUbicacion;

    public UbicacionRequestDto() {}

    public UbicacionRequestDto(String nombre, String calle, String carrera, TipoUbicacion tipoUbicacion) {
        this.nombre = nombre;
        this.calle = calle;
        this.carrera = carrera;
        this.tipoUbicacion = tipoUbicacion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public TipoUbicacion getTipoUbicacion() { return tipoUbicacion; }
    public void setTipoUbicacion(TipoUbicacion tipoUbicacion) { this.tipoUbicacion = tipoUbicacion; }

}
