package com.example.estructuras.service;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final GrafoNoDirigidoService grafoNoDirigidoService;

    public AdminService(GrafoNoDirigidoService grafoNoDirigidoService) {
        this.grafoNoDirigidoService = grafoNoDirigidoService;
    }
}
