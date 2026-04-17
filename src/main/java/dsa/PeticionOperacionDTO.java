public class PeticionOperacionDTO {
    private String expresion;
    private String idAlumno;
    private String idInstituto;

    // Constructor vacío obligatorio para que el servidor pueda convertir el JSON
    public PeticionOperacionDTO() {
    }

    public PeticionOperacionDTO(String expresion, String idAlumno, String idInstituto) {
        this.expresion = expresion;
        this.idAlumno = idAlumno;
        this.idInstituto = idInstituto;
    }

    public String getExpresion() { return expresion; }
    public void setExpresion(String expresion) { this.expresion = expresion; }

    public String getIdAlumno() { return idAlumno; }
    public void setIdAlumno(String idAlumno) { this.idAlumno = idAlumno; }

    public String getIdInstituto() { return idInstituto; }
    public void setIdInstituto(String idInstituto) { this.idInstituto = idInstituto; }
}