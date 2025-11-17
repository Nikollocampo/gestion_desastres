package com.example.estructuras.Mapping.dto;

import java.util.List;

public class TransporteRecursosResponseDto {
    private boolean exito;
    private List<String> ruta;
    private String mensaje;

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
    public List<String> getRuta() { return ruta; }
    public void setRuta(List<String> ruta) { this.ruta = ruta; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

