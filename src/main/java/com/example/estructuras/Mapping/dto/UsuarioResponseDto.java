package com.example.estructuras.Mapping.dto;
import com.example.estructuras.model.Rol;

public class UsuarioResponseDto {
    private String id;
    private String email;
    private String nombre;
    private Rol rol;

    public UsuarioResponseDto() {}

    public UsuarioResponseDto(String id, String email, String nombre, Rol rol) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

}
