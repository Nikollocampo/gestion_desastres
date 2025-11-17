package com.example.estructuras.Mapping.dto;

public class ObtenerRecursoPorIdRequestDto {
    private String id;
    public ObtenerRecursoPorIdRequestDto() {}
    public ObtenerRecursoPorIdRequestDto(String id) { this.id = id; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}

