package com.example.estructuras.model;

/**
 * Representa el rol de usuario Administrador del sistema.
 * Hereda de Usuario y puede ser extendido con atributos propios del rol.
 * La lógica de negocio debe estar en los servicios, no en el modelo.
 */
public class Administrador extends Usuario {
    // Aquí puedes agregar atributos propios del rol Administrador si los necesitas

    public Administrador(String id, String nombre, String email, String contrasena) {
        super(id, nombre, email, contrasena, Rol.ADMINISTRADOR);
    }

    public Administrador() {
        super();
        setRol(Rol.ADMINISTRADOR);
    }
    // Si necesitas métodos propios del rol (no lógica de negocio), agrégalos aquí
}
