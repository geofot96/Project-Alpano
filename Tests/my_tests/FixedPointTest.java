package my_tests;

import ch.epfl.alpano.gui.FixedPointStringConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO add javadoc
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class FixedPointTest {
    private final static FixedPointStringConverter zeroPlace = new FixedPointStringConverter(0);
    private final static FixedPointStringConverter onePlace = new FixedPointStringConverter(1);
    private final static FixedPointStringConverter twoPlaces = new FixedPointStringConverter(2);
    private final static FixedPointStringConverter threePlaces = new FixedPointStringConverter(3);
    private final static String empty = "";
    private final static String zero = "1";
    private final static String one = "1.0";
    private final static String two = "1.00";
    private final static String three = "1.000";
    private final static String mzero = "-1";
    private final static String mone = "-1.0";
    private final static String mtwo = "-1.00";
    private final static String mthree = "-1.000";
    private final static Integer One = 1;
    private final static Integer mOne = -1;





    @Test
    public void FPToStr()
    {
        assertEquals(zeroPlace.fromString(three),One);
        assertEquals(zeroPlace.fromString(two),One);
        assertEquals(onePlace.fromString(zero), new Integer(10));
        assertEquals(twoPlaces.fromString(mtwo), new Integer(-100));
        assertEquals(threePlaces.fromString(empty),new Integer(0));
    }
}
