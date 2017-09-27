package ch.epfl.alpano;

import java.util.Objects;

/**
 * Describes a two-dimensional interval of integers
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charllais (264231)
 */
public final class Interval2D
{
    private final Interval1D iX;
    private final Interval1D iY;

    /**
     * constructor of the class Interval2D that initializes
     * the two uni-dimensional intervals that describe it
     *
     * @param iX the first uni-dimensional interval
     * @param iY the second uni-dimensional interval
     */
    public Interval2D(Interval1D iX, Interval1D iY) throws NullPointerException
    {
        this.iX = Objects.requireNonNull(iX);
        this.iY = Objects.requireNonNull(iY);
    }

    /**
     * getter of the class for the iX attribute
     *
     * @return iX attribute
     */
    public Interval1D iX()
    {
        return iX;
    }

    /**
     * getter of the class for the iY attribute
     *
     * @return iY attribute
     */
    public Interval1D iY()
    {
        return iY;
    }

    /**
     * checks if a pair of numbers are contained inside each of the uni-dimensional intervals
     *
     * @param x the first number to be checked
     * @param y the second number to be checked
     * @return true if the numbers are contained and false otherwise
     */
    public boolean contains(int x, int y)
    {
        return iX.contains(x) && iY.contains(y);
    }

    /**
     * calculates the size of the interval
     *
     * @return the size of the interval
     */
    public int size()
    {
        return iX.size() * iY.size();
    }

    /**
     * calculates the size of the intersection of a pair of intervals
     *
     * @param that the second interval
     * @return the size of the intersection
     */
    public int sizeOfIntersectionWith(Interval2D that)
    {
        return iX.sizeOfIntersectionWith(that.iX) * iY.sizeOfIntersectionWith(that.iY);
    }


    /**
     * calculates the bounding union of a pair of intervals
     *
     * @param that the second interval
     * @return the bounding union of the two intervals
     */
    public Interval2D boundingUnion(Interval2D that)
    {
        return new Interval2D(this.iX.boundingUnion(that.iX), this.iY.boundingUnion(that.iY));
    }

    /**
     * checks if two intervals are unionable
     *
     * @param that the second interval
     * @return true if they are unionable, false otherwise
     */
    public boolean isUnionableWith(Interval2D that)
    {
        return (this.size() + that.size() - this.sizeOfIntersectionWith(that) == this.boundingUnion(that).size());
    }

    /**
     * calculates the union between two intervals
     *
     * @param that the second intervals
     * @return the union of the intervals
     * @throws IllegalArgumentException if the intervals are not unionable
     */
    public Interval2D union(Interval2D that) throws IllegalArgumentException
    {
        Preconditions.checkArgument(this.isUnionableWith(that));
        return this.boundingUnion(that);
    }

    @Override
    public boolean equals(Object thatO)
    {
        if(this == thatO)
            return true;
        if(thatO != null && thatO instanceof Interval2D)
        {
            Interval2D that = (Interval2D) thatO;
            return iX.equals(that.iX) && iY.equals(that.iY);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(iX(), iY());
    }

    @Override
    public String toString()
    {
        return iX.toString() + "×" + iY.toString();
    }
}
