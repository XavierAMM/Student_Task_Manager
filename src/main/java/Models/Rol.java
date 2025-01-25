package Models;

public class Rol {
    private int idRol; 
    private String descripcion;

    // Constructor vacío
    public Rol() {}

    // Constructor con parámetros
    public Rol(int idRol, String descripcion) {
        this.idRol = idRol;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método toString
    @Override
    public String toString() {
        return "Rol{" +
               "idRol=" + idRol +
               ", descripcion='" + descripcion + '\'' +
               '}';
    }
}

