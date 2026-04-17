import dsa.ReversePolishNotation;
import dsa.ReversePolishNotationImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReversePolishNotationTest {

    private ReversePolishNotation calculadora;

    @Before
    public void setUp() {
        calculadora = new ReversePolishNotationImpl();
    }

    @After
    public void tearDown() {
        calculadora = null;
    }

    @Test
    public void testProcesarExpresion() {
        String expresion = "5 1 2 + 4 * + 3 -";
        Double resultadoEsperado = 14.0;
        Double resultadoReal = calculadora.procesar(expresion);
        assertEquals(resultadoEsperado, resultadoReal, 0.001);
    }
}