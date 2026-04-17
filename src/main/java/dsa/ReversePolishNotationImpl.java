import models.Alumno;
import models.Instituto;
import models.OperacionMatematica;

import java.util.*;
import java.util.Stack; // Importamos la estructura de datos Pila

public class ReversePolishNotationImpl implements ReversePolishNotation {

    @Override
    public Double procesar(String expresion) {
        // Usamos una Pila (Stack) para ir guardando los números
        Stack<Double> pila = new Stack<>();

        // Separamos la expresión por espacios ("5 1 2 +" -> ["5", "1", "2", "+"])
        String[] elementos = expresion.split(" ");

        for (String elemento : elementos) {
            // Si el elemento es un operador, sacamos los dos últimos números de la pila y operamos
            if (elemento.equals("+")) {
                pila.push(pila.pop() + pila.pop());
            }
            else if (elemento.equals("*")) {
                pila.push(pila.pop() * pila.pop());
            }
            else if (elemento.equals("-")) {
                // En la resta el orden importa: el primero que sale es el que resta
                Double sustraendo = pila.pop();
                Double minuendo = pila.pop();
                pila.push(minuendo - sustraendo);
            }
            else if (elemento.equals("/")) {
                // En la división el orden también importa
                Double divisor = pila.pop();
                Double dividendo = pila.pop();
                pila.push(dividendo / divisor);
            }
            else {
                // Si no es un operador, asumimos que es un número.
                // Lo convertimos de texto a Double y lo metemos en la pila.
                pila.push(Double.parseDouble(elemento));
            }
        }

        // Al final, el único número que quede en la pila es el resultado final
        return pila.pop();
    }
}