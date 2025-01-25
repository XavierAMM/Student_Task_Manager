package Controllers;

import Models.Materia;
import Models.OracleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaController {
    private static MateriaController instance;
    private final OracleConnection db;

    // Constructor privado
    private MateriaController() {
        db = new OracleConnection();
    }

    // Método para obtener la instancia única
    public static MateriaController getInstance() {
        if (instance == null) {
            instance = new MateriaController();
        }
        return instance;
    }
    
    public List<Materia> getMateriasByIdDocente(int idDocente){
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM Materia WHERE id_docente = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDocente);
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

    // Método para añadir una nueva materia
    public boolean addMateria(String nombre, int idDocente) {
        String sql = "INSERT INTO Materia (nombre, id_docente) VALUES (?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setInt(2, idDocente);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para leer una materia específica por id
    public Materia getMateriaById(int idMateria) {
        String sql = "SELECT * FROM Materia WHERE id_materia = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Materia(
                        rs.getInt("id_materia"),
                        rs.getString("nombre"),
                        rs.getInt("id_docente")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra la materia
    }
    
    

    // Método para leer todas las materias
    public List<Materia> getAllMaterias() {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM Materia";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materias.add(new Materia(
                        rs.getInt("id_materia"),
                        rs.getString("nombre"),
                        rs.getInt("id_docente")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }

    // Método para editar una materia
    public boolean updateMateria(int idMateria, String nombre, int idDocente) {
        String sql = "UPDATE Materia SET nombre = ?, id_docente = ? WHERE id_materia = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setInt(2, idDocente);
            stmt.setInt(3, idMateria);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una materia
    public boolean deleteMateria(int idMateria) {
        String sql = "DELETE FROM Materia WHERE id_materia = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
