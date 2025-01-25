package Controllers;

import Models.Usuario;
import Models.OracleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuarioController {

    private static UsuarioController instance;
    private final OracleConnection db;
    protected static int mainUserId;

    public static Usuario getMainUser() {
        UsuarioController _uc = UsuarioController.getInstance();
        return _uc.getUsuarioById(mainUserId);
    }

    public void setMainUser(int mainUserId) {
        UsuarioController.mainUserId = mainUserId;
    }

    // Constructor privado
    private UsuarioController() {
        db = new OracleConnection();
    }

    // Método para obtener la instancia única
    public static UsuarioController getInstance() {
        if (instance == null) {
            instance = new UsuarioController();
        }
        return instance;
    }

    // Método para añadir un nuevo usuario
    public boolean addUsuario(int id_persona, String usuario, char[] contrasena) {
        String sql = "INSERT INTO Usuario (id_persona, usuario, contrasena) VALUES (?, ?, ?)";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (contrasena == null) {
                throw new IllegalArgumentException("La contraseña no puede ser null.");
            }

            String passString = new String(contrasena);
            stmt.setInt(1, id_persona); // Configurar id_persona
            stmt.setString(2, usuario); // Configurar usuario
            stmt.setString(3, passString); // Configurar contraseña

            Usuario.clearContrasena(contrasena); // Limpiar contraseña para mayor seguridad
            return stmt.executeUpdate() > 0; // Ejecutar la consulta

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para leer un usuario específico por id_usuario
    public Usuario getUsuarioById(int idUsuario) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Convertir la contraseña a char[]
                char[] passArray = rs.getString("contrasena").toCharArray();
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getInt("id_persona"),
                        rs.getString("usuario"),
                        passArray
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra el usuario
    }

    // Método para leer todos los usuarios
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Connection conn = db.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                char[] passArray = rs.getString("contrasena").toCharArray();
                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getInt("id_persona"),
                        rs.getString("usuario"),
                        passArray
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para editar un usuario
    public boolean updateUsuario(int idUsuario, Usuario newUsuario) {
        String sql = "UPDATE Usuario SET id_persona = ?, usuario = ?, contrasena = ? WHERE id_usuario = ?";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String passString = new String(newUsuario.getContrasena());
            stmt.setInt(1, newUsuario.getIdPersona());
            stmt.setString(2, newUsuario.getUsuario());
            stmt.setString(3, passString);
            stmt.setInt(4, idUsuario);
            Usuario.clearContrasena(newUsuario.getContrasena());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un usuario
    public boolean deleteUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para validar Login
    public Usuario validarLogin(String usuario, char[] contrasena) throws Exception {
        String sql = "SELECT * FROM Usuario WHERE usuario = ? AND contrasena = ?";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String passString = new String(contrasena);

            stmt.setString(1, usuario);
            stmt.setString(2, passString);

            Usuario.clearContrasena(contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Convertir la contraseña a char[]
                char[] passArray = rs.getString("contrasena").toCharArray();
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getInt("id_persona"),
                        rs.getString("usuario"),
                        passArray
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("Usuario o contraseña inválido.");
    }
}
