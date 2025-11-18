package com.example.estructuras.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UbicacionGeocodeService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final NominatimService nominatimService;

    public UbicacionGeocodeService(NominatimService nominatimService) {
        this.nominatimService = nominatimService;
    }

    public List<Map<String, Object>> geocodeUbicaciones() throws Exception {

        File file = new File("src/main/resources/json/ubicaciones.json");

        List<Map<String, Object>> ubicaciones =
                mapper.readValue(file, new TypeReference<>() {});

        for (Map<String, Object> u : ubicaciones) {

            String calle = (String) u.get("calle");
            String carrera = (String) u.get("carrera");
            String nombre = (String) u.get("nombre");

            String query = nombre;
            if (calle != null) query += " " + calle;
            if (carrera != null) query += " " + carrera;
            query += " Bogota";

            Optional<double[]> coords = nominatimService.geocode(query, "ja5036713@gmail.com");

            if (coords.isPresent()) {
                u.put("lat", coords.get()[0]);
                u.put("lng", coords.get()[1]);

                Thread.sleep(1100); // 1 req/s (regla de Nominatim)
            }
        }

        mapper.writeValue(
                new File("src/main/resources/json/ubicaciones_geocoded.json"),
                ubicaciones
        );

        return ubicaciones;
    }
}
