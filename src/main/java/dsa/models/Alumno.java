package models;

import java.util.LinkedList;
import java.util.List;

public class Alumno {

    private String idAlumno;
    private String nombre;
    private String idInstituto;

    // Listado de operaciones matemáticas realizadas por un alumno
    private List<OperacionMatematica> operacionesRealizadas;

    // Constructor vacío (necesario para el servicio REST)
    public Alumno() {
        this.operacionesRealizadas = new LinkedList<>();
    }

    // Constructor con parámetros
    public Alumno(String idAlumno, String nombre, String idInstituto) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.idInstituto = idInstituto;
        this.operacionesRealizadas = new LinkedList<>();
    }

    // Método para añadir una operación al historial
    public void addOperacion(OperacionMatematica operacion) {
        this.operacionesRealizadas.add(operacion);
    }

    // Getters y Setters
    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdInstituto() {
        return idInstituto;
    }

    public void setIdInstituto(String idInstituto) {
        this.idInstituto = idInstituto;
    }

    public List<OperacionMatematica> getOperacionesRealizadas() {
        return operacionesRealizadas;
    }

    public void setOperacionesRealizadas(List<OperacionMatematica> operacionesRealizadas) {
        this.operacionesRealizadas = operacionesRealizadas;
    }
}