package Controllers;

import Models.Rol;
import Models.OracleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolController {
    private static RolController instance; 
    private final OracleConnection db;

    // Constructor privado
    private RolController() {
        db = new OracleConnection();
    }

    // Método para obtener la instancia única
    public static RolController getInstance() {
        if (instance == null) {
            instance = new RolController();
        }
        return instance;
    }

    // Método para añadir un nuevo rol
    public boolean addRol(String descripcion) {
        String sql = "INSERT INTO Rol (descripcion) VALUES (?)";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descripcion);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para leer un rol específico por id
    public Rol getRolById(int idRol) {
        String sql = "SELECT * FROM Rol WHERE id_rol = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRol);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra el rol
    }

    // Método para leer todos los roles
    public List<Rol> getAllRoles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM Rol";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    // Método para editar un rol
    public boolean updateRol(int idRol, String descripcion) {
        String sql = "UPDATE Rol SET descripcion = ? WHERE id_rol = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descripcion);
            stmt.setInt(2, idRol);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un rol
    public boolean deleteRol(int idRol) {
        String sql = "DELETE FROM Rol WHERE id_rol = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRol);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
