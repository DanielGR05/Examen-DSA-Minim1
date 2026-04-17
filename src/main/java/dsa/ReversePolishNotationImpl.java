package dsa;

import java.util.Stack;

public class ReversePolishNotationImpl implements ReversePolishNotation {

    @Override
    public Double procesar(String expresion) {
        Stack<Double> pila = new Stack<>();
        String[] elementos = expresion.split(" ");

        for (String elemento : elementos) {
            if (elemento.equals("+")) {
                pila.push(pila.pop() + pila.pop());
            }
            else if (elemento.equals("*")) {
                pila.push(pila.pop() * pila.pop());
            }
            else if (elemento.equals("-")) {
                Double sustraendo = pila.pop();
                Double minuendo = pila.pop();
                pila.push(minuendo - sustraendo);
            }
            else if (elemento.equals("/")) {
                Double divisor = pila.pop();
                Double dividendo = pila.pop();
                pila.push(dividendo / divisor);
            }
            else {
                pila.push(Double.parseDouble(elemento));
            }
        }

        return pila.pop();
    }
}