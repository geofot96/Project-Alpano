package my_tests.etape5;

import static org.junit.Assert.*;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.PanoramaParameters;

import org.junit.Test;

public class PanoramaParametersTestM {

    @Test(expected = NullPointerException.class)
    public void constructorThrowsForNullPosition() {
        new PanoramaParameters(null, 100, 0.1, 1.0, 30, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsForNonCanonicalNegativeAzimuth() {
        new PanoramaParameters(new GeoPoint(0, 0), 100, -1, 1.0, 30, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsForNonCanonicalPositiveAzimuth() {
        new PanoramaParameters(new GeoPoint(0, 0), 100, 10, 1.0, 30, 10, 10);
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsNegativeHorizontalFieldOfView() {
        new PanoramaParameters(new GeoPoint(0, 0), 100, 1, -1.0, 30, 10, 10);
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsZeroNonValidPositiveHorizontalFieldOfView() {
        new PanoramaParameters(new GeoPoint(0, 0), 100, 1, 10, 30, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsForNegativeDistance() {
        new PanoramaParameters(new GeoPoint(0, 0), 100, 1, 1.0, -30, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsForNegativeWidth() {
        new PanoramaParameters(new GeoPoint(0, 0), 100, 1, 1.0, 30, -10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsForNegativeHeight() {
        new PanoramaParameters(new GeoPoint(0, 0), 100, 1, 1.0, 30, 10, -10);
    }

    @Test
    public void azimuthForXWorksForKnownValues() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        assertEquals(2.5918, p.azimuthForX(0), 0.1);
        assertEquals(2.7803, p.azimuthForX(1000), 0.1);
        assertEquals(Math.toRadians(162), p.azimuthForX(1250), 0.1);
        assertEquals(2.9689, p.azimuthForX(2000), 0.1);
        assertEquals(3.063, p.azimuthForX(2499), 0.1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void azimuthForXThrowsForOutOfBoundValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.azimuthForX(3000);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void azimuthForXThrowsForNegativeValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.azimuthForX(-100);
    }

    @Test
    public void xForAzimuthWorksForKnownValues() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        assertEquals(1000.0, p.xForAzimuth(2.7803), 0.5);
        assertEquals(1250.0, p.xForAzimuth(Math.toRadians(162)), 0.5);
        assertEquals(2000.0, p.xForAzimuth(2.9689), 0.5);
        assertEquals(2499.0, p.xForAzimuth(3.063), 0.5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void xForAzimuthWorksThrowsForOutOfBoundValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.xForAzimuth(2500);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void xForAzimuthWorksThrowsForNegativeValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.xForAzimuth(Math.toRadians(-170));
    }

    @Test
    public void altitudeForYWorksForKnownValues() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        assertEquals(0.0753, p.altitudeForY(0), 0.1);
        assertEquals(0.0376, p.altitudeForY(200), 0.1);
        assertEquals(0.0, p.altitudeForY(400), 0.1);
        assertEquals(-0.0378, p.altitudeForY(600), 0.1);
        assertEquals(-0.0753, p.altitudeForY(799), 0.1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void altitudeForYThrowsForOutOfBoundValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.altitudeForY(1000);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void altitudeForYThrowsForNegativeValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.altitudeForY(-100);
    }

    @Test
    public void yForAltitudeWorksForKnownValues() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        assertEquals(0.0, p.yForAltitude(0.0753), 0.5);
        assertEquals(200.0, p.yForAltitude(0.0376), 0.5);
        assertEquals(400.0, p.yForAltitude(0), 0.51);
        assertEquals(600.0, p.yForAltitude(-0.0378), 0.5);
        assertEquals(799.0, p.yForAltitude(-0.0753), 0.5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void yForAltitudeThrowsForOutOfBoundValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.yForAltitude(Math.toRadians(170));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void yForAltitudeThrowsForNegativeValue() {
        PanoramaParameters p = new PanoramaParameters(
                new GeoPoint(Math.toRadians(6.8087), Math.toRadians(47.0085)),
                1380, Math.toRadians(162), Math.toRadians(27), 300000, 2500,
                800);
        p.yForAltitude(Math.toRadians(-170));
    }
}
