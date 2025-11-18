package com.example.estructuras.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class NominatimService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Optional<double[]> geocode(String query, String email) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?format=json&limit=1&q="
                    + query.replace(" ", "+")
                    + "&email=" + email;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "gestion-desastres/1.0 (" + email + ")")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode arr = mapper.readTree(response.body());
            if (arr.isArray() && arr.size() > 0) {
                JsonNode first = arr.get(0);
                double lat = first.get("lat").asDouble();
                double lon = first.get("lon").asDouble();
                return Optional.of(new double[]{lat, lon});
            }

        } catch (Exception e) {
            System.out.println("Error geocoding: " + e.getMessage());
        }

        return Optional.empty();
    }
}
