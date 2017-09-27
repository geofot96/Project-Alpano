package my_tests.etape1To3;


import ch.epfl.alpano.Interval1D;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class Interval1DTestCC {


    static Interval1D int0_0 = new Interval1D(0,0);
    static Interval1D int0_1 = new Interval1D(0,1);
    static Interval1D intm1_0 = new Interval1D(-1,0);
    static Interval1D intm2_m1 = new Interval1D(-2,-1);
    static Interval1D intm2_1 = new Interval1D(-2,1);
    static Interval1D intm20_12 = new Interval1D(-20,12);
    static Interval1D intm5_70 = new Interval1D(-5,70);
    static Interval1D int30_100 = new Interval1D(30,100);


    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsException(){
        new Interval1D(0,-1);
    }

    @Test
    public void includedFromWorks(){
        assertEquals(0,int0_0.includedFrom(),0);
        assertEquals(0, int0_1.includedFrom(),0);
        assertEquals(-1,intm1_0.includedFrom(),0);
        assertEquals(-2, intm2_m1.includedFrom(),0);
        assertEquals(-2, intm2_1.includedFrom(),0);
    }

    @Test
    public void includedToWorks(){
        assertEquals(0,int0_0.includedTo(),0);
        assertEquals(1, int0_1.includedTo(),0);
        assertEquals(0,intm1_0.includedTo(),0);
        assertEquals(-1, intm2_m1.includedTo(),0);
        assertEquals(1, intm2_1.includedTo(),0);
    }

    @Test
    public void containsWorks(){
        assertEquals(true, int0_0.contains(0));
        assertEquals(true, int0_1.contains(0));
        assertEquals(true, int0_1.contains(1));
        assertEquals(true, intm1_0.contains(-1));
        assertEquals(true, intm1_0.contains(0));
        assertEquals(true, intm2_m1.contains(-2));
        assertEquals(true, intm2_m1.contains(-1));
        assertEquals(true, intm2_1.contains(-2));
        assertEquals(true, intm2_1.contains(-1));
        assertEquals(true, intm2_1.contains(0));
        assertEquals(true, intm2_1.contains(1));
        assertEquals(false,int0_0.contains(-4));
        assertEquals(false,int0_0.contains(12));
        assertEquals(false, intm2_1.contains(-4));
    }

    @Test
    public void sizeWorks(){
        assertEquals(1,int0_0.size(),0);
        assertEquals(2, int0_1.size(),0);
        assertEquals(2,intm1_0.size(),0);
        assertEquals(2, intm2_m1.size(),0);
        assertEquals(4, intm2_1.size(),0);
        assertEquals(71, int30_100.size(),0);
    }

    @Test(expected = NullPointerException.class)
    public void sizeOfIntersectionThrows(){
        int0_0.sizeOfIntersectionWith(null);
    }

    @Test
    public void sizeOfIntersectionWorks(){
        assertEquals(18, intm20_12.sizeOfIntersectionWith(intm5_70),0);
        assertEquals(0, intm20_12.sizeOfIntersectionWith(int30_100),0);
        assertEquals(41, intm5_70.sizeOfIntersectionWith(int30_100),0);
        assertEquals(1, intm2_m1.sizeOfIntersectionWith(intm1_0),0);
        assertEquals(int30_100.size(), int30_100.sizeOfIntersectionWith(int30_100));
    }

    @Test
    public void sizeOfIntersectionSymmetric(){
        assertEquals(int0_0.sizeOfIntersectionWith(intm5_70),
                intm5_70.sizeOfIntersectionWith(int0_0),0);
        assertEquals(intm5_70.sizeOfIntersectionWith(intm20_12),
                intm20_12.sizeOfIntersectionWith(intm5_70),0);
        assertEquals(int30_100.sizeOfIntersectionWith(intm5_70),
                intm5_70.sizeOfIntersectionWith(int30_100),0);

    }

    @Test(expected = NullPointerException.class)
    public void boundingUnionThrows(){
        int0_0.boundingUnion(null);
    }

    @Test
    public void boundingUnionWorks(){
        assertEquals(new Interval1D(-20,70),
                intm20_12.boundingUnion(intm5_70));
        assertEquals(new Interval1D(-5,100),
                intm5_70.boundingUnion(int30_100));
        assertEquals(new Interval1D(-20,100),
                intm20_12.boundingUnion(int30_100));
    }

    @Test
    public void boundingUnionSymmetric(){
        assertEquals(int30_100.boundingUnion(intm20_12),
                intm20_12.boundingUnion(int30_100));
        assertEquals(intm5_70.boundingUnion(intm20_12),
                intm20_12.boundingUnion(intm5_70));
        assertEquals(int30_100.boundingUnion(intm5_70),
                intm5_70.boundingUnion(int30_100));
    }
    @Test(expected = NullPointerException.class)
    public void isUnionableWithThrows(){
        int0_0.isUnionableWith(null);
    }

    @Test
    public void isUnionableWithWorks(){
        assertEquals(true, int0_0.isUnionableWith(intm1_0));
        assertEquals(true, int0_0.isUnionableWith(int0_1));
        assertEquals(true, intm2_m1.isUnionableWith(intm1_0));
        assertEquals(true, intm20_12.isUnionableWith(intm5_70));
        assertEquals(true, intm5_70.isUnionableWith(int30_100));
        assertEquals(true, intm2_m1.isUnionableWith(int0_0));
        assertEquals(true, intm2_m1.isUnionableWith(int0_1));
        assertEquals(false, intm20_12.isUnionableWith(int30_100));
    }


    @Test(expected = NullPointerException.class)
    public void unionThrowsForNull(){
        int30_100.union(null);
    }

    @Test
    public void unionWorks(){
        assertEquals(new Interval1D(-1,0),
                int0_0.union(intm1_0));
        assertEquals(new Interval1D(0,1), int0_0.union(int0_1));
        assertEquals(new Interval1D(-2,0), intm2_m1.union(intm1_0));
        assertEquals(new Interval1D(-20,70), intm20_12.union(intm5_70));
        assertEquals(new Interval1D(-5,100), intm5_70.union(int30_100));
    }

    @Test
    public void equalsWorks(){
        assertEquals(true,int0_0.equals(new Interval1D(0,0)));
        assertEquals(true,int0_0.equals(int0_0));
        assertEquals(false, int0_0.equals(int0_1));
    }

    @Test
    public void toStringWorks(){
        assertEquals("[-5..70]", intm5_70.toString());
        assertEquals("[0..0]",int0_0.toString());
    }

}
