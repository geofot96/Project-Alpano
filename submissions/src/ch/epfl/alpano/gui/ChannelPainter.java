package ch.epfl.alpano.gui;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.dem.ContinuousElevationModel;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Functional interface which represents a channel painter
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
@FunctionalInterface
public interface ChannelPainter
{
    /**
     * the abstract method of the interface, which calculates
     * the value of the channel at a specific point
     *
     * @param x the first coordinate of the point
     * @param y the second coordinate of the point
     * @return the value of the channel at this point
     */
    float valueAt(int x, int y);

    /**
     * gives the maximal distance to the neighboring pixels
     *
     * @param panorama panorama whose pixels need to be painted
     * @return a ChannelPainter for the maximum distance to neighbours
     */
    static ChannelPainter maxDistanceToNeighbour(Panorama panorama)
    {
        return (x, y) -> (Math.max(Math.max(panorama.distanceAt(x - 1, y, 0), panorama.distanceAt(x + 1, y, 0)), Math.max(panorama.distanceAt(x, y - 1, 0), panorama.distanceAt(x, y + 1, 0))) - panorama.distanceAt(x, y));
    }

    /**
     * calculates the distance between points in the panorama added to
     * facilitate method calls in
     * {@link PanoramaComputerBean#PanoramaComputerBean(ContinuousElevationModel, List)}
     *
     * @param panorama the panorama containing the points
     * @return the new channel painter
     */
    static ChannelPainter distanceAt(Panorama panorama)
    {
        return panorama::distanceAt;
    }

    /**
     * adds the value of the channel with a given constant
     *
     * @param a the constant
     * @return the new channel painter
     */
    default ChannelPainter add(float a)
    {
        return (x, y) -> valueAt(x, y) + a;
    }

    /**
     * subtracts the value of the channel with a given constant
     *
     * @param a the constant
     * @return the new channel painter
     */
    default ChannelPainter sub(float a)
    {
        return (x, y) -> valueAt(x, y) - a;
    }

    /**
     * multiplies the value of the channel by a given constant
     *
     * @param a the constant
     * @return the new channel painter
     */
    default ChannelPainter multiply(float a)
    {
        return (x, y) -> valueAt(x, y) * a;
    }

    /**
     * divides the value of the channel by a given constant
     *
     * @param a the constant
     * @return the new channel painter
     */
    default ChannelPainter divBy(float a)
    {
        return (x, y) -> valueAt(x, y) / a;
    }

    /**
     * applies a unary operator to a value which is produced by the channel
     *
     * @param f the unary operator
     * @return the new channel painter
     */
    default ChannelPainter map(DoubleUnaryOperator f)
    {
        return ((x, y) -> (float) f.applyAsDouble((double) valueAt(x, y)));
    }

    /**
     * applies the invert function to a a value which is produced by the channel
     *
     * @return the new channel painter
     */
    default ChannelPainter inverted()
    {
        return ((x, y) -> 1 - valueAt(x, y));
    }

    /**
     * applies the clamp function to a a value which is produced by the channel
     *
     * @return the new channel painter
     */
    default ChannelPainter clamp()
    {
        return ((x, y) -> Math.max(0, Math.min(valueAt(x, y), 1)));
    }

    /**
     * applies the cycle function to a a value which is produced by the channel
     *
     * @return the new channel painter
     */
    default ChannelPainter cycle()
    {
        return ((x, y) -> valueAt(x, y) % 1);
    }
}
