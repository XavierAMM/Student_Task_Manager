package Controllers;

import Models.Entrega;
import Models.OracleConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntregaController {
    private static EntregaController instance;
    private final OracleConnection db;

    // Constructor privado
    private EntregaController() {
        db = new OracleConnection();
    }

    // Método para obtener la instancia única
    public static EntregaController getInstance() {
        if (instance == null) {
            instance = new EntregaController();
        }
        return instance;
    }    
    
    public List<Entrega> getEntregasByIdEstudianteIdMateria(int idEstudiante, int idMateria){
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT ENTREGA.* FROM ENTREGA JOIN TAREA ON ENTREGA.ID_TAREA = TAREA.ID_TAREA JOIN MATERIA ON TAREA.ID_MATERIA = MATERIA.ID_MATERIA WHERE ENTREGA.ID_ESTUDIANTE = ? AND MATERIA.ID_MATERIA = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entregas.add(new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_tarea"),
                        rs.getInt("id_estudiante"),
                        rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime() : null,
                        rs.getObject("calificacion", Integer.class),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                ));
            }
            
            return entregas;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }    
    
    public List<Entrega> getEntregasByIdEstudiante(int idEstudiante){
        List<Entrega> entregas = new ArrayList<>();
        String sql = "select * from entrega where id_estudiante = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);           
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entregas.add(new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_tarea"),
                        rs.getInt("id_estudiante"),
                        rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime() : null,
                        rs.getObject("calificacion", Integer.class),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                ));
            }
            
            return entregas;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    public List<Entrega> getEntregasByIdMateria(int idMateria){
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT * FROM ENTREGA JOIN TAREA ON ENTREGA.ID_TAREA = TAREA.ID_TAREA JOIN MATERIA ON TAREA.ID_MATERIA = MATERIA.ID_MATERIA WHERE MATERIA.ID_MATERIA = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entregas.add(new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_tarea"),
                        rs.getInt("id_estudiante"),
                        rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime() : null,
                        rs.getObject("calificacion", Integer.class),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                ));
            }
            
            return entregas;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    public Entrega getEntregaByIdTareaAndIdEstudiante(int idTarea, int idEstudiante) {
        String sql = "SELECT * FROM Entrega WHERE id_tarea = ? and id_estudiante = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarea);
            stmt.setInt(2, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_tarea"),
                        rs.getInt("id_estudiante"),
                        rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime() : null,
                        rs.getObject("calificacion", Integer.class),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra la entrega
    }
    
    public List<Entrega> getEntregasByIdTarea(int idTarea){
        List<Entrega> entregas = new ArrayList<>();
        String sql = "select * from entrega where id_tarea = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarea);           
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entregas.add(new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_tarea"),
                        rs.getInt("id_estudiante"),
                        rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime() : null,
                        rs.getObject("calificacion", Integer.class),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                ));
            }
            
            return entregas;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    

    // Método para añadir una nueva entrega
    public boolean addEntrega(Entrega e) {
        String sql = "INSERT INTO Entrega (id_tarea, id_estudiante, fecha_entrega, calificacion, titulo, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, e.getIdTarea());
            stmt.setInt(2, e.getIdEstudiante());
            stmt.setTimestamp(3, e.getFechaEntrega() != null ? Timestamp.valueOf(e.getFechaEntrega()) : null);
            stmt.setObject(4, e.getCalificacion(), Types.INTEGER);
            stmt.setString(5, e.getTitulo());
            stmt.setString(6, e.getDescripcion());
            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    

    // Método para leer una entrega específica por id
    public Entrega getEntregaById(int idEntrega) {
        String sql = "SELECT * FROM Entrega WHERE id_entrega = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEntrega);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_tarea"),
                        rs.getInt("id_estudiante"),
                        rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime() : null,
                        rs.getObject("calificacion", Integer.class),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra la entrega
    }

    // Método para leer todas las entregas
    public List<Entrega> getAllEntregas() {
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT * FROM Entrega";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                entregas.add(new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_tarea"),
                        rs.getInt("id_estudiante"),
                        rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime() : null,
                        rs.getObject("calificacion", Integer.class),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    // Método para editar una entrega
    public boolean updateEntrega(Entrega e) {
        String sql = "UPDATE Entrega SET id_tarea = ?, id_estudiante = ?, fecha_entrega = ?, calificacion = ?, titulo = ?, descripcion = ? WHERE id_entrega = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, e.getIdTarea());
            stmt.setInt(2, e.getIdEstudiante());
            stmt.setTimestamp(3, e.getFechaEntrega() != null ? Timestamp.valueOf(e.getFechaEntrega()) : null);
            stmt.setObject(4, e.getCalificacion(), Types.INTEGER);
            stmt.setString(5, e.getTitulo());
            stmt.setString(6, e.getDescripcion());
            stmt.setInt(7, e.getIdEntrega());
            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {            
            ex.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una entrega
    public boolean deleteEntrega(int idEntrega) {
        String sql = "DELETE FROM Entrega WHERE id_entrega = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEntrega);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteEntregasByIdTarea(int idTarea) {
        String sql = "DELETE FROM Entrega WHERE id_tarea = ?";
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
