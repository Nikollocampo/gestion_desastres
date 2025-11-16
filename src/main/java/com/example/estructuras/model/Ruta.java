package com.example.estructuras.model;

public class Ruta {
    private Ubicacion origen;
    private Ubicacion destino;
    private float distancia;

    public Ruta(Ubicacion origen, Ubicacion destino, float distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    public Ruta() {}

    public int calcularPeso(){
        int peso = 0;
        int comparador = 5;
        if (distancia <= 0) return peso;
        for(int i = 1; i <= 10; i++){
            if(distancia >= comparador){
                peso = i;
                comparador += 5;
            }else{
                break;
            }
        }
        return peso;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public void setOrigen(Ubicacion origen) {
        this.origen = origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public void setDestino(Ubicacion destino) {
        this.destino = destino;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
