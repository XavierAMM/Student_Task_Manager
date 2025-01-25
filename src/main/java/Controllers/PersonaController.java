package Controllers;

import Models.Persona;
import Models.OracleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaController {
    private static PersonaController instance;
    private final OracleConnection db;

    // Constructor privado
    private PersonaController() {
        db = new OracleConnection();
    }

    // Método para obtener la instancia única
    public static PersonaController getInstance() {
        if (instance == null) {
            instance = new PersonaController();
        }
        return instance;
    }

    // Método para añadir una nueva persona
    public boolean addPersona(String nombre, String apellido, String cedula, String correo, int idRol) {
        String sql = "INSERT INTO Persona (nombre, apellido, cedula, correo, id_rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, cedula);
            stmt.setString(4, correo);
            stmt.setInt(5, idRol);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para leer una persona específica por id
    public Persona getPersonaById(int idPersona) {
        String sql = "SELECT * FROM Persona WHERE id_persona = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Persona(
                        rs.getInt("id_persona"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cedula"),
                        rs.getString("correo"),
                        rs.getInt("id_rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra la persona
    }

    // Método para leer todas las personas
    public List<Persona> getAllPersonas() {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM Persona";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    // Método para editar una persona
    public boolean updatePersona(int idPersona, String nombre, String apellido, String cedula, String correo, int idRol) {
        String sql = "UPDATE Persona SET nombre = ?, apellido = ?, cedula = ?, correo = ?, id_rol = ? WHERE id_persona = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, cedula);
            stmt.setString(4, correo);
            stmt.setInt(5, idRol);
            stmt.setInt(6, idPersona);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una persona
    public boolean deletePersona(int idPersona) {
        String sql = "DELETE FROM Persona WHERE id_persona = ?";
        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
