package my_tests.etape4;

import ch.epfl.alpano.Interval1D;
import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import org.junit.Test;

import java.io.File;

import static ch.epfl.alpano.dem.DiscreteElevationModel.SAMPLES_PER_DEGREE;
import static org.junit.Assert.assertEquals;

/**
 * Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class HgtDiscreteElevationModelTest {

    private static HgtDiscreteElevationModel eleMod = new HgtDiscreteElevationModel(new File("Ressources/hgt_6-10_45-47/N46E006.hgt"));

    @Test(expected = NullPointerException.class)
    public void constructorThrowsForNullFile(){
        new HgtDiscreteElevationModel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWrongSizeFile(){
        new HgtDiscreteElevationModel(new File("S88W055.hgt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWrongNameFile(){
        new HgtDiscreteElevationModel(new File("ddd"));
    }

    @Test
    public void elevationSampleDoesNotThrowForLimitValues(){
        eleMod.elevationSample(6*SAMPLES_PER_DEGREE,46*SAMPLES_PER_DEGREE);
        eleMod.elevationSample(7*SAMPLES_PER_DEGREE,46*SAMPLES_PER_DEGREE);
        eleMod.elevationSample(6*SAMPLES_PER_DEGREE,47*SAMPLES_PER_DEGREE);
        eleMod.elevationSample(7*SAMPLES_PER_DEGREE,47*SAMPLES_PER_DEGREE);
    }

    @Test
    public void elevationSampleDoesNotThrowForValidInput(){
        eleMod.elevationSample(6*SAMPLES_PER_DEGREE+100,46*SAMPLES_PER_DEGREE+20);
        eleMod.elevationSample(6*SAMPLES_PER_DEGREE+11,46*SAMPLES_PER_DEGREE+749);
    }

    @Test(expected = IllegalArgumentException.class)
    public void elevationSampleThrowsForTooBigX(){
        eleMod.elevationSample(Integer.MAX_VALUE,46*SAMPLES_PER_DEGREE+100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void elevationSampleThrowsForTooSmallX(){
        eleMod.elevationSample(Integer.MIN_VALUE,46*SAMPLES_PER_DEGREE+100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void elevationSampleThrowsForTooBigY(){
        eleMod.elevationSample(6*SAMPLES_PER_DEGREE +27,Integer.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void elevationSampleThrowsForTooSmallY(){
        eleMod.elevationSample(6*SAMPLES_PER_DEGREE+100,Integer.MIN_VALUE);
    }

    @Test
    public void extentWorks(){
        assertEquals(new Interval2D(new Interval1D(6*SAMPLES_PER_DEGREE,7*SAMPLES_PER_DEGREE),new Interval1D(46*SAMPLES_PER_DEGREE,47*SAMPLES_PER_DEGREE)),eleMod.extent());
    }

}

