package Controllers;
import Controllers.MateriaEstudianteController;
import Models.MateriaEstudiante;
import Models.Materia;
import Models.Persona;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MateriaEstudianteControllerTest {

    private static MateriaEstudianteController materiaEstudianteController;

    @BeforeAll
    public static void setUpClass() {        
        materiaEstudianteController = MateriaEstudianteController.getInstance();
    }

    @AfterAll
    public static void tearDownClass() {        
        materiaEstudianteController = null;
    }

    @Test
    public void testGetInstance() {
        System.out.println("testGetInstance");
        MateriaEstudianteController instance = MateriaEstudianteController.getInstance();
        assertNotNull(instance, "La instancia no debería ser nula");
    }

    @Test
    public void testAddAndGetMateriaEstudiante() {
        System.out.println("testAddAndGetMateriaEstudiante");

        int testIdMateria = 1; // Supongamos que existe la materia con ID 1
        int testIdEstudiante = 1; // Supongamos que existe el estudiante con ID 1

        boolean isAdded = materiaEstudianteController.addMateriaEstudiante(testIdMateria, testIdEstudiante);
        assertTrue(isAdded, "El vínculo entre materia y estudiante debería añadirse correctamente");

        List<MateriaEstudiante> relaciones = materiaEstudianteController.getAllMateriaEstudiante();
        MateriaEstudiante addedRelation = relaciones.stream()
                .filter(relation -> relation.getIdMateria() == testIdMateria && relation.getIdEstudiante() == testIdEstudiante)
                .findFirst()
                .orElse(null);

        assertNotNull(addedRelation, "La relación añadida debería existir en la tabla intermedia");

        boolean isDeleted = materiaEstudianteController.deleteMateriaEstudiante(addedRelation.getIdMatEst());
        assertTrue(isDeleted, "La relación debería eliminarse correctamente");
    }

    @Test
    public void testGetMateriasByIdEstudiante() {
        System.out.println("testGetMateriasByIdEstudiante");

        int testIdEstudiante = 1; // Supongamos que existe el estudiante con ID 1
        List<Materia> materias = materiaEstudianteController.getMateriasByIdEstudiante(testIdEstudiante);

        assertNotNull(materias, "La lista de materias no debería ser nula");
        assertTrue(materias.size() > 0, "El estudiante debería estar inscrito en al menos una materia");
    }

    @Test
    public void testUpdateMateriaEstudiante() {
        System.out.println("testUpdateMateriaEstudiante");

        int testIdMateria = 1; // Supongamos que existe la materia con ID 1
        int testIdEstudiante = 1; // Supongamos que existe el estudiante con ID 1

        boolean isAdded = materiaEstudianteController.addMateriaEstudiante(testIdMateria, testIdEstudiante);
        assertTrue(isAdded, "El vínculo entre materia y estudiante debería añadirse correctamente");

        List<MateriaEstudiante> relaciones = materiaEstudianteController.getAllMateriaEstudiante();
        MateriaEstudiante addedRelation = relaciones.stream()
                .filter(relation -> relation.getIdMateria() == testIdMateria && relation.getIdEstudiante() == testIdEstudiante)
                .findFirst()
                .orElse(null);

        assertNotNull(addedRelation, "La relación añadida debería existir en la tabla intermedia");

        int updatedIdMateria = 2; // Supongamos que existe otra materia con ID 2
        boolean isUpdated = materiaEstudianteController.updateMateriaEstudiante(addedRelation.getIdMatEst(), updatedIdMateria, testIdEstudiante);
        assertTrue(isUpdated, "La relación debería actualizarse correctamente");

        boolean isDeleted = materiaEstudianteController.deleteMateriaEstudiante(addedRelation.getIdMatEst());
        assertTrue(isDeleted, "La relación debería eliminarse correctamente después de la actualización");
    }

    @Test
    public void testDeleteMateriaEstudiante() {
        System.out.println("testDeleteMateriaEstudiante");

        int testIdMateria = 1; // Supongamos que existe la materia con ID 1
        int testIdEstudiante = 1; // Supongamos que existe el estudiante con ID 1

        boolean isAdded = materiaEstudianteController.addMateriaEstudiante(testIdMateria, testIdEstudiante);
        assertTrue(isAdded, "El vínculo entre materia y estudiante debería añadirse correctamente");

        List<MateriaEstudiante> relaciones = materiaEstudianteController.getAllMateriaEstudiante();
        MateriaEstudiante addedRelation = relaciones.stream()
                .filter(relation -> relation.getIdMateria() == testIdMateria && relation.getIdEstudiante() == testIdEstudiante)
                .findFirst()
                .orElse(null);

        assertNotNull(addedRelation, "La relación añadida debería existir en la tabla intermedia");

        boolean isDeleted = materiaEstudianteController.deleteMateriaEstudiante(addedRelation.getIdMatEst());
        assertTrue(isDeleted, "La relación debería eliminarse correctamente");
    }
}
