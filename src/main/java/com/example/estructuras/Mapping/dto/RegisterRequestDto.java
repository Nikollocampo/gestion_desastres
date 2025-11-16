package com.example.estructuras.Mapping.dto;
import com.example.estructuras.model.Rol;

public class RegisterRequestDto {
    private String email;
    private String nombre;
    private String contrasena;
    private Rol rol;

    public RegisterRequestDto() {}

    public RegisterRequestDto(String email, String nombre, String contrasena, Rol rol) {
        this.email = email;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}

