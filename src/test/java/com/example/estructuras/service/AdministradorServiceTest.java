package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.*;
import com.example.estructuras.model.*;
import com.example.estructuras.repository.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdministradorServiceTest {
    private AdministradorService service;
    private GrafoJsonRepository grafoRepo;
    private UbicacionJsonRepository ubicacionRepo;
    private RutaJsonRepository rutaRepo;
    private DesastreJsonRepository desastreRepo;
    private EquipoJsonRepository equipoRepo;
    private RecursoJsonRepository recursoRepo;

    @BeforeEach
    void setUp() {
        rutaRepo = new RutaJsonRepository();
        ubicacionRepo = new UbicacionJsonRepository();
        grafoRepo = new GrafoJsonRepository(rutaRepo, ubicacionRepo);
        desastreRepo = new DesastreJsonRepository();
        equipoRepo = new EquipoJsonRepository();
        recursoRepo = new RecursoJsonRepository();
        service = new AdministradorService(grafoRepo, ubicacionRepo, rutaRepo, desastreRepo, equipoRepo, recursoRepo);
    }

    @Test
    @Order(1)
    void testDefinirRutaOrigenDestinoNoExiste() throws IOException {
        DefinirRutaRequestDto dto = new DefinirRutaRequestDto("no-existe-origen", "no-existe-destino");
        RutaDetalleResponseDto res = service.definirRuta(dto);
        assertFalse(res.isExito());
        assertTrue(res.getMensaje().contains("no encontrado"));
    }

    @Test
    @Order(2)
    void testAsignarRecursosPrioridadSinDatos() throws IOException {
        desastreRepo.saveAll(List.of());
        recursoRepo.saveAll(List.of());
        List<AsignacionRecursoDto> asignaciones = service.asignarRecursosPrioridad();
        assertNotNull(asignaciones);
        assertEquals(0, asignaciones.size());
    }

    @Test
    @Order(3)
    void testGenerarReporte() throws IOException {
        OperacionSimpleResponseDto res = service.generarReporte();
        assertTrue(res.isExito());
        assertTrue(res.getMensaje().contains("Reporte generado"));
    }
}
