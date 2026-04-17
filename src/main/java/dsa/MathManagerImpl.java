import models.Alumno;
import models.Instituto;
import models.OperacionMatematica;
import org.apache.log4j.Logger; // Importante para las trazas que pide el examen

import java.util.*;

public class MathManagerImpl implements MathManager {

    // 1. Inicializamos el Logger (Log4j)
    final static Logger log = Logger.getLogger(MathManagerImpl.class);

    // 2. Patrón Singleton: Instancia única privada
    private static MathManagerImpl instance;

    // 3. Estructuras de datos
    private Queue<OperacionMatematica> colaOperaciones; // Cola para el orden de llegada
    private HashMap<String, Alumno> alumnos; // Diccionario para buscar alumnos rápido
    private HashMap<String, Instituto> institutos; // Diccionario para buscar institutos rápido
    private ReversePolishNotation calculadora; // Nuestra calculadora de la Parte 1

    // 4. Patrón Singleton: Constructor privado (nadie puede hacer 'new' desde fuera)
    private MathManagerImpl() {
        this.colaOperaciones = new LinkedList<>();
        this.alumnos = new HashMap<>();
        this.institutos = new HashMap<>();
        this.calculadora = new ReversePolishNotationImpl();
    }

    // 5. Patrón Singleton: Método para obtener la única instancia
    public static MathManagerImpl getInstance() {
        if (instance == null) {
            instance = new MathManagerImpl();
        }
        return instance;
    }

    // --- MÉTODOS AUXILIARES ---

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

        log.info("Fin addInstituto");
    }

    @Override
    public void addAlumno(String idAlumno, String nombre, String idInstituto) {
        log.info("Iniciando addAlumno - Parametros: idAlumno=" + idAlumno + ", nombre=" + nombre + ", idInstituto=" + idInstituto);

        Instituto insti = institutos.get(idInstituto);
        if (insti != null) {
            Alumno nuevoAlumno = new Alumno(idAlumno, nombre, idInstituto);
            alumnos.put(idAlumno, nuevoAlumno);
            insti.addAlumnoId(idAlumno); // Vinculamos el alumno al instituto
            log.info("Alumno añadido correctamente al instituto " + idInstituto);
        } else {
            log.fatal("Fatal: No se puede añadir el alumno porque el instituto " + idInstituto + " no existe.");
        }

        log.info("Fin addAlumno");
    }

    // --- MÉTODOS DEL EXAMEN ---

    @Override
    public void solicitarOperacion(String expresion, String idAlumno, String idInstituto) {
        log.info("Iniciando solicitarOperacion - Parametros: expresion=[" + expresion + "], idAlumno=" + idAlumno + ", idInstituto=" + idInstituto);

        Alumno alumno = alumnos.get(idAlumno);
        if (alumno != null && alumno.getIdInstituto().equals(idInstituto)) {
            // Creamos la operación (usamos un ID aleatorio o basado en tiempo)
            String idOp = UUID.randomUUID().toString();
            OperacionMatematica op = new OperacionMatematica(idOp, expresion, idAlumno);

            // La guardamos en el historial del alumno
            alumno.addOperacion(op);
            // La metemos en la cola para procesarla luego en orden de llegada
            colaOperaciones.add(op);

            log.info("Operación añadida a la cola correctamente.");
        } else {
            log.error("Error: El alumno no existe o no pertenece a ese instituto.");
        }

        log.info("Fin solicitarOperacion");
    }

    @Override
    public OperacionMatematica procesarOperacion() {
        log.info("Iniciando procesarOperacion");

        // Sacamos la primera operación que llegó a la cola
        OperacionMatematica op = colaOperaciones.poll();

        if (op != null) {
            try {
                // Usamos la calculadora para procesar el texto
                Double resultado = calculadora.procesar(op.getExpresion());
                op.setResultado(resultado); // Se marca como procesada automáticamente por el setter
                log.info("Operación procesada con éxito. Resultado: " + resultado);
            } catch (Exception e) {
                log.error("Error al procesar la expresión matemática: " + op.getExpresion(), e);
            }
        } else {
            log.info("No hay operaciones pendientes en la cola.");
        }

        log.info("Fin procesarOperacion - Retorna: " + (op != null ? op.getIdOperacion() : "null"));
        return op;
    }

    @Override
    public List<OperacionMatematica> getOperacionesInstituto(String idInstituto) {
        log.info("Iniciando getOperacionesInstituto - Parametros: idInstituto=" + idInstituto);
        List<OperacionMatematica> resultado = new LinkedList<>();

        Instituto insti = institutos.get(idInstituto);
        if (insti != null) {
            // Recorremos los IDs de los alumnos de ese instituto
            for (String idAlum : insti.getAlumnosIds()) {
                Alumno alumno = alumnos.get(idAlum);
                if (alumno != null) {
                    resultado.addAll(alumno.getOperacionesRealizadas());
                }
            }
        } else {
            log.error("Error: El instituto no existe.");
        }

        log.info("Fin getOperacionesInstituto - Retorna una lista de tamaño: " + resultado.size());
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

        log.info("Fin getOperacionesAlumno - Retorna una lista de tamaño: " + resultado.size());
        return resultado;
    }

    @Override
    public List<Instituto> getInstitutosOrdenados() {
        log.info("Iniciando getInstitutosOrdenados");

        // Pasamos los institutos a una lista para poder ordenarlos
        List<Instituto> listaInstitutos = new LinkedList<>(institutos.values());

        // Los ordenamos de forma DESCENDENTE por el número de operaciones de sus alumnos
        listaInstitutos.sort((insti1, insti2) -> {
            int opsInsti1 = getOperacionesInstituto(insti1.getIdInstituto()).size();
            int opsInsti2 = getOperacionesInstituto(insti2.getIdInstituto()).size();
            // Para orden descendente comparamos el 2 contra el 1
            return Integer.compare(opsInsti2, opsInsti1);
        });

        log.info("Fin getInstitutosOrdenados");
        return listaInstitutos;
    }

    // Método extra útil para los test JUNIT: Sirve para limpiar los datos entre test y test
    public void clear() {
        this.colaOperaciones.clear();
        this.alumnos.clear();
        this.institutos.clear();
    }
}