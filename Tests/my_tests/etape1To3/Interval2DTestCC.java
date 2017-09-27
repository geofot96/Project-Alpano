package my_tests.etape1To3;


import ch.epfl.alpano.Interval1D;
import ch.epfl.alpano.Interval2D;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author Clement Charollais
 */
public class Interval2DTestCC {

    private Interval2D int0_0_0_1= new Interval2D(Interval1DTestCC.int0_0,
            Interval1DTestCC.int0_1);
    private Interval2D intm1_0_0_1= new Interval2D(Interval1DTestCC.intm1_0,
            Interval1DTestCC.int0_1);
    private Interval2D intm2_m1_0_1= new Interval2D(Interval1DTestCC.intm2_m1,
            Interval1DTestCC.int0_1);
    private Interval2D intm2_m1_m1_0= new Interval2D(Interval1DTestCC.intm2_m1,
            Interval1DTestCC.intm1_0);
    private Interval2D intm2_1_m1_0= new Interval2D(Interval1DTestCC.intm2_1,
            Interval1DTestCC.intm1_0);
    private Interval2D intm20_12_m5_70= new Interval2D(Interval1DTestCC.intm20_12,
            Interval1DTestCC.intm5_70);
    private Interval2D intm20_12_30_100= new Interval2D(Interval1DTestCC.intm20_12,
            Interval1DTestCC.int30_100);
    private Interval2D intm5_70_30_100= new Interval2D(Interval1DTestCC.intm5_70,
            Interval1DTestCC.int30_100);
    private Interval2D int30_100_m20_12 = new Interval2D(Interval1DTestCC.int30_100,
            Interval1DTestCC.intm20_12);
    private Interval2D int30_100_30_100= new Interval2D(Interval1DTestCC.int30_100,
            Interval1DTestCC.int30_100);

    @Test(expected = NullPointerException.class)
    public void constructorThrows(){
        new Interval2D(null, Interval1DTestCC.int0_0);
    }

    @Test
    public void iXWorks(){
        assertEquals(Interval1DTestCC.int0_0,int0_0_0_1.iX());
        Assert.assertEquals(new Interval1D(0,0),int0_0_0_1.iX());
        assertEquals(Interval1DTestCC.intm1_0,intm1_0_0_1.iX());
        assertEquals(new Interval1D(-1,0),intm1_0_0_1.iX());
        assertEquals(Interval1DTestCC.intm2_m1,intm2_m1_0_1.iX());
        assertEquals(new Interval1D(-2,-1),intm2_m1_0_1.iX());
        assertEquals(Interval1DTestCC.intm2_m1,intm2_m1_m1_0.iX());
        assertEquals(new Interval1D(-2,-1),intm2_m1_m1_0.iX());
    }

    @Test
    public void iYWorks(){
        assertEquals(Interval1DTestCC.int0_1,int0_0_0_1.iY());
        assertEquals(new Interval1D(0,1),int0_0_0_1.iY());
        assertEquals(Interval1DTestCC.intm1_0,intm2_m1_m1_0.iY());
        assertEquals(new Interval1D(-1,0),intm2_m1_m1_0.iY());
        assertEquals(Interval1DTestCC.intm5_70,intm20_12_m5_70.iY());
        assertEquals(new Interval1D(-5,70),intm20_12_m5_70.iY());
    }

    @Test
    public void contains(){
        assertEquals(true, int30_100_30_100.contains(30,30));
        assertEquals(true, int30_100_30_100.contains(30,100));
        assertEquals(false,int30_100_30_100.contains(10,100));
        assertEquals(false,int30_100_30_100.contains(10,10));
        assertEquals(false,int30_100_30_100.contains(10,10));
    }

    @Test
    public void sizeWorks(){
        assertEquals(2508,intm20_12_m5_70.size(),0);
        assertEquals(2343,intm20_12_30_100.size(),0);
        assertEquals(5396,intm5_70_30_100.size(),0);
        assertEquals(2,int0_0_0_1.size(),0);
    }

    @Test(expected = NullPointerException.class)
    public void sizeOfIntersectThrows(){
        int0_0_0_1.sizeOfIntersectionWith(null);
    }

    @Test
    public void sizeOfIntersectWorks(){
        assertEquals(0,intm20_12_30_100.sizeOfIntersectionWith(int30_100_m20_12),0);
        assertEquals(5041,int30_100_30_100.sizeOfIntersectionWith(int30_100_30_100),0);
        assertEquals(738,intm20_12_m5_70.sizeOfIntersectionWith(intm5_70_30_100));
    }

    @Test(expected = NullPointerException.class)
    public void boundingUnionThrows(){
        int0_0_0_1.boundingUnion(null);
    }

    @Test
    public void boundingUnionWorks(){
    }

    @Test(expected = NullPointerException.class)
    public void isUnionableWithThrows(){
        int0_0_0_1.isUnionableWith(null);
    }

    @Test
    public void isUnionableWithWorks(){

    }

    @Test(expected = NullPointerException.class)
    public void unionThrows(){
        int0_0_0_1.union(null);
    }

    @Test
    public void unionWorks(){

    }

    @Test
    public void equalsWorks(){

    }

    @Test
    public void toStringWorks(){

    }
}
