package com.example.estructuras.service;

import com.example.estructuras.Mapping.dto.RegisterRequestDto;
import com.example.estructuras.Mapping.dto.UsuarioResponseDto;
import com.example.estructuras.Mapping.dto.EvacuacionResponseDto;
import com.example.estructuras.model.Rol;
import com.example.estructuras.repository.UserJsonRepository;
import com.example.estructuras.repository.DesastreJsonRepository;
import com.example.estructuras.repository.UbicacionJsonRepository;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OperadorEmergenciaServiceTest {
    private OperadorEmergenciaService service;
    private UserJsonRepository userRepo;
    private DesastreJsonRepository desastreRepo;
    private UbicacionJsonRepository ubicacionRepo;

    @BeforeEach
    void setUp() {
        userRepo = new UserJsonRepository();
        desastreRepo = new DesastreJsonRepository();
        ubicacionRepo = new UbicacionJsonRepository();
        service = new OperadorEmergenciaService(userRepo, desastreRepo, ubicacionRepo);
    }

    @Test
    @Order(1)
    void testCreateAndGetById() throws IOException {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setNombre("Operador Test");
        dto.setEmail("operador_test@correo.com");
        dto.setContrasena("clave123");
        UsuarioResponseDto creado = service.create(dto);
        assertNotNull(creado.getId());
        assertEquals("Operador Test", creado.getNombre());
        assertEquals(Rol.OPERADOR_EMERGENCIA, creado.getRol());
        UsuarioResponseDto byId = service.getById(creado.getId());
        assertNotNull(byId);
        assertEquals("Operador Test", byId.getNombre());
    }

    @Test
    @Order(2)
    void testListAll() throws IOException {
        List<UsuarioResponseDto> lista = service.listAll();
        assertNotNull(lista);
        assertTrue(lista.stream().allMatch(u -> u.getRol() == Rol.OPERADOR_EMERGENCIA));
    }

    @Test
    @Order(3)
    void testUpdate() throws IOException {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setNombre("Operador Update");
        dto.setEmail("operador_update@correo.com");
        dto.setContrasena("clave123");
        UsuarioResponseDto creado = service.create(dto);
        RegisterRequestDto updateDto = new RegisterRequestDto();
        updateDto.setNombre("Operador Actualizado");
        updateDto.setEmail("operador_update@correo.com");;
        UsuarioResponseDto actualizado = service.update(creado.getId(), updateDto);
        assertEquals("Operador Actualizado", actualizado.getNombre());
    }

    @Test
    @Order(4)
    void testDelete() throws IOException {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setNombre("Operador Delete");
        dto.setEmail("operador_delete@correo.com");
        dto.setContrasena("clave123");
        UsuarioResponseDto creado = service.create(dto);
        boolean eliminado = service.delete(creado.getId());
        assertTrue(eliminado);
        UsuarioResponseDto byId = service.getById(creado.getId());
        assertNull(byId);
    }

    @Test
    @Order(5)
    void testMonitorearUbicaciones() throws IOException {
        List<String> resultado = service.monitorearUbicaciones();
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        assertTrue(resultado.get(0).contains("Monitoreando"));
    }

    @Test
    @Order(6)
    void testActualizarSituacion() throws IOException {
        // Este test requiere que haya al menos un desastre en el repo
        var desastres = desastreRepo.findAll();
        if (desastres.isEmpty()) return;
        var desastre = desastres.get(0);
        String mensaje = service.actualizarSituacion(desastre.getIdDesastre(), 123, 5);
        assertTrue(mensaje.contains("Situación actualizada"));
    }

    @Test
    @Order(7)
    void testGestionarEvacuaciones() throws IOException {
        List<String> resultado = service.gestionarEvacuaciones();
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertFalse(resultado.isEmpty(), "El resultado no debe estar vacío");
        // Si no hay desastres, debe contener el mensaje específico
        if (resultado.size() == 1) {
            assertEquals("No hay desastres para evacuar.", resultado.get(0));
        } else {
            // Si hay desastres, debe contener el encabezado y el cierre
            assertEquals("=== GESTIÓN DE EVACUACIÓN POR RIESGO DE ZONA ===", resultado.get(0));
            assertEquals("Evacuación finalizada.", resultado.get(resultado.size() - 1));
            // Si hay al menos un desastre, verifica el formato de la línea de evacuación
            if (resultado.size() > 2) {
                String linea = resultado.get(1);
                assertTrue(linea.startsWith("→ Evacuando: "), "La línea debe comenzar con '→ Evacuando: '");
                assertTrue(linea.contains("| Zona: "), "Debe contener '| Zona: '");
                assertTrue(linea.contains("| Riesgo Zona: "), "Debe contener '| Riesgo Zona: '");
                assertTrue(linea.contains("| Prioridad Desastre: "), "Debe contener '| Prioridad Desastre: '");
                assertTrue(linea.contains("| Personas: "), "Debe contener '| Personas: '");
                assertTrue(linea.contains("| Magnitud: "), "Debe contener '| Magnitud: '");
            }
        }
    }

    @Test
    @Order(8)
    void testGestionarEvacuacionesDetallado() throws IOException {
        List<EvacuacionResponseDto> resultado = service.gestionarEvacuacionesDetallado();
        assertNotNull(resultado);
        // Puede estar vacío si no hay desastres, pero no debe ser null
    }
}
