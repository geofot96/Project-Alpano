package my_tests.etape1To3;

import static org.junit.Assert.*;

import ch.epfl.alpano.Interval1D;
import org.junit.Test;

public class Interval1DTestGF
{
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument()
    {
        final Interval1D interval = new Interval1D(5, 2);
    }

    @Test
    public void testincludedFromTo()
    {
        final Interval1D interval = new Interval1D(1, 2);
        assertEquals(1, interval.includedFrom(), 0);
        assertEquals(2, interval.includedTo(), 0);
    }

    @Test
    public void testSize()
    {
        final Interval1D interval = new Interval1D(50, 100);
        assertEquals(51, interval.size());
    }

    @Test
    public void testContains()
    {
        assertTrue(new Interval1D(0, 3).contains(2));
        assertTrue(new Interval1D(0, 3).contains(0));
        assertTrue(new Interval1D(0, 3).contains(3));
        assertFalse(new Interval1D(0, 3).contains(4));
        assertFalse(new Interval1D(0, 3).contains(-1));
    }

    @Test
    public void testSizeOfIntersection()
    {
        assertEquals(0, new Interval1D(0, 1).sizeOfIntersectionWith(new Interval1D(4, 5)));
        assertEquals(1, new Interval1D(0, 3).sizeOfIntersectionWith(new Interval1D(3, 5)));
        assertEquals(2, new Interval1D(0, 2).sizeOfIntersectionWith(new Interval1D(1, 5)));
        assertEquals(4, new Interval1D(0, 3).sizeOfIntersectionWith(new Interval1D(-1, 5)));

        assertEquals(2, new Interval1D(1, 3).sizeOfIntersectionWith(new Interval1D(0, 2)));
        assertEquals(2, new Interval1D(1, 2).sizeOfIntersectionWith(new Interval1D(0, 2)));

    }

    @Test
    public void testIntersectionSizeReturnsTheSameResultNoMatterTheSequenceOfTheInputs()
    {
        assertEquals(0, new Interval1D(4, 5).sizeOfIntersectionWith(new Interval1D(0, 1)));
        assertEquals(1, new Interval1D(3, 5).sizeOfIntersectionWith(new Interval1D(0, 3)));
        assertEquals(2, new Interval1D(1, 5).sizeOfIntersectionWith(new Interval1D(0, 2)));
        assertEquals(4, new Interval1D(-1, 5).sizeOfIntersectionWith(new Interval1D(0, 3)));
    }

    @Test
    public void testBoundingUnion()
    {
        final Interval1D interval1 = new Interval1D(1, 4);
        final Interval1D interval2 = new Interval1D(3, 8);
        final Interval1D interval3 = new Interval1D(5, 6);

        final Interval1D expected1 = new Interval1D(1, 8);
        final Interval1D expected2 = new Interval1D(1, 6);
        final Interval1D expected3 = new Interval1D(3, 8);
/*
        assertEquals(expected1, interval1.boundingUnion(interval2));
        assertEquals(expected2, interval1.boundingUnion(interval3));
        assertEquals(expected3, interval2.boundingUnion(interval3));*/
        assertEquals(expected1.includedFrom(), interval1.boundingUnion(interval2).includedFrom());
        assertEquals(expected1.includedTo(), interval1.boundingUnion(interval2).includedTo());

        assertEquals(expected2.includedFrom(), interval1.boundingUnion(interval3).includedFrom());
        assertEquals(expected2.includedTo(), interval1.boundingUnion(interval3).includedTo());

        assertEquals(expected3.includedFrom(), interval2.boundingUnion(interval3).includedFrom());
        assertEquals(expected3.includedTo(), interval2.boundingUnion(interval3).includedTo());
    }

    @Test
    public void testBoundingUnionReturnsTheSameResultNoMatterTheSequenceOfTheInputs()
    {
        final Interval1D interval1 = new Interval1D(1, 4);
        final Interval1D interval2 = new Interval1D(3, 8);
        final Interval1D interval3 = new Interval1D(5, 6);

        final Interval1D expected1 = new Interval1D(1, 8);
        final Interval1D expected2 = new Interval1D(1, 6);
        final Interval1D expected3 = new Interval1D(3, 8);

        assertEquals(expected1.includedFrom(), interval2.boundingUnion(interval1).includedFrom());
        assertEquals(expected1.includedTo(), interval2.boundingUnion(interval1).includedTo());

        assertEquals(expected2.includedFrom(), interval3.boundingUnion(interval1).includedFrom());
        assertEquals(expected2.includedTo(), interval3.boundingUnion(interval1).includedTo());

        assertEquals(expected3.includedFrom(), interval3.boundingUnion(interval2).includedFrom());
        assertEquals(expected3.includedTo(), interval3.boundingUnion(interval2).includedTo());
    }

    @Test
    public void testIsUnionable()
    {
        final Interval1D interval1 = new Interval1D(1, 4);
        final Interval1D interval2 = new Interval1D(3, 8);
        final Interval1D interval3 = new Interval1D(6, 7);
        final Interval1D interval4 = new Interval1D(6, 7);

        assertTrue(interval1.isUnionableWith(interval2));
        assertTrue(interval2.isUnionableWith(interval1));
        assertFalse(interval1.isUnionableWith(interval3));
        assertFalse(interval3.isUnionableWith(interval1));
        assertTrue(interval3.isUnionableWith(interval4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnionIllegalArgument()
    {
        final Interval1D interval1 = new Interval1D(0, 2);
        final Interval1D interval2 = new Interval1D(5, 7);

        interval1.union(interval2);
    }

    @Test
    public void testUnion()
    {
        final Interval1D interval1 = new Interval1D(0, 1);
        final Interval1D interval2 = new Interval1D(2, 4);

        final Interval1D expected = new Interval1D(0, 4);
        assertEquals(expected, interval1.union(interval2));

        assertEquals(expected.includedFrom(), interval1.union(interval2).includedFrom());
        assertEquals(expected.includedTo(), interval1.union(interval2).includedTo());
    }

    @Test
    public void testEqualsHashCode()
    {
        final Interval1D interval1 = new Interval1D(27, 48);
        final Interval1D interval2 = new Interval1D(27, 48);
        final Interval1D interval3 = new Interval1D(27, 49);

        assertFalse(interval1.equals(null));
        assertFalse(interval1.equals(new Object()));

        assertTrue(interval1.equals(interval2));
        assertTrue(interval2.equals(interval1));

        assertFalse(interval1.equals(interval3));
        assertFalse(interval3.equals(interval1));

        assertEquals(interval1.hashCode(), interval2.hashCode());
        assertEquals(interval1.hashCode(), interval1.hashCode());
        assertNotEquals(interval1.hashCode(), interval3.hashCode());
    }

    @Test
    public void testToString()
    {
        final Interval1D interval = new Interval1D(3, 14);
        assertEquals("[3..14]", interval.toString());
    }
}
