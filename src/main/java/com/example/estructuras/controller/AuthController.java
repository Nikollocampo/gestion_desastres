package com.example.estructuras.controller;

import com.example.estructuras.Mapping.dto.LoginRequestDto;
import com.example.estructuras.Mapping.dto.RegisterRequestDto;
import com.example.estructuras.Mapping.dto.UsuarioResponseDto;

import com.example.estructuras.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // https://locahost:8080/api/auth/register
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDto> register(@RequestBody RegisterRequestDto dto) throws Exception {
        UsuarioResponseDto response = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDto> login(@RequestBody LoginRequestDto dto) throws Exception {
        UsuarioResponseDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    /*@GetMapping("/administrador")
    public ResponseEntity<String> administradorAccess() {
        authService.getDataAdminsitrador();
        return ResponseEntity.ok("Acceso concedido para administrador");
    }*/

}

