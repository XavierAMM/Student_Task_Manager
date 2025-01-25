package Controllers;

import Models.Materia;
import Models.MateriaEstudiante;
import Models.OracleConnection;
import Models.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaEstudianteController {
    private static MateriaEstudianteController instance;
    private final OracleConnection db;

    // Constructor privado
    private MateriaEstudianteController() {
        db = new OracleConnection();
    }

    // Método para obtener la instancia única
    public static MateriaEstudianteController getInstance() {
        if (instance == null) {
            instance = new MateriaEstudianteController();
        }
        return instance;
    }
    
    public List<Persona> getEstudiantesByIdMateria(int idMateria){
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT Persona.* FROM MATERIA_ESTUDIANTE INNER JOIN Persona ON MATERIA_ESTUDIANTE.id_estudiante = Persona.id_persona WHERE MATERIA_ESTUDIANTE.id_materia = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                personas.add(new Persona(
                        rs.getInt("id_persona"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cedula"),
                        rs.getString("correo"),
                        rs.getInt("id_rol")
                ));            
            }
            return personas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Materia> getMateriasByIdEstudiante(int idEstudiante){
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT Materia.* FROM MATERIA_ESTUDIANTE INNER JOIN Materia ON MATERIA_ESTUDIANTE.id_materia = Materia.id_materia WHERE MATERIA_ESTUDIANTE.id_estudiante = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                materias.add(new Materia(
                        rs.getInt("id_materia"),
                        rs.getString("nombre"),
                        rs.getInt("id_docente")
                ));            
            }
            return materias;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para añadir una nueva relación materia-estudiante
    public boolean addMateriaEstudiante(int idMateria, int idEstudiante) {
        String sql = "INSERT INTO Materia_Estudiante (id_materia, id_estudiante) VALUES (?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            stmt.setInt(2, idEstudiante);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para leer una relación específica por id_mat_est
    public MateriaEstudiante getMateriaEstudianteById(int idMatEst) {
        String sql = "SELECT * FROM Materia_Estudiante WHERE id_mat_est = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMatEst);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new MateriaEstudiante(
                        rs.getInt("id_mat_est"),
                        rs.getInt("id_materia"),
                        rs.getInt("id_estudiante")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra la relación
    }

    // Método para leer todas las relaciones materia-estudiante
    public List<MateriaEstudiante> getAllMateriaEstudiante() {
        List<MateriaEstudiante> relaciones = new ArrayList<>();
        String sql = "SELECT * FROM Materia_Estudiante";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                relaciones.add(new MateriaEstudiante(
                        rs.getInt("id_mat_est"),
                        rs.getInt("id_materia"),
                        rs.getInt("id_estudiante")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relaciones;
    }

    // Método para editar una relación materia-estudiante
    public boolean updateMateriaEstudiante(int idMatEst, int idMateria, int idEstudiante) {
        String sql = "UPDATE Materia_Estudiante SET id_materia = ?, id_estudiante = ? WHERE id_mat_est = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            stmt.setInt(2, idEstudiante);
            stmt.setInt(3, idMatEst);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una relación materia-estudiante
    public boolean deleteMateriaEstudiante(int idMatEst) {
        String sql = "DELETE FROM Materia_Estudiante WHERE id_mat_est = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMatEst);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
