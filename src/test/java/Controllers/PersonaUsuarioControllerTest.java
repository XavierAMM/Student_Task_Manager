package Controllers;

import Controllers.PersonaController;
import Controllers.UsuarioController;
import Models.Persona;
import Models.Usuario;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonaUsuarioControllerTest {

    private static PersonaController personaController;
    private static UsuarioController usuarioController;

    private static int testIdPersona;
    private static int testIdUsuario;

    @BeforeAll
    public static void setUpClass() {
        personaController = PersonaController.getInstance();
        usuarioController = UsuarioController.getInstance();
    }

    @Test
    @Order(1)
    public void testAddPersonaAndUsuario() {
        System.out.println("testAddPersonaAndUsuario");

        String nombre = "TestNombre";
        String apellido = "TestApellido";
        String cedula = "1234567890";
        String correo = "test@email.com";
        int idRol = 1;

        boolean isPersonaAdded = personaController.addPersona(nombre, apellido, cedula, correo, idRol);
        assertTrue(isPersonaAdded, "La persona debería añadirse correctamente");

        Persona testPersona = personaController.getAllPersonas().stream()
                .filter(p -> p.getNombre().equals(nombre) && p.getApellido().equals(apellido))
                .findFirst()
                .orElse(null);
        assertNotNull(testPersona, "La persona debería existir después de ser añadida");

        testIdPersona = testPersona.getIdPersona();        

        boolean isUsuarioAdded = usuarioController.addUsuario(testIdPersona, "TestUser", "password123".toCharArray());
        assertTrue(isUsuarioAdded, "El usuario debería añadirse correctamente");

        Usuario testUsuario = usuarioController.getAllUsuarios().stream()
                .filter(u -> u.getUsuario().equals("TestUser"))
                .findFirst()
                .orElse(null);
        assertNotNull(testUsuario, "El usuario debería existir después de ser añadido");

        testIdUsuario = testUsuario.getIdUsuario();        
    }

    @Test
    @Order(2)
    public void testUpdatePersonaAndUsuario() {
        System.out.println("testUpdatePersonaAndUsuario");
        assertTrue(testIdPersona > 0, "El ID de la persona debe ser válido antes de actualizar");
        assertTrue(testIdUsuario > 0, "El ID del usuario debe ser válido antes de actualizar");

        String newNombre = "UpdatedNombre";
        String newApellido = "UpdatedApellido";
        String newCedula = "8888888888";
        String newCorreo = "updated@test.com";
        int newRol = 2;

        boolean personaUpdated = personaController.updatePersona(testIdPersona, newNombre, newApellido, newCedula, newCorreo, newRol);
        assertTrue(personaUpdated, "La persona debería actualizarse correctamente");

        Persona updatedPersona = personaController.getPersonaById(testIdPersona);
        assertNotNull(updatedPersona, "La persona actualizada debería existir");        

        String newUsuario = "UpdatedUser";
        char[] newContrasena = "newpassword".toCharArray();

        Usuario updatedUsuario = new Usuario(testIdUsuario, testIdPersona, newUsuario, newContrasena);
        boolean usuarioUpdated = usuarioController.updateUsuario(testIdUsuario, updatedUsuario);
        assertTrue(usuarioUpdated, "El usuario debería actualizarse correctamente");

        Usuario retrievedUsuario = usuarioController.getUsuarioById(testIdUsuario);
        assertNotNull(retrievedUsuario, "El usuario actualizado debería existir");        
    }

    @Test
    @Order(3)
    public void testDeleteUsuarioAndPersona() {
        System.out.println("testDeleteUsuarioAndPersona");
        assertTrue(testIdPersona > 0, "El ID de la persona debe ser válido antes de eliminar");
        assertTrue(testIdUsuario > 0, "El ID del usuario debe ser válido antes de eliminar");

        boolean usuarioDeleted = usuarioController.deleteUsuario(testIdUsuario);
        assertTrue(usuarioDeleted, "El usuario debería eliminarse correctamente");

        Usuario deletedUsuario = usuarioController.getUsuarioById(testIdUsuario);
        assertNull(deletedUsuario, "El usuario eliminado no debería existir");

        boolean personaDeleted = personaController.deletePersona(testIdPersona);
        assertTrue(personaDeleted, "La persona debería eliminarse correctamente");

        Persona deletedPersona = personaController.getPersonaById(testIdPersona);
        assertNull(deletedPersona, "La persona eliminada no debería existir");
    }
}
