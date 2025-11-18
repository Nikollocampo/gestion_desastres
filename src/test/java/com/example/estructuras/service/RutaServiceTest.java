package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.RutaRequestDto;
import com.example.estructuras.Mapping.dto.RutaResponseDto;
import com.example.estructuras.Mapping.dto.UbicacionRequestDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.TipoUbicacion;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.RutaJsonRepository;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RutaServiceTest {
    private RutaService service;
    private UbicacionJsonRepository ubicacionRepo;
    private RutaJsonRepository rutaRepo;
    private Ubicacion origen;
    private Ubicacion destino;

    @BeforeEach
    void setUp() throws IOException {
        ubicacionRepo = new UbicacionJsonRepository();
        rutaRepo = new RutaJsonRepository();
        service = new RutaService(ubicacionRepo, rutaRepo);
        // Crear ubicaciones de prueba
        origen = new Ubicacion("origen-id", "Origen", "Calle O", "Carrera O", TipoUbicacion.REFUGIO);
        destino = new Ubicacion("destino-id", "Destino", "Calle D", "Carrera D", TipoUbicacion.CENTRO_AYUDA);
        ubicacionRepo.add(origen);
        ubicacionRepo.add(destino);
    }

    @Test
    @Order(1)
    void testCrear() throws IOException {
        RutaRequestDto dto = new RutaRequestDto(origen.getId(), destino.getId(), 12.5f);
        RutaResponseDto response = service.crear(dto);
        assertNotNull(response);
        assertEquals(origen.getId(), response.getOrigen().getId());
        assertEquals(destino.getId(), response.getDestino().getId());
        assertEquals(12.5f, response.getDistancia());
        assertEquals(service.calcularPeso(12.5f), response.getPeso());
    }

    @Test
    @Order(2)
    void testListar() throws IOException {
        RutaRequestDto dto = new RutaRequestDto(origen.getId(), destino.getId(), 7.0f);
        service.crear(dto);
        List<RutaResponseDto> rutas = service.listar();
        assertNotNull(rutas);
        assertTrue(rutas.stream().anyMatch(r -> r.getOrigen().getId().equals(origen.getId()) && r.getDestino().getId().equals(destino.getId())));
    }

    @Test
    @Order(3)
    void testObtenerUbicacionExistente() throws IOException {
        Ubicacion encontrada = service.obtenerUbicacion(origen.getId());
        assertNotNull(encontrada);
        assertEquals(origen.getNombre(), encontrada.getNombre());
    }

    @Test
    @Order(4)
    void testObtenerUbicacionNoExistente() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.obtenerUbicacion("no-existe"));
        assertTrue(ex.getMessage().contains("Ubicación no encontrada"));
    }

    @Test
    @Order(5)
    void testToResponseDto() {
        RutaRequestDto dto = new RutaRequestDto(origen.getId(), destino.getId(), 8.0f);
        RutaResponseDto creada = service.toResponseDto(new com.example.estructuras.model.Ruta(origen, destino, 8.0f));
        assertEquals(origen.getId(), creada.getOrigen().getId());
        assertEquals(destino.getId(), creada.getDestino().getId());
        assertEquals(8.0f, creada.getDistancia());
        assertEquals(service.calcularPeso(8.0f), creada.getPeso());
    }

    @Test
    @Order(6)
    void testToUbicacionDto() {
        UbicacionResponseDto dto = service.toUbicacionDto(origen);
        assertEquals(origen.getId(), dto.getId());
        assertEquals(origen.getNombre(), dto.getNombre());
        assertEquals(origen.getCalle(), dto.getCalle());
        assertEquals(origen.getCarrera(), dto.getCarrera());
        assertEquals(origen.getTipoUbicacion(), dto.getTipoUbicacion());
    }

    @Test
    @Order(7)
    void testCalcularPeso() {
        assertEquals(0, service.calcularPeso(0));
        assertEquals(0, service.calcularPeso(-5));
        assertEquals(1, service.calcularPeso(5));
        assertEquals(2, service.calcularPeso(10));
        assertEquals(3, service.calcularPeso(15));
        assertEquals(10, service.calcularPeso(50));
    }
}

