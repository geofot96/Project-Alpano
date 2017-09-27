package my_tests.etape5;
import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.summit.Summit;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static java.lang.Math.toRadians;

/**
 * Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class SummitTests
{
    @Test
    public void TestCorrectName()
    {
        Summit actual = new Summit("EIGER", new GeoPoint(toRadians(8.0053), toRadians(46.5775)), 3970);
        assertEquals("EIGER (8.0053,46.5775) 3970", actual.toString());
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsForNullName(){
        new Summit(null,new GeoPoint(2,1),12);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsForNullPosition(){
        new Summit("wd",null,12);
    }
}
