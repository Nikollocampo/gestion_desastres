package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.LoginRequestDto;
import com.example.estructuras.Mapping.dto.RegisterRequestDto;
import com.example.estructuras.Mapping.dto.UsuarioResponseDto;
import com.example.estructuras.exception.InvalidCredentialsException;
import com.example.estructuras.exception.UserAlreadyExistsException;
import com.example.estructuras.model.Rol;
import com.example.estructuras.model.Usuario;
import com.example.estructuras.repository.UserJsonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserJsonRepository repository;

    public AuthService(UserJsonRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponseDto register(RegisterRequestDto dto) throws IOException {
        Optional<Usuario> existing = repository.findAll().stream()
                .filter(u -> dto.getEmail().equals(u.getEmail()))
                .findFirst();
        if (existing.isPresent()) {
            throw new UserAlreadyExistsException("Usuario ya existe con ese email");
        }
        String id = UUID.randomUUID().toString();
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol() != null ? dto.getRol() : Rol.OPERADOR_EMERGENCIA);
        repository.add(usuario);
        return new UsuarioResponseDto(usuario.getId(), usuario.getEmail(), usuario.getNombre(), usuario.getRol());
    }

    public UsuarioResponseDto login(LoginRequestDto dto) throws IOException {
        Optional<Usuario> opt = repository.findAll().stream()
                .filter(u -> dto.getEmail().equals(u.getEmail()))
                .findFirst();
        if (opt.isEmpty()) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
        Usuario u = opt.get();
        if (!u.getContrasena().equals(dto.getContrasena())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
        return new UsuarioResponseDto(u.getId(), u.getEmail(), u.getNombre(), u.getRol());
    }
}
