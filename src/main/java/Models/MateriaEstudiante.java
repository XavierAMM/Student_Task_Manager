package Models;

import Controllers.MateriaController;
import Controllers.PersonaController;

public class MateriaEstudiante {
    private int idMatEst; 
    private int idMateria;
    private Materia materia;
    private int idEstudiante;
    private Persona estudiante;

    // Constructor vacío
    public MateriaEstudiante() {}

    // Constructor con parámetros
    public MateriaEstudiante(int idMatEst, int idMateria, int idEstudiante) {
        this.idMatEst = idMatEst;
        this.idMateria = idMateria;
        this.idEstudiante = idEstudiante;
        updateEstudiante();
        updateMateria();
    }

    // Getters y Setters
    public int getIdMatEst() {
        return idMatEst;
    }

    public void setIdMatEst(int idMatEst) {
        this.idMatEst = idMatEst;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
        updateMateria();
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
        updateEstudiante();
    }

    public Materia getMateria() {
        return materia;
    }
    
    private void updateMateria(){
        MateriaController _controller = MateriaController.getInstance();
        this.materia = _controller.getMateriaById(idMateria);
    }

    public Persona getEstudiante() {
        return estudiante;
    }
    
    private void updateEstudiante(){
        PersonaController _controller = PersonaController.getInstance();
        this.estudiante = _controller.getPersonaById(idEstudiante);
    }
    
    // Método toString
    @Override
    public String toString() {
        return "MateriaEstudiante{" +
               "idMatEst=" + idMatEst +
               ", materia=" + materia.getNombre() +
               ", estudiante=" + estudiante.getNombre() + " " + estudiante.getApellido() +
               '}';
    }
}

