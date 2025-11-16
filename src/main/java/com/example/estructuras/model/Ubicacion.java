package com.example.estructuras.model;

import java.util.Objects;

public class Ubicacion {
    private String id;
    private String nombre;
    private String calle;
    private String carrera;
    private TipoUbicacion tipoUbicacion;

    public Ubicacion(String id, String nombre, String calle, String carrera, TipoUbicacion tipoUbicacion) {
        this.id = id;
        this.nombre = nombre;
        this.calle = calle;
        this.carrera = carrera;
        this.tipoUbicacion = tipoUbicacion;
    }

    public  Ubicacion() {}

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ubicacion other = (Ubicacion) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public TipoUbicacion getTipoUbicacion() {return  tipoUbicacion;}

}
