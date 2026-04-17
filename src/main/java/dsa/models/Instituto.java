package dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Instituto {

    private String idInstituto;
    private String nombre;

    private List<String> alumnosIds;

    public Instituto() {
        this.alumnosIds = new LinkedList<>();
    }

    public Instituto(String idInstituto, String nombre) {
        this.idInstituto = idInstituto;
        this.nombre = nombre;
        this.alumnosIds = new LinkedList<>();
    }

    public void addAlumnoId(String idAlumno) {
        this.alumnosIds.add(idAlumno);
    }

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