
package Models;

import Controllers.MateriaController;
import java.time.LocalDateTime;

public class Tarea {
    private int idTarea; 
    private int idMateria;
    private Materia materia;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion; 
    private LocalDateTime fechaEntrega;

    // Constructor vacío
    public Tarea() {}

    // Constructor con parámetros
    public Tarea(int idTarea, int idMateria, String titulo, String descripcion, LocalDateTime fechaCreacion, LocalDateTime fechaEntrega) {
        this.idTarea = idTarea;
        this.idMateria = idMateria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
        updateMateria();
    }

    // Getters y Setters
    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
        updateMateria();
    }

    public Materia getMateria() {
        return materia;
    }
    
    private void updateMateria(){
        MateriaController _controller = MateriaController.getInstance();
        this.materia = _controller.getMateriaById(idMateria);
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    // Método toString
    @Override
    public String toString() {
        return "Tarea{" +
               "idTarea=" + idTarea +
               ", materia=" + materia.getNombre() +
               ", titulo='" + titulo + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", fechaCreacion=" + fechaCreacion +
               ", fechaEntrega=" + fechaEntrega +
               '}';
    }
}
