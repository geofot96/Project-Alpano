package ch.epfl.alpano;

import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.PI;

/**
 * Contains multiple mathematical methods
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */

public interface Math2
{
    /**
     * The double of Pi
     */
    double PI2 = 2 * PI;

    /**
     * calculates the square of a number
     *
     * @param x the number who's square in going to be calculated
     * @return the square of the number
     */
    static double sq(double x)
    {
        return x * x;
    }

    /**
     * calculates the floored division between two numbers
     *
     * @param x the divident
     * @param y the divisor
     * @return the result of the floored division
     */
    static double floorMod(double x, double y)
    {
        return x - y * (int) Math.floor(x / y);
    }

    /**
     * calculates the haversin of a specific value
     *
     * @param x the value
     * @return the haversin of the value
     */
    static double haversin(double x)
    {
        return sq(Math.sin(x / 2));
    }

    /**
     * calculates the distance between two angles
     *
     * @param a1 the first angle
     * @param a2 the second angle
     * @return the distance between the two angles in radians
     */
    static double angularDistance(double a1, double a2)
    {
        return floorMod(a2 - a1 + PI, PI2) - PI;
    }

    /**
     * calculates the linear interpolation of a point of a function f(x) between two other points
     *
     * @param y0 f(0)
     * @param y1 f(1)
     * @param x  the values who's linear interpolation will be calculated
     * @return f(x)
     */
    static double lerp(double y0, double y1, double x)
    {
        return ((y1 - y0) * x + y0);
    }

    /**
     * calculates the bilinear interpolation of a point of a function f(x,y) between four other points
     *
     * @param z00 f(0,0)
     * @param z10 f(1,0)
     * @param z01 f(0,1)
     * @param z11 f(1,1)
     * @param x   the first variable of the function f
     * @param y   the second variable of the function f
     * @return f(x, y)
     */
    static double bilerp(double z00, double z10, double z01, double z11, double x, double y)
    {
        return (lerp(lerp(z00, z10, x), lerp(z01, z11, x), y));
    }

    /**
     * finds the first interval of a specific size that contains a 0 of the function f, between two numbers
     *
     * @param f    the function
     * @param minX the first number
     * @param maxX the second number
     * @param dX   the size of the interval
     * @return the lower bound of the interval
     */
    static double firstIntervalContainingRoot(DoubleUnaryOperator f, double minX, double maxX, double dX)
    {
        double val = minX + dX;

        while(val <= maxX)
        {
            if(f.applyAsDouble(minX) * f.applyAsDouble(val) <= 0)
                return minX;
            minX += dX;
            val += dX;
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * finds an interval of length smaller or equal than epsilon, between two numbers that contains a zero of a function
     * and throws an exception if there is no zero of the function
     *
     * @param f       the function
     * @param x1      the first number
     * @param x2      the second number
     * @param epsilon the maximum length of the interval
     * @return the lower bound of the interval
     * @throws IllegalArgumentException if there x1 and x2 have the same sign
     */
    static double improveRoot(DoubleUnaryOperator f, double x1, double x2, double epsilon)
    {
        Preconditions.checkArgument(f.applyAsDouble(x1) * f.applyAsDouble(x2) <= 0);

        while(x2 - x1 > epsilon)
        {
            final double maxX = (x1 + x2) / 2.0;
            final double fMinX = f.applyAsDouble(x1);
            final double fMaxX = f.applyAsDouble(maxX);

            if(fMinX * fMaxX > 0)
                x1 = maxX;
            else
                x2 = maxX;
        }
        return x1;
    }
}
