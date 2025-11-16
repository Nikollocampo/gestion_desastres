
package com.example.estructuras.model;

public class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String contrasena;
    private Rol rol;

    public Usuario(String id, String nombre, String email, String contrasena, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario() {}

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getContrasena() { return contrasena; }
    public Rol getRol() { return rol; }

    // Setters (added so Jackson can deserialize/serialize)
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setRol(Rol rol) { this.rol = rol; }
}
