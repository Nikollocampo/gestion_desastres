package com.example.estructuras.service;

import org.springframework.stereotype.Service;

@Service
public class AdministradorService {

    private final GrafoNoDirigidoService grafoNoDirigidoService;

    public AdministradorService(GrafoNoDirigidoService grafoNoDirigidoService) {
        this.grafoNoDirigidoService = grafoNoDirigidoService;
    }
}
