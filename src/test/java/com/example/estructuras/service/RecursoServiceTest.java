package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.RecursoRequestDto;
import com.example.estructuras.Mapping.dto.RecursoResponseDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.TipoRecurso;
import com.example.estructuras.model.Ubicacion;
import com.example.estructuras.repository.RecursoJsonRepository;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecursoServiceTest {
    private RecursoService service;
    private RecursoJsonRepository repo;
    private RecursoRequestDto recursoDto;
    private UbicacionResponseDto ubicacionDto;

    @BeforeEach
    void setUp() throws IOException {
        repo = new RecursoJsonRepository();
        service = new RecursoService(repo);
        ubicacionDto = new UbicacionResponseDto(
                "ubic1", "Ubicacion1", "Calle 1", "Carrera 1", null, 1.0, 2.0
        );
        recursoDto = new RecursoRequestDto();
        recursoDto.setId(UUID.randomUUID().toString());
        recursoDto.setNombre("Agua");
        recursoDto.setTipo(TipoRecurso.ALIMENTO);
        recursoDto.setCantidad(10);
        recursoDto.setUbicacion(ubicacionDto);
        // Limpiar antes de cada test
        repo.saveAll(List.of());
    }

    @Test
    @Order(1)
    void testCrearYObtenerPorId() throws IOException {
        RecursoRequestDto creado = service.crear(recursoDto);
        assertNotNull(creado.getId());
        assertEquals("Agua", creado.getNombre());
        assertEquals(TipoRecurso.ALIMENTO, creado.getTipo());
        assertEquals(10, creado.getCantidad());
        RecursoRequestDto obtenido = service.obtenerPorId(creado.getId());
        assertEquals(creado.getId(), obtenido.getId());
    }

    @Test
    @Order(2)
    void testListar() throws IOException {
        service.crear(recursoDto);
        List<RecursoRequestDto> lista = service.listar();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(r -> r.getNombre().equals("Agua")));
    }

    @Test
    @Order(3)
    void testActualizar() throws IOException {
        RecursoRequestDto creado = service.crear(recursoDto);
        creado.setCantidad(20);
        RecursoRequestDto actualizado = service.actualizar(creado);
        assertEquals(20, actualizado.getCantidad());
    }

    @Test
    @Order(4)
    void testEliminar() throws IOException {
        RecursoRequestDto creado = service.crear(recursoDto);
        service.eliminar(creado.getId());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.obtenerPorId(creado.getId()));
        assertTrue(ex.getMessage().contains("Recurso no encontrado"));
    }

    @Test
    @Order(5)
    void testAgregarCantidad() throws IOException {
        RecursoRequestDto creado = service.crear(recursoDto);
        RecursoRequestDto actualizado = service.agregarCantidad(creado.getId(), 5);
        assertEquals(15, actualizado.getCantidad());
    }

    @Test
    @Order(6)
    void testConsumir() throws IOException {
        RecursoRequestDto creado = service.crear(recursoDto);
        boolean consumido = service.consumir(creado.getId(), 5);
        assertTrue(consumido);
        RecursoRequestDto actualizado = service.obtenerPorId(creado.getId());
        assertEquals(5, actualizado.getCantidad());
    }

    @Test
    @Order(7)
    void testConsumirNoSuficiente() throws IOException {
        RecursoRequestDto creado = service.crear(recursoDto);
        boolean consumido = service.consumir(creado.getId(), 20);
        assertFalse(consumido);
        RecursoRequestDto actualizado = service.obtenerPorId(creado.getId());
        assertEquals(10, actualizado.getCantidad());
    }
}

