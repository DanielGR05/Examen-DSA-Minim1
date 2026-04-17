import models.Alumno;
import models.Instituto;
import models.OperacionMatematica;
import java.util.List;

public interface MathManager {

    /**
     * Requerir que se solucioni una operació matemàtica basada en notació polaca inversa
     * per part d'un alumne que pertany a un institut.
     */
    void solicitarOperacion(String expresion, String idAlumno, String idInstituto);

    /**
     * Processar una operació matemàtica.
     * Es processen en ordre d'arribada i es retorna l'operació amb el valor calculat.
     */
    OperacionMatematica procesarOperacion();

    /**
     * Llistat d'operacions matemàtiques realitzades per un institut.
     */
    List<OperacionMatematica> getOperacionesInstituto(String idInstituto);

    /**
     * Llistat d'operacions matemàtiques realitzades per un alumne.
     */
    List<OperacionMatematica> getOperacionesAlumno(String idAlumno);

    /**
     * Llistat d'instituts ordenat (descendentment) per nombre d'operacions
     * matemàtiques realitzades pels seus alumnes.
     */
    List<Instituto> getInstitutosOrdenados();

    // Mètodes auxiliars per afegir dades abans d'operar
    void addAlumno(String idAlumno, String nombre, String idInstituto);
    void addInstituto(String idInstituto, String nombre);
}