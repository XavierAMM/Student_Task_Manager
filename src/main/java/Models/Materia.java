package Models;

import Controllers.PersonaController;

public class Materia {
    private int idMateria;
    private String nombre;
    private int idDocente;
    private Persona docente;

    // Constructor vacío
    public Materia() {}

    // Constructor con parámetros
    public Materia(int idMateria, String nombre, int idDocente) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.idDocente = idDocente;
        updateDocente();
    }

    // Getters y Setters
    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
        updateDocente();
    }

    public Persona getDocente() {
        return docente;
    }
    
    private void updateDocente(){
        PersonaController _controller = PersonaController.getInstance();
        this.docente = _controller.getPersonaById(idDocente);
    }
    
    
    // Método toString
    @Override
    public String toString() {
        return "Materia{" +
               "idMateria=" + idMateria +
               ", nombre='" + nombre + '\'' +
               ", docente=" + docente.getNombre() + " " + docente.getApellido() +
               '}';
    }
}

