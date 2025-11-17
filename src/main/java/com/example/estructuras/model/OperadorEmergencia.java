package com.example.estructuras.model;

/**
 * Representa el rol de usuario Operador de Emergencia del sistema.
 * Hereda de Usuario y puede ser extendido con atributos propios del rol.
 * La lógica de negocio debe estar en los servicios, no en el modelo.
 */
public class OperadorEmergencia extends Usuario {


    public OperadorEmergencia(String id, String nombre, String email, String contrasena) {
        super(id, nombre, email, contrasena, Rol.OPERADOR_EMERGENCIA);
    }

    public OperadorEmergencia() {
        super();
        setRol(Rol.OPERADOR_EMERGENCIA);
    }
    // Si necesitas métodos propios del rol (no lógica de negocio), agrégalos aquí
}
