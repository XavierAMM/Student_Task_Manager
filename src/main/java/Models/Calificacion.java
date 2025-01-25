package Models;

public class Calificacion {
    private Tarea tarea;
    private Materia materia;
    private int calificacion;

    public Calificacion(Tarea tarea, int calificacion) {
        this.tarea = tarea;
        this.materia = tarea.getMateria();
        this.calificacion = calificacion;
    }
    
    public Calificacion(Materia materia, int calificacion) {
        this.tarea = null;
        this.materia = materia;
        this.calificacion = calificacion;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
    
    
}
