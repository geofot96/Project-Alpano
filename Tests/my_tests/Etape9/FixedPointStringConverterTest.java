package my_tests.Etape9;

import ch.epfl.alpano.gui.FixedPointStringConverter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * TODO Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class FixedPointStringConverterTest
{
    @Test
    public void ToAndFromStringWork()
    {
        FixedPointStringConverter c = new FixedPointStringConverter(1);

        assertEquals(120, c.fromString("12"), 0);
        assertEquals(123, c.fromString("12.3"), 0);
        assertEquals(123, c.fromString("12.34"), 0);
        assertEquals(124, c.fromString("12.35"), 0);
        assertEquals(124, c.fromString("12.36789"), 0);


        System.out.println("67.8".equals(c.toString(678)));
    }
}
