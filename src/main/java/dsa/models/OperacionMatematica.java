package dsa.models;

public class OperacionMatematica {

    private String idOperacion;
    private String expresion;
    private Double resultado;
    private String idAlumno;
    private boolean procesada;

    public OperacionMatematica() {
        this.procesada = false;
    }

    public OperacionMatematica(String idOperacion, String expresion, String idAlumno) {
        this.idOperacion = idOperacion;
        this.expresion = expresion;
        this.idAlumno = idAlumno;
        this.resultado = null;
        this.procesada = false;
    }

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
        this.procesada = true;
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