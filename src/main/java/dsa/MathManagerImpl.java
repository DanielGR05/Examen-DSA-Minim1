package dsa;

import dsa.exceptions.InstitutoNotFoundException;
import dsa.models.Alumno;
import dsa.models.Instituto;
import dsa.models.OperacionMatematica;
import org.apache.log4j.Logger;

import java.util.*;

public class MathManagerImpl implements MathManager {

    final static Logger log = Logger.getLogger(MathManagerImpl.class);

    private static MathManagerImpl instance;

    private Queue<OperacionMatematica> colaOperaciones;
    private HashMap<String, Alumno> alumnos;
    private HashMap<String, Instituto> institutos;
    private ReversePolishNotation calculadora;

    private MathManagerImpl() {
        this.colaOperaciones = new LinkedList<>();
        this.alumnos = new HashMap<>();
        this.institutos = new HashMap<>();
        this.calculadora = new ReversePolishNotationImpl();
    }

    public static MathManagerImpl getInstance() {
        if (instance == null) {
            instance = new MathManagerImpl();
        }
        return instance;
    }

    @Override
    public void addInstituto(String idInstituto, String nombre) {
        log.info("Iniciando addInstituto - Parametros: idInstituto=" + idInstituto + ", nombre=" + nombre);

        if (!institutos.containsKey(idInstituto)) {
            Instituto nuevoInsti = new Instituto(idInstituto, nombre);
            institutos.put(idInstituto, nuevoInsti);
            log.info("Instituto añadido correctamente.");
        } else {
            log.error("Error: El instituto con ID " + idInstituto + " ya existe.");
        }
    }

    @Override
    public void addAlumno(String idAlumno, String nombre, String idInstituto) {
        log.info("Iniciando addAlumno - Parametros: idAlumno=" + idAlumno + ", nombre=" + nombre + ", idInstituto=" + idInstituto);

        Instituto insti = institutos.get(idInstituto);
        if (insti != null) {
            Alumno nuevoAlumno = new Alumno(idAlumno, nombre, idInstituto);
            alumnos.put(idAlumno, nuevoAlumno);
            insti.addAlumnoId(idAlumno);
            log.info("Alumno añadido correctamente al instituto " + idInstituto);
        } else {
            log.fatal("Fatal: No se puede añadir el alumno porque el instituto " + idInstituto + " no existe.");
        }
    }

    @Override
    public void solicitarOperacion(String expresion, String idAlumno, String idInstituto) {
        log.info("Iniciando solicitarOperacion - Parametros: expresion=[" + expresion + "], idAlumno=" + idAlumno + ", idInstituto=" + idInstituto);

        Alumno alumno = alumnos.get(idAlumno);
        if (alumno != null && alumno.getIdInstituto().equals(idInstituto)) {
            String idOp = UUID.randomUUID().toString();
            OperacionMatematica op = new OperacionMatematica(idOp, expresion, idAlumno);
            alumno.addOperacion(op);
            colaOperaciones.add(op);

            log.info("Operación añadida a la cola correctamente.");
        } else {
            log.error("Error: El alumno no existe o no pertenece a ese instituto.");
        }
    }

    @Override
    public OperacionMatematica procesarOperacion() {
        log.info("Iniciando procesarOperacion");
        OperacionMatematica op = colaOperaciones.poll();

        if (op != null) {
            try {
                Double resultado = calculadora.procesar(op.getExpresion());
                op.setResultado(resultado);
                log.info("Operación procesada con éxito. Resultado: " + resultado);
            } catch (Exception e) {
                log.error("Error al procesar la expresión matemática: " + op.getExpresion(), e);
            }
        } else {
            log.info("No hay operaciones pendientes en la cola.");
        }
        return op;
    }

    @Override
    public List<OperacionMatematica> getOperacionesInstituto(String idInstituto) throws InstitutoNotFoundException {
        log.info("Iniciando getOperacionesInstituto - Parametros: idInstituto=" + idInstituto);

        Instituto insti = institutos.get(idInstituto);
        if (insti == null) {
            log.error("Error: El instituto con ID " + idInstituto + " no existe.");
            throw new InstitutoNotFoundException();
        }
        List<OperacionMatematica> resultado = new LinkedList<>();
        for (String idAlum : insti.getAlumnosIds()) {
            Alumno alumno = alumnos.get(idAlum);
            if (alumno != null) {
                resultado.addAll(alumno.getOperacionesRealizadas());
            }
        }
        return resultado;
    }

    @Override
    public List<OperacionMatematica> getOperacionesAlumno(String idAlumno) {
        log.info("Iniciando getOperacionesAlumno - Parametros: idAlumno=" + idAlumno);

        Alumno alumno = alumnos.get(idAlumno);
        List<OperacionMatematica> resultado;

        if (alumno != null) {
            resultado = alumno.getOperacionesRealizadas();
        } else {
            log.error("Error: El alumno no existe.");
            resultado = new LinkedList<>();
        }

        return resultado;
    }

    @Override
    public List<Instituto> getInstitutosOrdenados() {
        log.info("Iniciando getInstitutosOrdenados");
        List<Instituto> listaInstitutos = new LinkedList<>(institutos.values());

        listaInstitutos.sort((Instituto insti1, Instituto insti2) -> {
            try {
                int opsInsti1 = getOperacionesInstituto(insti1.getIdInstituto()).size();
                int opsInsti2 = getOperacionesInstituto(insti2.getIdInstituto()).size();
                return Integer.compare(opsInsti2, opsInsti1);
            } catch (InstitutoNotFoundException e) {
                return 0;
            }
        });
        return listaInstitutos;
    }

    public void clear() {
        this.colaOperaciones.clear();
        this.alumnos.clear();
        this.institutos.clear();
    }
}