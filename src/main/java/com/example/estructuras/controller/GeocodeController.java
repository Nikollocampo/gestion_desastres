package com.example.estructuras.controller;

import com.example.estructuras.service.UbicacionGeocodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/geocode")
public class GeocodeController {

    private final UbicacionGeocodeService service;

    public GeocodeController(UbicacionGeocodeService service) {
        this.service = service;
    }

    @PostMapping("/ubicaciones")
    public List<Map<String, Object>> geocodeUbicaciones() throws Exception {
        return service.geocodeUbicaciones();
    }
}
