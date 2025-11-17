package com.example.estructuras.Mapping.dto;

import com.example.estructuras.model.Ubicacion;

import java.util.List;

public class RutaDetalleResponseDto {
    private boolean exito;
    private String mensaje;
    private UbicacionResponseDto origen;
    private UbicacionResponseDto destino;
    private float distancia;
    private int peso;
    private List<String> camino;

    public static RutaDetalleResponseDto ok(Ubicacion origen, Ubicacion destino, float distancia, int peso, List<String> camino, String mensaje){
        RutaDetalleResponseDto dto = new RutaDetalleResponseDto();
        dto.exito = true;
        dto.mensaje = mensaje;
        dto.origen = new UbicacionResponseDto(origen.getId(), origen.getNombre(), origen.getCalle(), origen.getCarrera(), origen.getTipoUbicacion());
        dto.destino = new UbicacionResponseDto(destino.getId(), destino.getNombre(), destino.getCalle(), destino.getCarrera(), destino.getTipoUbicacion());
        dto.distancia = distancia;
        dto.peso = peso;
        dto.camino = camino;
        return dto;
    }
    public static RutaDetalleResponseDto error(String mensaje){
        RutaDetalleResponseDto dto = new RutaDetalleResponseDto();
        dto.exito = false;
        dto.mensaje = mensaje;
        return dto;
    }
    public boolean isExito() { return exito; }
    public String getMensaje() { return mensaje; }
    public UbicacionResponseDto getOrigen() { return origen; }
    public UbicacionResponseDto getDestino() { return destino; }
    public float getDistancia() { return distancia; }
    public int getPeso() { return peso; }
    public List<String> getCamino() { return camino; }
}

