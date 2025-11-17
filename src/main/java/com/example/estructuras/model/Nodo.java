package com.example.estructuras.model;

public class Nodo implements  Comparable<Nodo>{
    Ubicacion ubicacion;
    float distancia;

    public Nodo(Ubicacion ubicacion, float distancia) {
        this.ubicacion = ubicacion;
        this.distancia = distancia;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public float getDistancia() {
        return distancia;
    }
    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    @Override
    public int compareTo(Nodo otro) {
        return Float.compare(this.distancia, otro.distancia);
    }

}
