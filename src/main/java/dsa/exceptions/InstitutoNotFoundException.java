package dsa.exceptions;

public class InstitutoNotFoundException extends Exception {
    public InstitutoNotFoundException() {
        super("El instituto solicitado no existe");
    }
}