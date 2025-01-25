package Controllers;
import Controllers.EntregaController;
import Models.Entrega;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class EntregaControllerTest {

    private static EntregaController entregaController;

    @BeforeAll
    public static void setUpClass() {
        entregaController = EntregaController.getInstance();
    }

    @AfterAll
    public static void tearDownClass() {
        entregaController = null;
    }

    @Test
    public void testAddEntrega() {
        System.out.println("addEntrega");
        Entrega nuevaEntrega = new Entrega(1, 1, LocalDateTime.now(), "Test Título", "Test Descripción");
        boolean result = entregaController.addEntrega(nuevaEntrega);
        assertTrue(result, "La entrega debería añadirse correctamente");

        // Obtener el ID generado automáticamente
        List<Entrega> entregas = entregaController.getEntregasByIdEstudiante(1);
        Entrega ultimaEntrega = entregas.stream()
                .filter(e -> "Test Título".equals(e.getTitulo()) && "Test Descripción".equals(e.getDescripcion()))
                .findFirst()
                .orElse(null);

        assertNotNull(ultimaEntrega, "La entrega añadida debería estar disponible en la base de datos");

        // Eliminar la entrega creada
        boolean deleteResult = entregaController.deleteEntrega(ultimaEntrega.getIdEntrega());
        assertTrue(deleteResult, "La entrega debería eliminarse correctamente");
    }

    @Test
    public void testUpdateEntrega() {
        System.out.println("updateEntrega");
        // Crear una entrega para actualizar
        Entrega nuevaEntrega = new Entrega(1, 1, LocalDateTime.now(), "Título Original", "Descripción Original");
        entregaController.addEntrega(nuevaEntrega);

        // Obtener la entrega recién añadida
        List<Entrega> entregas = entregaController.getEntregasByIdEstudiante(1);
        Entrega entregaParaActualizar = entregas.stream()
                .filter(e -> "Título Original".equals(e.getTitulo()))
                .findFirst()
                .orElse(null);

        assertNotNull(entregaParaActualizar, "La entrega debería existir antes de actualizarla");

        // Actualizar la entrega
        entregaParaActualizar.setTitulo("Título Actualizado");
        entregaParaActualizar.setDescripcion("Descripción Actualizada");
        boolean updateResult = entregaController.updateEntrega(entregaParaActualizar);
        assertTrue(updateResult, "La entrega debería actualizarse correctamente");

        // Verificar la actualización
        Entrega entregaActualizada = entregaController.getEntregaById(entregaParaActualizar.getIdEntrega());
        assertEquals("Título Actualizado", entregaActualizada.getTitulo(), "El título debería haberse actualizado");
        assertEquals("Descripción Actualizada", entregaActualizada.getDescripcion(), "La descripción debería haberse actualizado");

        // Eliminar la entrega
        entregaController.deleteEntrega(entregaParaActualizar.getIdEntrega());
    }

    @Test
    public void testDeleteEntrega() {
        System.out.println("deleteEntrega");
        // Crear una entrega para eliminar
        Entrega nuevaEntrega = new Entrega(1, 1, LocalDateTime.now(), "Título a Eliminar", "Descripción a Eliminar");
        entregaController.addEntrega(nuevaEntrega);

        // Obtener la entrega recién añadida
        List<Entrega> entregas = entregaController.getEntregasByIdEstudiante(1);
        Entrega entregaParaEliminar = entregas.stream()
                .filter(e -> "Título a Eliminar".equals(e.getTitulo()))
                .findFirst()
                .orElse(null);

        assertNotNull(entregaParaEliminar, "La entrega debería existir antes de eliminarla");

        // Eliminar la entrega
        boolean deleteResult = entregaController.deleteEntrega(entregaParaEliminar.getIdEntrega());
        assertTrue(deleteResult, "La entrega debería eliminarse correctamente");

        // Verificar que ya no exista
        Entrega entregaEliminada = entregaController.getEntregaById(entregaParaEliminar.getIdEntrega());
        assertNull(entregaEliminada, "La entrega ya no debería estar disponible en la base de datos");
    }

    @Test
    public void testDeleteEntregasByIdTarea() {
        System.out.println("deleteEntregasByIdTarea");
        // Crear varias entregas asociadas a una tarea
        int idTarea = 1;
        entregaController.addEntrega(new Entrega(idTarea, 1, LocalDateTime.now(), "Entrega 1", "Descripción 1"));
        entregaController.addEntrega(new Entrega(idTarea, 2, LocalDateTime.now(), "Entrega 2", "Descripción 2"));

        // Verificar que existen entregas
        List<Entrega> entregas = entregaController.getEntregasByIdTarea(idTarea);
        assertFalse(entregas.isEmpty(), "Deberían existir entregas asociadas a la tarea antes de eliminarlas");

        // Eliminar entregas asociadas a la tarea
        boolean deleteResult = entregaController.deleteEntregasByIdTarea(idTarea);
        assertTrue(deleteResult, "Las entregas asociadas a la tarea deberían eliminarse correctamente");

        // Verificar que se eliminaron
        List<Entrega> entregasEliminadas = entregaController.getEntregasByIdTarea(idTarea);
        assertTrue(entregasEliminadas.isEmpty(), "No deberían existir entregas asociadas a la tarea después de eliminarlas");
    }
}
