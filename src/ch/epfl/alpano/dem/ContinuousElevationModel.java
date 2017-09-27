package ch.epfl.alpano.dem;

import ch.epfl.alpano.Distance;
import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Math2;

import static ch.epfl.alpano.dem.DiscreteElevationModel.sampleIndex;
import static java.lang.Math.*;
import static java.util.Objects.requireNonNull;

/**
 * Describes a continuous elevation model obtain by interpolation of a DEM
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */

public final class ContinuousElevationModel
{
    private final DiscreteElevationModel dem;
    private final static double D = Distance.toMeters(1 / DiscreteElevationModel.SAMPLES_PER_RADIAN);

    /**
     * Constructor of the Continuous DEM (CDEM)
     *
     * @param dem DEM from which the CDEM is derivated
     */
    public ContinuousElevationModel(DiscreteElevationModel dem)
    {
        this.dem = requireNonNull(dem);
    }

    /**
     * Provides the altitude at a given GeoPoint by bilinear interpolation
     * with neighbouring points on the DEM
     *
     * @param p point at which the altitude is computed
     * @return the altitude of the point
     */
    public double elevationAt(GeoPoint p)
    {
        double xb = sampleIndex(p.longitude());
        double yb = sampleIndex(p.latitude());
        int x = (int) floor(xb);
        int y = (int) floor(yb);

        return Math2.bilerp(getDiscreteAltitude(x, y), getDiscreteAltitude(x + 1, y), getDiscreteAltitude(x, y + 1), getDiscreteAltitude(x + 1, y + 1), xb - x, yb - y);
    }

    /**
     * Provides the slope (angle between the normal to the ground and
     * the normal to the sphere) by bilinear interpolation with neighbouring points
     *
     * @param p point at which the slope is computed
     * @return the slope of the point
     */
    public double slopeAt(GeoPoint p)
    {
        double xb = sampleIndex(p.longitude());
        double yb = sampleIndex(p.latitude());
        int x = (int) floor(xb);
        int y = (int) floor(yb);

        return Math2.bilerp(getDiscreteSlope(x, y), getDiscreteSlope(x + 1, y), getDiscreteSlope(x, y + 1), getDiscreteSlope(x + 1, y + 1), xb - x, yb - y);
    }

    /**
     * Provides the altitude at a given index (belonging to the DEM)
     *
     * @param x longitude of the index whose altitude is to be given
     * @param y latitude of the index whose altitude is to be given
     * @return the altitude corresponding to the input index if it belongs to the DEM, 0 otherwise
     */
    private double getDiscreteAltitude(int x, int y)
    {
        if(dem.extent().contains(x, y))
            return dem.elevationSample(x, y);
        return 0;
    }

    /**
     * Provides the slope at a given index (belonging to the DEM)
     *
     * @param x longitude of the index whose slope is to be given
     * @param y latitude of the index whose slope is to be given
     * @return the slope corresponding to the input index if it belongs to the DEM, 0 otherwise
     */
    private double getDiscreteSlope(int x, int y)
    {
        double altitude = getDiscreteAltitude(x, y);
        double Dza = getDiscreteAltitude(x + 1, y) - altitude;
        double Dzb = getDiscreteAltitude(x, y + 1) - altitude;
        return acos(D / (sqrt(Math2.sq(Dza) + Math2.sq(Dzb) + Math2.sq(D))));
    }

}
