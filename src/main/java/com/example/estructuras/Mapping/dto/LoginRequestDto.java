package com.example.estructuras.Mapping.dto;

public class LoginRequestDto {
    private String email;
    private String contrasena;

    public LoginRequestDto() {}

    public LoginRequestDto(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
