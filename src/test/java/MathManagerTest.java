import dsa.MathManagerImpl;
import dsa.models.Instituto;
import dsa.models.OperacionMatematica;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MathManagerTest {

    private MathManagerImpl manager;

    @Before
    public void setUp() {
        manager = MathManagerImpl.getInstance();

        manager.addInstituto("INSTI-1", "Instituto Castelldefels");
        manager.addInstituto("INSTI-2", "Instituto Gava");

        manager.addAlumno("ALUM-1", "Pepe", "INSTI-1");
        manager.addAlumno("ALUM-2", "Maria", "INSTI-1");
        manager.addAlumno("ALUM-3", "Juan", "INSTI-2");
    }

    @After
    public void tearDown() {
        manager.clear();
    }

    @Test
    public void testSolicitarYProcesarOperacion() {
        manager.solicitarOperacion("5 1 2 + 4 * + 3 -", "ALUM-1", "INSTI-1");
        List<OperacionMatematica> opsAlumno = manager.getOperacionesAlumno("ALUM-1");
        assertEquals(1, opsAlumno.size());
        OperacionMatematica procesada = manager.procesarOperacion();
        assertNotNull(procesada);
        assertEquals(Double.valueOf(14.0), procesada.getResultado());
    }

    @Test
    public void testInstitutosOrdenados() {
        manager.solicitarOperacion("2 2 +", "ALUM-3", "INSTI-2");
        manager.solicitarOperacion("3 3 *", "ALUM-3", "INSTI-2");
        manager.solicitarOperacion("5 5 -", "ALUM-1", "INSTI-1");
        List<Instituto> institutos = manager.getInstitutosOrdenados();
        assertEquals("INSTI-2", institutos.get(0).getIdInstituto());
        assertEquals("INSTI-1", institutos.get(1).getIdInstituto());
    }
}