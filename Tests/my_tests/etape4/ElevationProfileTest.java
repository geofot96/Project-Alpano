package my_tests.etape4;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.ElevationProfile;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import org.junit.Test;

import java.io.File;

/**
 * Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class ElevationProfileTest
{

    private static ElevationProfile elePro = new ElevationProfile(new ContinuousElevationModel(new HgtDiscreteElevationModel(new File("Ressources/hgt_6-10_45-47/N46E006.hgt"))),
            new GeoPoint(0, 0), 0, 10000);

    @Test(expected = NullPointerException.class)
    public void ConstructorThrowsForNullCEM()
    {
        new ElevationProfile(null, new GeoPoint(0, 0), 0, 1000);
    }

    @Test(expected = NullPointerException.class)
    public void ConstructorThrowsForNullOrigin()
    {
        new ElevationProfile(new ContinuousElevationModel(new HgtDiscreteElevationModel(new File("Ressources/hgt_6-10_45-47/N46E006.hgt"))),
                null, 0, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConstructorThrowsForNonCanonicalAngle()
    {
        new ElevationProfile(new ContinuousElevationModel(new HgtDiscreteElevationModel(new File("Ressources/hgt_6-10_45-47/N46E006.hgt"))),
                new GeoPoint(0, 0), -30, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConstructorThrowsForNegativeLength()
    {
        new ElevationProfile(new ContinuousElevationModel(new HgtDiscreteElevationModel(new File("Ressources/hgt_6-10_45-47/N46E006.hgt"))),
                new GeoPoint(0, 0), 0, -1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConstructorThrowsForZeroLength()
    {
        new ElevationProfile(new ContinuousElevationModel(new HgtDiscreteElevationModel(new File("Ressources/hgt_6-10_45-47/N46E006.hgt"))),
                new GeoPoint(0, 0), 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void elevationAtThrowsForNegativeX()
    {
        elePro.elevationAt(-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void elevationAtThrowsForTooBigX()
    {
        elePro.elevationAt(20000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void positionAtThrowsForNegativeX()
    {
        elePro.positionAt(-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void positionAtThrowsForTooBigX()
    {
        elePro.positionAt(20000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void slopeAtThrowsForNegativeX()
    {
        elePro.slopeAt(-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void slopeAtThrowsForTooBigX()
    {
        elePro.slopeAt(20000);
    }

    @Test
    public void elevationAtDoesntThrowForCorners()
    {
        elePro.elevationAt(0);
        elePro.elevationAt(10000);
    }

    @Test
    public void positionAtDoesntThrowForCorners()
    {
        elePro.positionAt(0);
        elePro.positionAt(10000);
    }

    @Test
    public void slopeAtDoesntThrowForCorners()
    {
        elePro.slopeAt(0);
        elePro.slopeAt(10000);
    }

    @Test
    public void positionAtDoesNotThrowForNormalValues()
    {
        elePro.positionAt(342);
        elePro.positionAt(4324);
        elePro.positionAt(12);
        elePro.positionAt(9999);
    }

    @Test
    public void slopeAtDoesNotThrowForNormalValues()
    {
        elePro.slopeAt(342);
        elePro.slopeAt(4324);
        elePro.slopeAt(12);
        elePro.slopeAt(9999);
    }

    @Test
    public void elevationAtDoesNotThrowForNormalValues()
    {
        elePro.elevationAt(342);
        elePro.elevationAt(4324);
        elePro.elevationAt(12);
        elePro.elevationAt(9999);
    }
}
