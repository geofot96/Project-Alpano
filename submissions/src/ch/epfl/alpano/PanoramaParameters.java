package ch.epfl.alpano;

import java.util.Objects;

import static ch.epfl.alpano.Math2.PI2;

/**
 * Class containing the parameters of a panorama
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class PanoramaParameters
{

    private final GeoPoint observerPosition;
    private final double centerAzimuth;
    private final int observeElevation;
    private final double horizontalFieldOfView;
    private final double verticalFieldOfView;
    private final int maxDistance;
    private final int width;
    private final int height;
    private final double delta;

    /**
     * Constructs a Class containing all the parameters of a Panorama to be built
     *
     * @param observerPosition      Position of the observer
     * @param observerElevation     elevation of the observer
     * @param centerAzimuth         direction in which the panorama is built
     * @param horizontalFieldOfView horizontal angle of view
     * @param maxDistance           maximum distance of visibility
     * @param width                 number of horizontal points that will be computed
     * @param height                number of vertical points that will be computed
     * @throws IllegalArgumentException if the azimuth is not cannonical or
     *                                  if the distance is not strictly positive or
     *                                  if the width is not strictly bigger than 1
     *                                  if the width is not strictly bigger than 1
     * @throws NullPointerException     if observerPosition is null
     */
    public PanoramaParameters(GeoPoint observerPosition, int observerElevation,
                              double centerAzimuth, double horizontalFieldOfView, int maxDistance,
                              int width, int height) throws IllegalArgumentException, NullPointerException
    {
        this.observerPosition = Objects.requireNonNull(observerPosition);
        this.observeElevation = observerElevation;
        Preconditions.checkArgument(Azimuth.isCanonical(this.centerAzimuth = centerAzimuth));
        Preconditions.checkArgument(((this.horizontalFieldOfView = horizontalFieldOfView) <= PI2) && (horizontalFieldOfView > 0));
        Preconditions.checkArgument((this.maxDistance = maxDistance) > 0);
        Preconditions.checkArgument((this.width = width) > 1);
        Preconditions.checkArgument((this.height = height) > 1);
        delta = horizontalFieldOfView / ((double) width - 1);
        this.verticalFieldOfView = horizontalFieldOfView * (((double) height - 1.0) / ((double) width - 1.0));


    }

    /**
     * Getter for the observer's position
     *
     * @return observer's position
     */
    public GeoPoint observerPosition()
    {
        return observerPosition;
    }

    /**
     * Getter for the direction in which the panorama is orientated
     *
     * @return an angle (in radians) indicating the direction of the panorama
     */
    public double centerAzimuth()
    {
        return centerAzimuth;
    }

    /**
     * Getter for the observer elevation
     *
     * @return the elevation of the observer
     */
    public int observerElevation()
    {
        return observeElevation;
    }

    /**
     * getter for the horizontal field of view
     *
     * @return horizontal angle of view
     */
    public double horizontalFieldOfView()
    {
        return horizontalFieldOfView;
    }

    /**
     * getter for the maximum distance
     *
     * @return the distance at which the panorama stops
     */
    public int maxDistance()
    {
        return maxDistance;
    }

    /**
     * getter for the width
     *
     * @return number of vertical points to be computed
     */
    public int width()
    {
        return width;
    }

    /**
     * getter for the height
     *
     * @return number of vertical points to be computed
     */
    public int height()
    {
        return height;
    }

    /**
     * getter for the vertical field of view
     *
     * @return vertical angle of view
     */
    public double verticalFieldOfView()
    {
        return verticalFieldOfView;
    }

    /**
     * Computes the angle corresponding to an index in the panorama
     *
     * @param x the index for which the angle is required
     * @return the angle corresponding to the index
     * @throws IllegalArgumentException if x does not belong to the panorama
     */
    public double azimuthForX(double x) throws IllegalArgumentException
    {
        Preconditions.checkArgument(x >= 0 && !(x > width - 1));
        return Azimuth.canonicalize(x * delta + centerAzimuth - horizontalFieldOfView / 2);
    }

    /**
     * returns the index corresponding to a certain angle
     *
     * @param a the angle for which the index is required
     * @return the index corresponding to an angle
     * @throws IllegalArgumentException if the angle does not belong to the panorama
     */
    public double xForAzimuth(double a) throws IllegalArgumentException
    {
        Preconditions.checkArgument(Math.abs(Math2.angularDistance(a, centerAzimuth) * 2) <= horizontalFieldOfView);
        return (a - centerAzimuth + horizontalFieldOfView / 2) / delta;
    }

    /**
     * Computes the angle corresponding to an index in the panorama
     *
     * @param y the index for which the angle is required
     * @return the angle corresponding to the index
     * @throws IllegalArgumentException if x does not belong to the panorama
     */
    public double altitudeForY(double y) throws IllegalArgumentException
    {
        Preconditions.checkArgument(y >= 0 && !(y > height - 1));

        return (((double) height - 1.0) / 2.0 - y) * delta;
    }

    /**
     * returns the index corresponding to a certain angle
     *
     * @param a the angle for which the index is required
     * @return the index corresponding to an angle
     * @throws IllegalArgumentException if the angle does not belong to the panorama
     */
    public double yForAltitude(double a) throws IllegalArgumentException
    {
        Preconditions.checkArgument(a >= -((height - 1.0) / 2.0) * delta && a <= ((height - 1.0) / 2.0) * delta);
        return -a / delta + ((double) height - 1.0) / 2.0;
    }

    /**
     * Checks whether a point belongs to the panorama
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if it does false otherwise
     */
    boolean isValidSampleIndex(int x, int y)
    {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    /**
     * converts a 2d point into a point in linear index.
     *
     * @param x vertical coordinate
     * @param y horizontal coordinate
     * @return the index.
     */
    int linearSampleIndex(int x, int y)
    {
        return x + width * y;
    }
}
