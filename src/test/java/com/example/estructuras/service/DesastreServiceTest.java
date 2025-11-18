package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.DesastreResponseDto;
import com.example.estructuras.Mapping.dto.EquipoResponseDto;
import com.example.estructuras.model.*;
import com.example.estructuras.repository.DesastreJsonRepository;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DesastreServiceTest {
    private DesastreService service;
    private DesastreJsonRepository repo;
    private Desastre desastre;
    private Ubicacion ubicacion;

    @BeforeEach
    void setUp() throws IOException {
        repo = new DesastreJsonRepository();
        service = new DesastreService(repo);
        ubicacion = new Ubicacion("ubic1", "Ubicacion1", "Calle 1", "Carrera 1", TipoUbicacion.REFUGIO, 1.0, 2.0);
        desastre = new Desastre(5, "Incendio", "des1", TipoDesastre.INCENDIO, 100, LocalDate.now(), ubicacion);
        repo.saveAll(new ArrayList<>()); // Limpiar antes de cada test
    }

    @Test
    @Order(1)
    void testAgregarYObtenerPorId() throws IOException {
        service.agregarDesastre(desastre);
        DesastreResponseDto dto = service.obtenerPorId(desastre.getIdDesastre());
        assertNotNull(dto);
        assertEquals("Incendio", dto.getNombre());
        assertEquals(TipoDesastre.INCENDIO, dto.getTipoDesastre());
        assertEquals(5, dto.getMagnitud());
        assertEquals(100, dto.getPersonasAfectadas());
        assertEquals("Ubicacion1", dto.getUbicacion().getNombre());
    }

    @Test
    @Order(2)
    void testObtenerTodos() throws IOException {
        service.agregarDesastre(desastre);
        List<DesastreResponseDto> lista = service.obtenerTodos();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(d -> d.getIdDesastre().equals(desastre.getIdDesastre())));
    }

    @Test
    @Order(3)
    void testEliminarDesastre() throws IOException {
        service.agregarDesastre(desastre);
        boolean eliminado = service.eliminarDesastre(desastre.getIdDesastre());
        assertTrue(eliminado);
        assertNull(service.obtenerPorId(desastre.getIdDesastre()));
    }

    @Test
    @Order(4)
    void testObtenerEquiposAsignadosVacio() {
        List<EquipoResponseDto> equipos = service.obtenerEquiposAsignados(desastre);
        assertNotNull(equipos);
        assertTrue(equipos.isEmpty());
    }

    @Test
    @Order(5)
    void testObtenerEquiposAsignadosConEquipo() {
        Equipo equipo = new Equipo("eq1", 10, TipoEquipo.BOMBEROS);
        equipo.setUbicacion(ubicacion);
        List<Equipo> lista = new ArrayList<>();
        lista.add(equipo);
        desastre.setEquiposAsignados(lista);
        List<EquipoResponseDto> equipos = service.obtenerEquiposAsignados(desastre);
        assertNotNull(equipos);
        assertEquals(1, equipos.size());
        assertEquals("eq1", equipos.get(0).getIdEquipo());
    }
}
