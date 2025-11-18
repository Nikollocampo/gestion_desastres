package com.example.estructuras.Mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class OperacionSimpleResponseDto {
    @JsonProperty("exito")
    private boolean exito;
    @JsonProperty("mensaje")
    private String mensaje;
    @JsonProperty("codigo")
    private String codigo; // opcional para categorizar el resultado
    @JsonProperty("timestamp")
    private Instant timestamp;
    @JsonProperty("asignaciones")
    private List<AsignacionRecursoDto> asignaciones;

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
    public List<AsignacionRecursoDto> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<AsignacionRecursoDto> asignaciones) { this.asignaciones = asignaciones; }
}
