package dsa;

import dsa.exceptions.InstitutoNotFoundException;
import dsa.models.Instituto;
import dsa.models.OperacionMatematica;
import java.util.List;

public interface MathManager {

    void solicitarOperacion(String expresion, String idAlumno, String idInstituto);

    OperacionMatematica procesarOperacion();

    List<OperacionMatematica> getOperacionesInstituto(String idInstituto) throws InstitutoNotFoundException;

    List<OperacionMatematica> getOperacionesAlumno(String idAlumno);

    List<Instituto> getInstitutosOrdenados();

    void addAlumno(String idAlumno, String nombre, String idInstituto);
    void addInstituto(String idInstituto, String nombre);
}