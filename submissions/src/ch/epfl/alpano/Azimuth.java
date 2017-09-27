package ch.epfl.alpano;

import static ch.epfl.alpano.Math2.PI2;
import static java.lang.Math.PI;

/**
 * Contains multiple mathematical methods to do manipulations with the azimuth angle
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charllais (264231)
 */

public interface Azimuth
{
    /**
     * checks if the azimuth angle is between 0 and 360 degrees
     *
     * @param azimuth the angle
     * @return true if it is between 0 and 360, false otherwise
     */
    static boolean isCanonical(double azimuth)
    {
        return (0 <= azimuth && azimuth < PI2);
    }

    /**
     * converts the azimuth angle in the range of 0 until 360 degrees
     *
     * @param azimuth the angle
     * @return the canonicalized azimuth
     */
    static double canonicalize(double azimuth)
    {
        return ((isCanonical(azimuth) ? azimuth : Math2.floorMod(azimuth, PI2)));
    }

    /**
     * converts the azimuth angle from clockwise to counter-clockwise
     *
     * @param azimuth the angle
     * @return the azimuth converted
     */
    static double toMath(double azimuth) throws IllegalArgumentException
    {
        Preconditions.checkArgument(isCanonical(azimuth));
        return canonicalize(-azimuth);
    }

    /**
     * converts an angle from counter-clockwise to clockwise
     *
     * @param angle the angle to be converted
     * @return the converted angle
     */
    static double fromMath(double angle) throws IllegalArgumentException
    {
        Preconditions.checkArgument(isCanonical(angle));
        return canonicalize(-angle);
    }

    /**
     * translates an angle to a combination of four given strings that determine the orientation
     *
     * @param azimuth the angle
     * @param n       northDiscreteElevationModel
     * @param e       east
     * @param s       south
     * @param w       west
     * @return a combination of the given strings
     */
    static String toOctantString(double azimuth, String n, String e, String s, String w) throws IllegalArgumentException
    {
        Preconditions.checkArgument(isCanonical(azimuth));
        String[] cardPoints = {n, n + e, e, s + e, s, s + w, w, n + w};

        return cardPoints[(int) (Math.floor(canonicalize(azimuth + PI / 8) / (PI / 4)))];
    }
}
