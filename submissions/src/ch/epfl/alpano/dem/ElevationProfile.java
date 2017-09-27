package ch.epfl.alpano.dem;

import ch.epfl.alpano.*;

import static ch.epfl.alpano.Math2.lerp;
import static java.lang.Math.*;
import static java.util.Objects.requireNonNull;

/**
 * Represents an elevation profile
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class ElevationProfile
{
    private final static int DIST = 4096;

    private final double length;
    private final double[][] tab;
    private final ContinuousElevationModel cem;

    /**
     * constructor of the class
     * the columns of the table contain : longitude and latitude
     *
     * @param elevationModel the elevation model who's elevation profile is to bbe created
     * @param origin         the origin of the elevation profile
     * @param azimuth        the direction of the elevation profile
     * @param length         the length of the elevation profile
     * @throws IllegalArgumentException if the azimuth is not canonical or if the length is not positive
     * @throws NullPointerException     if the the CEM or the GeoPoint attributes are null
     */
    public ElevationProfile(ContinuousElevationModel elevationModel, GeoPoint origin, double azimuth, double length) throws IllegalArgumentException, NullPointerException
    {
        Preconditions.checkArgument(Azimuth.isCanonical(azimuth));
        this.cem = requireNonNull(elevationModel);
        Preconditions.checkArgument((this.length = length) > 0);
        tab = new double[(int) (ceil(length / DIST) + 1)][2]; //Double[sample number][ longitude, latitude]
        for(int i = 0; i < ceil(length / DIST) + 1; i++)
        {
            double temp = sin(origin.latitude()) * cos(Distance.toRadians(DIST * i));
            temp += cos(origin.latitude()) * sin(Distance.toRadians(DIST * i)) * cos(Azimuth.toMath(azimuth));
            tab[i][1] = asin(temp);
            temp = asin((sin(Azimuth.toMath(azimuth)) * sin(Distance.toRadians(DIST * i))) / cos(tab[i][1]));
            temp = origin.longitude() - temp + PI;
            tab[i][0] = (temp % Math2.PI2) - PI;
        }

    }

    /**
     * calculates the elevation in a given position
     *
     * @param x the position of the point
     * @return the elevation at this point
     * @throws IllegalArgumentException if the point does not belong to the elevation profile
     */
    public double elevationAt(double x) throws IllegalArgumentException
    {
        Preconditions.checkArgument(x >= 0 && x <= length);
        return cem.elevationAt(positionAt(x));
    }

    /**
     * calculates the position of a given point
     *
     * @param x the point
     * @return the coordinates of the point
     * @throws IllegalArgumentException if the point does not belong to the elevation profile
     */
    public GeoPoint positionAt(double x) throws IllegalArgumentException
    {
        Preconditions.checkArgument(x >= 0 && x <= length);
        return new GeoPoint(lerp(tab[(int) (floor(x / DIST))][0], tab[(int) (ceil(x / DIST))][0], (x % DIST) / DIST), lerp(tab[(int) (floor(x / DIST))][1], tab[(int) (ceil(x / DIST))][1], (x % DIST) / DIST));
    }

    /**
     * calculates the slope in a given position
     *
     * @param x the position of the point
     * @return the slope at this point
     * @throws IllegalArgumentException if the point does not belong to the slope profile
     */
    public double slopeAt(double x) throws IllegalArgumentException
    {
        Preconditions.checkArgument(x >= 0 && x <= length);
        return cem.slopeAt(positionAt(x));
    }

}
