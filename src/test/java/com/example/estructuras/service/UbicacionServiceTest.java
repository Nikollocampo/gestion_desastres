package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.UbicacionRequestDto;
import com.example.estructuras.Mapping.dto.UbicacionResponseDto;
import com.example.estructuras.model.TipoUbicacion;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UbicacionServiceTest {
    private UbicacionService service;
    private UbicacionJsonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new UbicacionJsonRepository();
        service = new UbicacionService(repository);
    }

    @Test
    @Order(1)
    void testCrearYObtenerPorId() throws IOException {
        UbicacionRequestDto dto = new UbicacionRequestDto("TestNombre", "TestCalle", "TestCarrera", TipoUbicacion.REFUGIO);
        UbicacionResponseDto response = service.crear(dto);
        assertNotNull(response.getId());
        assertEquals("TestNombre", response.getNombre());
        UbicacionResponseDto byId = service.obtenerPorId(response.getId());
        assertEquals("TestNombre", byId.getNombre());
        assertEquals("TestCalle", byId.getCalle());
        assertEquals("TestCarrera", byId.getCarrera());
        assertEquals(TipoUbicacion.REFUGIO, byId.getTipoUbicacion());
    }

    @Test
    @Order(2)
    void testListar() throws IOException {
        List<UbicacionResponseDto> lista = service.listar();
        assertNotNull(lista);
        assertTrue(lista.size() > 0);
    }

    @Test
    @Order(3)
    void testActualizar() throws IOException {
        UbicacionRequestDto dto = new UbicacionRequestDto("ActualizarNombre", "ActualizarCalle", "ActualizarCarrera", TipoUbicacion.CENTRO_AYUDA);
        UbicacionResponseDto creado = service.crear(dto);
        UbicacionRequestDto nuevoDto = new UbicacionRequestDto("NuevoNombre", "NuevaCalle", "NuevaCarrera", TipoUbicacion.REFUGIO);
        UbicacionResponseDto actualizado = service.actualizar(creado.getId(), nuevoDto);
        assertEquals("NuevoNombre", actualizado.getNombre());
        assertEquals("NuevaCalle", actualizado.getCalle());
        assertEquals("NuevaCarrera", actualizado.getCarrera());
        assertEquals(TipoUbicacion.REFUGIO, actualizado.getTipoUbicacion());
    }

    @Test
    @Order(4)
    void testEliminar() throws IOException {
        UbicacionRequestDto dto = new UbicacionRequestDto("EliminarNombre", "EliminarCalle", "EliminarCarrera", TipoUbicacion.REFUGIO);
        UbicacionResponseDto creado = service.crear(dto);
        service.eliminar(creado.getId());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.obtenerPorId(creado.getId()));
        assertTrue(ex.getMessage().contains("Ubicación no encontrada"));
    }
}
