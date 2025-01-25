package Controllers;
import Controllers.MateriaController;
import Models.Materia;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MateriaControllerTest {

    private static MateriaController materiaController;

    @BeforeAll
    public static void setUpClass() {
        materiaController = MateriaController.getInstance();
    }

    @AfterAll
    public static void tearDownClass() {
        materiaController = null;
    }

    @Test
    public void testGetInstance() {
        MateriaController instance = MateriaController.getInstance();
        assertNotNull(instance, "La instancia no debería ser nula");
    }

    @Test
    public void testAddAndGetMateria() {
        System.out.println("addAndGetMateria");
        String testNombre = "Materia Test";
        int testIdDocente = 1; // Suponiendo que existe un docente con ID 1
        boolean isAdded = materiaController.addMateria(testNombre, testIdDocente);
        assertTrue(isAdded, "La materia debería añadirse correctamente");

        List<Materia> materias = materiaController.getAllMaterias();
        Materia addedMateria = materias.stream()
                .filter(m -> m.getNombre().equals(testNombre) && m.getIdDocente() == testIdDocente)
                .findFirst()
                .orElse(null);

        assertNotNull(addedMateria, "La materia añadida debería encontrarse en la lista");
        assertEquals(testNombre, addedMateria.getNombre(), "El nombre de la materia debería coincidir");
        assertEquals(testIdDocente, addedMateria.getIdDocente(), "El ID del docente debería coincidir");

        boolean isDeleted = materiaController.deleteMateria(addedMateria.getIdMateria());
        assertTrue(isDeleted, "La materia debería eliminarse correctamente");
    }

    @Test
    public void testUpdateMateria() {
        System.out.println("updateMateria");
        String testNombre = "Materia Update Test";
        int testIdDocente = 1; // Suponiendo que existe un docente con ID 1
        boolean isAdded = materiaController.addMateria(testNombre, testIdDocente);
        assertTrue(isAdded, "La materia debería añadirse correctamente");

        List<Materia> materias = materiaController.getAllMaterias();
        Materia addedMateria = materias.stream()
                .filter(m -> m.getNombre().equals(testNombre) && m.getIdDocente() == testIdDocente)
                .findFirst()
                .orElse(null);

        assertNotNull(addedMateria, "La materia añadida debería encontrarse en la lista");

        String updatedNombre = "Materia Updated";
        int updatedIdDocente = 2; // Suponiendo que existe un docente con ID 2
        boolean isUpdated = materiaController.updateMateria(addedMateria.getIdMateria(), updatedNombre, updatedIdDocente);
        assertTrue(isUpdated, "La materia debería actualizarse correctamente");

        Materia updatedMateria = materiaController.getMateriaById(addedMateria.getIdMateria());
        assertNotNull(updatedMateria, "La materia actualizada debería encontrarse");
        assertEquals(updatedNombre, updatedMateria.getNombre(), "El nombre de la materia debería haberse actualizado");
        assertEquals(updatedIdDocente, updatedMateria.getIdDocente(), "El ID del docente debería haberse actualizado");

        boolean isDeleted = materiaController.deleteMateria(addedMateria.getIdMateria());
        assertTrue(isDeleted, "La materia debería eliminarse correctamente");
    }

    @Test
    public void testDeleteMateria() {
        System.out.println("deleteMateria");
        String testNombre = "Materia Delete Test";
        int testIdDocente = 1; // Suponiendo que existe un docente con ID 1
        boolean isAdded = materiaController.addMateria(testNombre, testIdDocente);
        assertTrue(isAdded, "La materia debería añadirse correctamente");

        List<Materia> materias = materiaController.getAllMaterias();
        Materia addedMateria = materias.stream()
                .filter(m -> m.getNombre().equals(testNombre) && m.getIdDocente() == testIdDocente)
                .findFirst()
                .orElse(null);

        assertNotNull(addedMateria, "La materia añadida debería encontrarse en la lista");

        boolean isDeleted = materiaController.deleteMateria(addedMateria.getIdMateria());
        assertTrue(isDeleted, "La materia debería eliminarse correctamente");

        Materia deletedMateria = materiaController.getMateriaById(addedMateria.getIdMateria());
        assertNull(deletedMateria, "La materia eliminada no debería encontrarse");
    }
}