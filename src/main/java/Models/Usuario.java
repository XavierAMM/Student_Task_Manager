package Models;

import Controllers.PersonaController;

public class Usuario {
    private int idUsuario;
    private int idPersona;
    private Persona persona;
    private String usuario; 
    private char[] contrasena;

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(int idUsuario, int idPersona, String usuario, char [] contrasena) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.usuario = usuario;
        this.contrasena = contrasena;
        updatePersona();
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Persona getPersona() {
        return persona;
    }
    
    private void updatePersona(){
        PersonaController _controller = PersonaController.getInstance();
        this.persona = _controller.getPersonaById(idPersona);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public char [] getContrasena() {
        return contrasena;
    }

    public void setContrasena(char [] contrasena) {
        this.contrasena = contrasena;
    }

    // Método toString
    @Override
    public String toString() {
        return "Usuario{" +
               "idUsuario=" + idUsuario +
               ", persona=" + persona.getNombre() + " " + persona.getApellido() +
               ", usuario='" + usuario + '\'' +
               '}';
    }
    
    // Limpiar el contenido de la contraseña para seguridad
    public static void clearContrasena(char [] contrasena) {
        java.util.Arrays.fill(contrasena, '\0');
    }
}

