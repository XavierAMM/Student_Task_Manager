package Controllers;

import Models.Tarea;
import Models.OracleConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TareaController {
    private static TareaController instance;
    private final OracleConnection db;

    // Constructor privado
    private TareaController() {
        db = new OracleConnection();
    }

    // Método para obtener la instancia única
    public static TareaController getInstance() {
        if (instance == null) {
            instance = new TareaController();
        }
        return instance;
    }

    // Método para añadir una nueva tarea
    public boolean addTarea(Tarea t) {
        String sql = "INSERT INTO Tarea (id_materia, titulo, descripcion, fecha_creacion, fecha_entrega) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, t.getIdMateria());
            stmt.setString(2, t.getTitulo());
            stmt.setString(3, t.getDescripcion());
            stmt.setTimestamp(4, Timestamp.valueOf(t.getFechaCreacion()));
            stmt.setTimestamp(5, Timestamp.valueOf(t.getFechaEntrega()));
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Tarea> getTareasByIdMateria(int idMateria){        
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tarea WHERE id_materia = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tareas.add(new Tarea(
                        rs.getInt("id_tarea"),
                        rs.getInt("id_materia"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getTimestamp("fecha_entrega").toLocalDateTime()
                ));            
            }
            return tareas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }

    // Método para leer una tarea específica por id
    public Tarea getTareaById(int idTarea) {
        String sql = "SELECT * FROM Tarea WHERE id_tarea = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarea);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Tarea(
                        rs.getInt("id_tarea"),
                        rs.getInt("id_materia"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getTimestamp("fecha_entrega").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra la tarea
    }

    // Método para leer todas las tareas
    public List<Tarea> getAllTareas() {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tarea";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tareas.add(new Tarea(
                        rs.getInt("id_tarea"),
                        rs.getInt("id_materia"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getTimestamp("fecha_entrega").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tareas;
    }

    // Método para editar una tarea
    public boolean updateTarea(Tarea t) {
        String sql = "UPDATE Tarea SET id_materia = ?, titulo = ?, descripcion = ?, fecha_creacion = ?, fecha_entrega = ? WHERE id_tarea = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, t.getIdMateria());
            stmt.setString(2, t.getTitulo());
            stmt.setString(3, t.getDescripcion());
            stmt.setTimestamp(4, Timestamp.valueOf(t.getFechaCreacion()));
            stmt.setTimestamp(5, Timestamp.valueOf(t.getFechaEntrega()));
            stmt.setInt(6, t.getIdTarea());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una tarea
    public boolean deleteTarea(int idTarea) {
        String sql = "DELETE FROM Tarea WHERE id_tarea = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarea);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
