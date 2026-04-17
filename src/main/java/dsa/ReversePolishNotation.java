import models.Alumno;
import models.Instituto;
import models.OperacionMatematica;
import java.util.List;

public interface ReversePolishNotation {

    /**
     * Procesa una expresión matemática en notación polaca inversa.
     * @param expresion La cadena de texto con la expresión (ej. "5 1 2 + 4 * + 3 -")
     * @return El resultado numérico de la operación
     */
    Double procesar(String expresion);

}