package my_tests.etape1To3;


import static ch.epfl.alpano.Math2.PI2;
import static java.lang.Math.PI;

import ch.epfl.alpano.GeoPoint;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author Clement Charollais
 */
public class GeoPointTestCC {
    private final GeoPoint moscow = new GeoPoint(Math.toRadians(37.623),Math.toRadians(55.753)); //Lausanne
    private final GeoPoint lausanne = new GeoPoint(Math.toRadians(6.631),Math.toRadians(46.521)); //Moscow
    private final GeoPoint northPole = new GeoPoint(0, PI/2);
    private final GeoPoint southPole = new GeoPoint(0, -PI/2);
    private final GeoPoint toronto = new GeoPoint(Math.toRadians(-79.381667),Math.toRadians(43.6525));


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsErrorsForBadInput(){
        new GeoPoint(-10,0);
        new GeoPoint(0,-10);
        new GeoPoint(-10,-10);
    }
    @Test
    public void getterWorks(){
        assertEquals(Math.toRadians(37.623),moscow.longitude(),0);
        assertEquals(Math.toRadians(6.631),lausanne.longitude(),0);
        assertEquals(Math.toRadians(0),northPole.longitude(),0);
        assertEquals(Math.toRadians(0),southPole.longitude(),0);
        assertEquals(Math.toRadians(55.753),moscow.latitude(),0);
        assertEquals(Math.toRadians(46.521),lausanne.latitude(),0);
        assertEquals(PI/2,northPole.latitude(),0);
        assertEquals(-PI/2,southPole.latitude(),0);
        assertEquals(Math.toRadians(43.6525),toronto.latitude(),0);

    }
    @Test
    public void distanceToWorksForKnownValues(){
        assertEquals(0,moscow.distanceTo(moscow),1000);
        assertEquals(0,lausanne.distanceTo(lausanne),1000);
        assertEquals(0,northPole.distanceTo(northPole),1000);
        assertEquals(0,southPole.distanceTo(southPole),1000);
        assertEquals(0,toronto.distanceTo(toronto),1000);
        assertEquals(2367148,moscow.distanceTo(lausanne),1000);
        assertEquals(3808093,moscow.distanceTo(northPole),1000);
        assertEquals(16206994,moscow.distanceTo(southPole),1000);
        assertEquals(7484735,moscow.distanceTo(toronto),1000);
        assertEquals(2367148,lausanne.distanceTo(moscow),1000);
        assertEquals(4834644,lausanne.distanceTo(northPole),1000);
        assertEquals(15180443,lausanne.distanceTo(southPole),1000);
        assertEquals(6407243,lausanne.distanceTo(toronto),1000);
        assertEquals(3808093,northPole.distanceTo(moscow),1000);
        assertEquals(4834644,northPole.distanceTo(lausanne),1000);
        assertEquals(20015087,northPole.distanceTo(southPole),1000);
        assertEquals(5153607,northPole.distanceTo(toronto),1000);
        assertEquals(16206994,southPole.distanceTo(moscow),1000);
        assertEquals(15180443,southPole.distanceTo(lausanne),1000);
        assertEquals(20015087,southPole.distanceTo(northPole),1000);
        assertEquals(14861480,southPole.distanceTo(toronto),1000);
    }

    @Test
    public void distanceToIsSymmetric(){
        assertEquals(lausanne.distanceTo(moscow),moscow.distanceTo(lausanne),1);
        assertEquals(lausanne.distanceTo(northPole),northPole.distanceTo(lausanne),1);
        assertEquals(lausanne.distanceTo(southPole),southPole.distanceTo(lausanne),1);
        assertEquals(lausanne.distanceTo(toronto),toronto.distanceTo(lausanne),1);
        assertEquals(moscow.distanceTo(northPole),northPole.distanceTo(moscow),1);
        assertEquals(moscow.distanceTo(southPole),southPole.distanceTo(moscow),1);
        assertEquals(moscow.distanceTo(toronto),toronto.distanceTo(moscow),1);
        assertEquals(northPole.distanceTo(southPole),southPole.distanceTo(northPole),1);
        assertEquals(northPole.distanceTo(toronto),toronto.distanceTo(northPole),1);
        assertEquals(southPole.distanceTo(toronto),toronto.distanceTo(southPole),1);
    }

    @Test
    public void distanceToWorksForLimitValues(){
        assertEquals(0, toronto.distanceTo(toronto),1E-4);
        assertEquals(0, northPole.distanceTo(northPole),1E-4);
        assertEquals(0, lausanne.distanceTo(lausanne),1E-4);
    }

    @Test(expected = NullPointerException.class)
    public void distanceToThrowsExceptionForBadInput(){
        toronto.distanceTo(null);
    }

    @Test
    public void azimuthToWorksForKnownValues(){
        assertEquals(0.924216,lausanne.azimuthTo(moscow),1E-4);
        assertEquals(PI2,lausanne.azimuthTo(northPole),1E-4);
        assertEquals(PI,lausanne.azimuthTo(southPole),1E-4);
        assertEquals(-1.02489+PI2,lausanne.azimuthTo(toronto),1E-4);
        //assertEquals(1.35078,moscow.azimuthTo(lausanne),1E-4);
        assertEquals(PI2,moscow.azimuthTo(northPole),1E-4);
        assertEquals(-0.773607+PI2,moscow.azimuthTo(toronto),1E-4);
    }

    @Test
    public void azimuthToWorksForLimitValues(){
        assertEquals(PI,northPole.azimuthTo(southPole),1E-4);
        assertEquals(0,southPole.azimuthTo(northPole),1E-4);
    }
    @Test(expected = NullPointerException.class)
    public void azimuthToThrowsExceptionForNull(){
        toronto.azimuthTo(null);
    }

    @Test
    public void toStringWorksForKnownValues(){
        assertEquals("(6.6310,46.5210)", lausanne.toString());
        assertEquals("(37.6230,55.7530)", moscow.toString());
        assertEquals("(0.0000,90.0000)", northPole.toString());
        assertEquals("(0.0000,-90.0000)", southPole.toString());
        assertEquals("(-79.3817,43.6525)", toronto.toString());
    }

}
