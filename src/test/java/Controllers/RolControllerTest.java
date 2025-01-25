package Controllers;

import Controllers.RolController;
import Models.Rol;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RolControllerTest {

    private static RolController rolController;

    private static int testIdRol;

    @BeforeAll
    public static void setUpClass() {
        rolController = RolController.getInstance();
    }

    @Test
    @Order(1)
    public void testAddRol() {
        System.out.println("testAddRol");

        String descripcion = "Test Rol";
        boolean isRolAdded = rolController.addRol(descripcion);
        assertTrue(isRolAdded, "El rol debería añadirse correctamente");

        List<Rol> roles = rolController.getAllRoles();
        Rol testRol = roles.stream()
                .filter(r -> r.getDescripcion().equals(descripcion))
                .findFirst()
                .orElse(null);

        assertNotNull(testRol, "El rol debería existir después de ser añadido");
        testIdRol = testRol.getIdRol();        
    }

    @Test
    @Order(2)
    public void testUpdateRol() {
        System.out.println("testUpdateRol");

        assertTrue(testIdRol > 0, "El ID del rol debe ser válido antes de actualizar");

        String nuevaDescripcion = "Rol Actualizado";
        boolean isRolUpdated = rolController.updateRol(testIdRol, nuevaDescripcion);
        assertTrue(isRolUpdated, "El rol debería actualizarse correctamente");

        Rol updatedRol = rolController.getRolById(testIdRol);
        assertNotNull(updatedRol, "El rol actualizado debería existir");
        assertEquals(nuevaDescripcion, updatedRol.getDescripcion(), "La descripción debería coincidir con la actualización");
    }

    @Test
    @Order(3)
    public void testDeleteRol() {
        System.out.println("testDeleteRol");

        assertTrue(testIdRol > 0, "El ID del rol debe ser válido antes de eliminar");

        boolean isRolDeleted = rolController.deleteRol(testIdRol);
        assertTrue(isRolDeleted, "El rol debería eliminarse correctamente");

        Rol deletedRol = rolController.getRolById(testIdRol);
        assertNull(deletedRol, "El rol eliminado no debería existir");
    }
}
