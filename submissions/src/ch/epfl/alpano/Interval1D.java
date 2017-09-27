package ch.epfl.alpano;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Describes an interval of integers
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charllais (264231)
 */
public final class Interval1D
{
    private final int includedFrom;
    private final int includedTo;

    /**
     * constructor of the class Interval1D that initializes the starting and the ending
     * point of the interval
     *
     * @param includedFrom the starting point of the interval
     * @param includedTo   the ending point of the interval
     * @throws IllegalArgumentException if the ending point of the interval is inferior to the starting point
     */
    public Interval1D(int includedFrom, int includedTo)
    {
        Preconditions.checkArgument(!(includedTo < includedFrom));
        this.includedFrom = includedFrom;
        this.includedTo = includedTo;
    }

    /**
     * getter of the class for the includedTo attribute
     *
     * @return the ending point of the interval
     */
    public int includedTo()
    {
        return includedTo;
    }

    /**
     * getter of the class for the includedFrom attribute
     *
     * @return the starting point of the interval
     */
    public int includedFrom()
    {
        return includedFrom;
    }

    /**
     * checks if a number is contained inside the interval
     *
     * @param v the number to be checked
     * @return true if the number is contained and false otherwise
     */
    public boolean contains(int v)
    {
        return (v <= includedTo && v >= includedFrom);
    }

    /**
     * calculates the size of the interval
     *
     * @return the size of the interval
     */
    public int size()
    {
        return includedTo - includedFrom + 1;
    }

    /**
     * calculates the size of the intersection of two intervals
     *
     * @param that the second interval
     * @return the size of the intersection
     */
    public int sizeOfIntersectionWith(Interval1D that)
    {
        int from = max(this.includedFrom, that.includedFrom);
        int to = min(this.includedTo, that.includedTo);

        return (to - from >= 0) ? to - from + 1 : 0;
    }

    /**
     * calculates the bounding union of two intervals
     *
     * @param that the second interval
     * @return the bounding union of the two intervals
     */
    public Interval1D boundingUnion(Interval1D that)
    {
        return new Interval1D(min(this.includedFrom(), that.includedFrom()), max(this.includedTo(), that.includedTo()));
    }

    /**
     * checks if two intervals are unionable
     *
     * @param that the second interval
     * @return true if they are unionable, false otherwise
     */
    public boolean isUnionableWith(Interval1D that)
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
    public Interval1D union(Interval1D that) throws IllegalArgumentException
    {
        Preconditions.checkArgument(this.isUnionableWith(that));
        return this.boundingUnion(that);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(includedFrom(), includedTo());
    }

    @Override
    public boolean equals(Object thatO)
    {
        if(this == thatO)
            return true;
        if(thatO != null && thatO instanceof Interval1D)
        {
            Interval1D that = (Interval1D) thatO;
            return includedTo == that.includedTo && includedFrom == that.includedFrom;
        }
        return false;

    }

    @Override
    public String toString()
    {
        return "[" + Integer.toString(includedFrom) + ".." + Integer.toString(includedTo) + "]";
    }
}
