package models;

public class OperacionMatematica {

    private String idOperacion;
    private String expresion; // La expresión en notación polaca inversa
    private Double resultado; // Usamos Double (con mayúscula) para permitir que sea null antes de calcularse
    private String idAlumno; // El ID del alumno que solicitó la operación
    private boolean procesada; // Para saber si ya pasó por la cola de "orden de llegada"

    // Constructor vacío (necesario para el servicio REST)
    public OperacionMatematica() {
        this.procesada = false;
    }

    // Constructor con parámetros para cuando el alumno solicita la operación
    public OperacionMatematica(String idOperacion, String expresion, String idAlumno) {
        this.idOperacion = idOperacion;
        this.expresion = expresion;
        this.idAlumno = idAlumno;
        this.resultado = null; // Aún no se ha calculado
        this.procesada = false;
    }

    // Getters y Setters
    public String getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(String idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public Double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
        this.procesada = true; // Si le asignamos un resultado, asumimos que ya se procesó
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public boolean isProcesada() {
        return procesada;
    }

    public void setProcesada(boolean procesada) {
        this.procesada = procesada;
    }
}