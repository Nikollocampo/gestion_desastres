package com.example.estructuras.Mapping.dto;

import java.time.Instant;

public class OperacionSimpleResponseDto {
    private boolean exito;
    private String mensaje;
    private String codigo; // opcional para categorizar el resultado
    private Instant timestamp;

    public OperacionSimpleResponseDto() {}

    private OperacionSimpleResponseDto(boolean exito, String mensaje, String codigo) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.timestamp = Instant.now();
    }

    public static OperacionSimpleResponseDto ok(String mensaje) {
        return new OperacionSimpleResponseDto(true, mensaje, "OK");
    }

    public static OperacionSimpleResponseDto error(String mensaje) {
        return new OperacionSimpleResponseDto(false, mensaje, "ERROR");
    }

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}

