package my_tests.etape1To3;

import ch.epfl.alpano.dem.ContinuousElevationModel;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class ContinuousDemTestGF
{
    @Test (expected = NullPointerException.class)
    public void ContinuousElevationModelThrowsException()
    {
        new ContinuousElevationModel(null);
    }
}
