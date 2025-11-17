package com.example.estructuras.Mapping.dto;

import java.util.List;

public class AsignarEquipoResponseDto {
    private boolean exito;
    private String mensaje;
    private int integrantesDesplegados;
    private int integrantesRestantes;
    // nuevos campos de detalle
    private String prioridadDesastre;
    private int personalRequerido;
    private float distanciaRuta;
    private float tiempoEstimadoMinutos;
    private List<String> camino;

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public int getIntegrantesDesplegados() { return integrantesDesplegados; }
    public void setIntegrantesDesplegados(int integrantesDesplegados) { this.integrantesDesplegados = integrantesDesplegados; }
    public int getIntegrantesRestantes() { return integrantesRestantes; }
    public void setIntegrantesRestantes(int integrantesRestantes) { this.integrantesRestantes = integrantesRestantes; }

    public String getPrioridadDesastre() { return prioridadDesastre; }
    public void setPrioridadDesastre(String prioridadDesastre) { this.prioridadDesastre = prioridadDesastre; }
    public int getPersonalRequerido() { return personalRequerido; }
    public void setPersonalRequerido(int personalRequerido) { this.personalRequerido = personalRequerido; }
    public float getDistanciaRuta() { return distanciaRuta; }
    public void setDistanciaRuta(float distanciaRuta) { this.distanciaRuta = distanciaRuta; }
    public float getTiempoEstimadoMinutos() { return tiempoEstimadoMinutos; }
    public void setTiempoEstimadoMinutos(float tiempoEstimadoMinutos) { this.tiempoEstimadoMinutos = tiempoEstimadoMinutos; }
    public List<String> getCamino() { return camino; }
    public void setCamino(List<String> camino) { this.camino = camino; }
}
