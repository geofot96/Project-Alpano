package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.Preconditions;

import static java.util.Objects.requireNonNull;

/**
 * Represents the union between to discrete elevation models
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
final class CompositeDiscreteElevationModel implements DiscreteElevationModel
{
    private final Interval2D Composite;
    private final DiscreteElevationModel d1;
    private final DiscreteElevationModel d2;

    /**
     * Constructs a new DEM with two exisiting ones.
     *
     * @param dem1 first DEM to merge
     * @param dem2 second DEM to merge
     * @throws NullPointerException if either of the input DEMs is null
     */
    CompositeDiscreteElevationModel(DiscreteElevationModel dem1, DiscreteElevationModel dem2)
    {

        Preconditions.checkArgument(dem1.extent().isUnionableWith(dem2.extent()));
        d1 = requireNonNull(dem1);
        d2 = requireNonNull(dem2);
        Composite = dem1.extent().union(dem2.extent());
    }

    /**
     * Provides the domain on which the DEM has data
     *
     * @return A two dimensional integer interval whose bounds are the same than the extent of the DEM
     * @see DiscreteElevationModel
     */
    @Override
    public Interval2D extent()
    {
        return Composite;
    }

    /**
     * Provides the altitude at a given point
     *
     * @param x longitude of the point whose altitude is asked (in radians)
     * @param y latitude of the point whose altitude is asked (in radians)
     * @return the altitude at the input point
     * @throws IllegalArgumentException if the DEM has no data for the input point
     * @see DiscreteElevationModel
     */
    @Override
    public double elevationSample(int x, int y) throws IllegalArgumentException
    {
        Preconditions.checkArgument(this.extent().contains(x, y), "coordinates outside domain");
        if(d1.extent().contains(x, y))
            return d1.elevationSample(x, y);
        else
            return d2.elevationSample(x, y);
    }

    /**
     * Closes the DEM files after usage
     *
     * @throws Exception if
     * @see AutoCloseable if the DEM cannot be closed
     */
    @Override
    public void close() throws Exception
    {
        d1.close();
        d2.close();
    }
}
