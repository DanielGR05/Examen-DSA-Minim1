package models;

import java.util.LinkedList;
import java.util.List;

public class Instituto {

    private String idInstituto;
    private String nombre;

    // Guardamos solo los IDs de los alumnos para evitar ciclos infinitos al pasar a JSON
    private List<String> alumnosIds;

    // Constructor vacío (necesario para el servicio REST)
    public Instituto() {
        this.alumnosIds = new LinkedList<>();
    }

    // Constructor con parámetros
    public Instituto(String idInstituto, String nombre) {
        this.idInstituto = idInstituto;
        this.nombre = nombre;
        this.alumnosIds = new LinkedList<>();
    }

    // Método para registrar a un alumno en el instituto
    public void addAlumnoId(String idAlumno) {
        this.alumnosIds.add(idAlumno);
    }

    // Getters y Setters
    public String getIdInstituto() {
        return idInstituto;
    }

    public void setIdInstituto(String idInstituto) {
        this.idInstituto = idInstituto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getAlumnosIds() {
        return alumnosIds;
    }

    public void setAlumnosIds(List<String> alumnosIds) {
        this.alumnosIds = alumnosIds;
    }
}