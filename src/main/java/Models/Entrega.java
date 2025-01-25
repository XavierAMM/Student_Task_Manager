package Models;

import Controllers.PersonaController;
import Controllers.TareaController;
import java.time.LocalDateTime;

public class Entrega {
    private int idEntrega; 
    private int idTarea; 
    private Tarea tarea;
    private int idEstudiante;
    private Persona estudiante;
    private LocalDateTime fechaEntrega;
    private Integer calificacion; 
    private String titulo;
    private String descripcion;

    // Constructor vacío
    public Entrega() {}

    // Constructor con parámetros
    public Entrega(int idEntrega, int idTarea, int idEstudiante, LocalDateTime fechaEntrega, Integer calificacion, String titulo, String descripcion) {
        this.idEntrega = idEntrega;
        this.idTarea = idTarea;
        this.idEstudiante = idEstudiante;
        this.fechaEntrega = fechaEntrega;
        this.calificacion = calificacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        updateTarea();
        updateEstudiante();
    }
    
    public Entrega(int idTarea, int idEstudiante, LocalDateTime fechaEntrega, String titulo, String descripcion) {        
        this.idTarea = idTarea;
        this.idEstudiante = idEstudiante;
        this.fechaEntrega = fechaEntrega;
        this.calificacion = -1;
        this.titulo = titulo;
        this.descripcion = descripcion;
        updateTarea();
        updateEstudiante();
    }

    // Getters y Setters
    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public Tarea getTarea() {
        return tarea;
    }
    
    private void updateTarea(){
        TareaController _controller = TareaController.getInstance();
        this.tarea = _controller.getTareaById(idTarea);
    }

    public Persona getEstudiante() {
        return estudiante;
    }

    private void updateEstudiante(){
        PersonaController _controller = PersonaController.getInstance();
        this.estudiante = _controller.getPersonaById(idEstudiante);
    }
    
    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
        updateTarea();
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
        updateEstudiante();
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        String calif = "";
        if(this.calificacion>=0) calif = " (" + this.calificacion + ")";
        return this.estudiante.getNombre() + " " + this.estudiante.getApellido() + calif;
    }
}
