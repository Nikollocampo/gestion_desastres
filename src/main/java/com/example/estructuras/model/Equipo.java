package com.example.estructuras.model;

public class Equipo {
    private String idEquipo;
    private int integrantesDisponibles;
    private TipoEquipo tipoEquipo;
    private Ubicacion ubicacion;

    public Equipo(String idEquipo, int integrantesDisponibles, TipoEquipo tipoEquipo) {
        this.idEquipo = idEquipo;
        this.integrantesDisponibles = integrantesDisponibles;
        this.tipoEquipo = tipoEquipo;
        this.ubicacion =  new Ubicacion("U1", "Ubicacion equipo", "5", "10",TipoUbicacion.CENTRO_AYUDA);
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public Equipo(){};

    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIntegrantesDisponibles() {
        return integrantesDisponibles;
    }

    public void setIntegrantesDisponibles(int integrantesDisponibles) {
        this.integrantesDisponibles = integrantesDisponibles;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public void setUbicacion(Ubicacion ubicacion) {this.ubicacion = ubicacion; }

    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo='" + idEquipo + '\'' +
                ", integrantesDisponibles=" + integrantesDisponibles +
                ", tipoEquipo=" + tipoEquipo +
                '}';
    }
}
