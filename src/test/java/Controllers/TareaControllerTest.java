package Controllers;

import Controllers.TareaController;
import Models.Tarea;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TareaControllerTest {

    private static TareaController tareaController;

    private static int testIdTarea;
    private static int testIdMateria = 1; // Cambiar a un ID válido existente en la base de datos

    @BeforeAll
    public static void setUpClass() {
        tareaController = TareaController.getInstance();
    }

    @Test
    @Order(1)
    public void testAddTarea() {
        System.out.println("testAddTarea");

        Tarea nuevaTarea = new Tarea(
                0, 
                testIdMateria, 
                "Test Tarea", 
                "Esta es una descripción de prueba",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );

        boolean isTareaAdded = tareaController.addTarea(nuevaTarea);
        assertTrue(isTareaAdded, "La tarea debería añadirse correctamente");

        List<Tarea> tareas = tareaController.getTareasByIdMateria(testIdMateria);
        Tarea testTarea = tareas.stream()
                .filter(t -> t.getTitulo().equals("Test Tarea"))
                .findFirst()
                .orElse(null);

        assertNotNull(testTarea, "La tarea debería existir después de ser añadida");
        testIdTarea = testTarea.getIdTarea();        
    }

    @Test
    @Order(2)
    public void testUpdateTarea() {
        System.out.println("testUpdateTarea");

        assertTrue(testIdTarea > 0, "El ID de la tarea debe ser válido antes de actualizar");

        Tarea tareaActualizada = new Tarea(
                testIdTarea,
                testIdMateria,
                "Test Tarea Actualizada",
                "Descripción actualizada de la tarea",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(14)
        );

        boolean isTareaUpdated = tareaController.updateTarea(tareaActualizada);
        assertTrue(isTareaUpdated, "La tarea debería actualizarse correctamente");

        Tarea updatedTarea = tareaController.getTareaById(testIdTarea);
        assertNotNull(updatedTarea, "La tarea actualizada debería existir");
        assertEquals("Test Tarea Actualizada", updatedTarea.getTitulo(), "El título debería coincidir con la actualización");
        assertEquals("Descripción actualizada de la tarea", updatedTarea.getDescripcion(), "La descripción debería coincidir con la actualización");
    }

    @Test
    @Order(3)
    public void testDeleteTarea() {
        System.out.println("testDeleteTarea");

        assertTrue(testIdTarea > 0, "El ID de la tarea debe ser válido antes de eliminar");

        boolean isTareaDeleted = tareaController.deleteTarea(testIdTarea);
        assertTrue(isTareaDeleted, "La tarea debería eliminarse correctamente");

        Tarea deletedTarea = tareaController.getTareaById(testIdTarea);
        assertNull(deletedTarea, "La tarea eliminada no debería existir");
    }
}
