package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.Preconditions;

/**
 * Describes a Discrete Elevation Model
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */

public interface DiscreteElevationModel extends AutoCloseable
{
    int SAMPLES_PER_DEGREE = 3600;
    double SAMPLES_PER_RADIAN = SAMPLES_PER_DEGREE * 180 / Math.PI;

    /**
     * Provides the index of a given angle.
     *
     * @param angle angle whose index in the DEM is needed
     * @return the index of the input angle
     */
    static double sampleIndex(double angle)
    {
        return angle * DiscreteElevationModel.SAMPLES_PER_RADIAN;
    }

    /**
     * Provides the domain on which the DEM has data
     *
     * @return A two dimensional integer interval whose bounds are the same than the extent of the DEM
     */
    Interval2D extent();

    /**
     * Provides the altitude at a given point
     *
     * @param x index of the point whose altitude is asked (on sampleIndex)
     * @param y index of the point whose altitude is asked (on SampleIndex)
     * @return the altitude at the input point
     * @throws IllegalArgumentException if the DEM has no data for the input point
     */
    double elevationSample(int x, int y) throws IllegalArgumentException;

    /**
     * Performs the union between two DEMs if they are unionable
     *
     * @param that Other DEM with which the current DEM will be merged
     * @return a DEM containing both the current DEM and the input one
     * @throws IllegalArgumentException if the two DEMs are not unionable
     * @see Interval2D
     */
    default DiscreteElevationModel union(DiscreteElevationModel that) throws IllegalArgumentException
    {
        Preconditions.checkArgument(this.extent().isUnionableWith(that.extent()));
        return new CompositeDiscreteElevationModel(this, that);
    }
}
