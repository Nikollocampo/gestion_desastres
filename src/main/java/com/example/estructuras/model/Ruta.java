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
    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    // Metodo para calcular un "peso" basado en la distancia en tramos de 5 km (igual lógica que RutaService)
    public int calcularPeso() {
        if (distancia <= 0) return 0;
        int peso = 0;
        int comparador = 5;
        for (int i = 1; i <= 10; i++) {
            if (distancia >= comparador) {
                peso = i;
                comparador += 5;
            } else {
                break;
            }
        }
        return peso;
    }
}
