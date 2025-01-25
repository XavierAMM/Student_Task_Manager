package Models;

import Controllers.RolController;

public class Persona {
    private int idPersona; 
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private int idRol; 
    private Rol rol;

    // Constructor vacío
    public Persona() {}

    // Constructor con parámetros
    public Persona(int idPersona, String nombre, String apellido, String cedula, String correo, int idRol) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correo = correo;
        this.idRol = idRol;
        updateRol();
    }

    // Getters y Setters
    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
        updateRol();
    }    
    
    public Rol getRol() {
        return rol;
    }
    
    private void updateRol(){
        RolController _controller = RolController.getInstance();
        this.rol = _controller.getRolById(idRol);
    }

    // Método toString
    @Override
    public String toString() {
        return "Persona{" +
               "idPersona=" + idPersona +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", cedula='" + cedula + '\'' +
               ", correo='" + correo + '\'' +
               ", rol=" + rol.getDescripcion() +
               '}';
    }
}

