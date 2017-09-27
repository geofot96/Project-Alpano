package ch.epfl.alpano.gui;

import static java.lang.Math.abs;

/**
 * Represents the parameters that will be available to the user and their boundaries
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public enum UserParameters
{
    OBSERVER_LONGITUDE(60000, 120000),
    OBSERVER_LATITUDE(450000, 480000),
    OBSERVER_ELEVATION(300, 10000),
    CENTER_AZIMUTH(0, 359),
    HORIZONTAL_FIELD_OF_VIEW(1, 360),
    MAX_DISTANCE(10, 600),//kilometers
    WIDTH(30, 16000),
    HEIGHT(10, 4000),
    SUPER_SAMPLING_EXPONENT(0, 2);

    private final int min, max;

    /**
     * constructor that sets the minimum and the maximum values of a parameter
     *
     * @param min the minimum value
     * @param max the maximum value
     */
    UserParameters(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * checks if a parameter value is between its boundaries and if not returns the value closest to the boundaries
     *
     * @param val the value of the parameter
     * @return the value if it is between the boundaries or the closest value to the boundaries if it's not
     */
    public int sanitize(int val)
    {
        return (betweenBoundaries(val, min, max) ? val : minDist(val, min, max));
    }

    /**
     * checks if a value is between two boundaries, implemented to simplify the sanitize method
     *
     * @param val the value to be checked
     * @param min the lower boundary
     * @param max the upper boundary
     * @return true if the value is between the boundaries, false otherwise
     */
    private boolean betweenBoundaries(int val, int min, int max)
    {
        return val <= max && val >= min;
    }

    /**
     * calculates to which of two values a third value is closer to, implemented to simplify the sanitize method
     *
     * @param val the value to be checked
     * @param min the first value
     * @param max the second value
     * @return the first value if val is closer to it, the second value otherwise
     */
    private int minDist(int val, int min, int max)
    {
        return (abs(val - min) <= abs(val - max) ? min : max);
    }
}
