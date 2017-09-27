package ch.epfl.alpano;

import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.ElevationProfile;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

/**
 * Computes the representation of a panorama.
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class PanoramaComputer
{
    private final ContinuousElevationModel cem;
    private final static double REFRACTION_INDEX = 0.13;
    private final static double DX = 64;
    private final static double EPSILON = 4;
    private final static double RAY_TO_GROUND_DISTANCE_COEFFICIENT = (1 - REFRACTION_INDEX) / (2 * Distance.EARTH_RADIUS);

    /**
     * Constructor for the computer
     *
     * @param cem Continuous elevation model which is used.
     */

    public PanoramaComputer(ContinuousElevationModel cem)
    {
        this.cem = Objects.requireNonNull(cem);
    }

    /**
     * Computes the image representing the StandalonePanorama
     *
     * @param parameters parameters of the image which is to be created
     * @return a StandalonePanorama with the parameters given
     */
    public Panorama computePanorama(PanoramaParameters parameters)
    {
        final GeoPoint observerPos = parameters.observerPosition();
        final double height = parameters.height();
        final double width = parameters.width();
        final double observerElevation = parameters.observerElevation();
        final double maxDistance = parameters.maxDistance();
        final Panorama.Builder panoramaBuilder = new Panorama.Builder(parameters);

        for(int i = 0; i < width; ++i)
        {
            double intersectDistance = 0d;
            ElevationProfile elevationProfile = new ElevationProfile(this.cem, observerPos, parameters.azimuthForX((double) i), maxDistance);
            for(int j = (int) height - 1; j >= 0; j--)
            {
                DoubleUnaryOperator rayToGroundDistance = PanoramaComputer.rayToGroundDistance(elevationProfile, observerElevation, Math.tan(parameters.altitudeForY(j)));
                double beginIntervalIntersect = Math2.firstIntervalContainingRoot(rayToGroundDistance, intersectDistance, maxDistance,
                        DX);
                if(beginIntervalIntersect == Double.POSITIVE_INFINITY)
                    break;

                intersectDistance = Math2.improveRoot(rayToGroundDistance, beginIntervalIntersect, beginIntervalIntersect + DX, EPSILON);
                GeoPoint intersectPosition = elevationProfile.positionAt(intersectDistance);

                panoramaBuilder.setDistanceAt(i, j, (float) (intersectDistance / Math.cos(parameters.altitudeForY(j))));
                panoramaBuilder.setLatitudeAt(i, j, (float) intersectPosition.latitude());
                panoramaBuilder.setLongitudeAt(i, j, (float) intersectPosition.longitude());
                panoramaBuilder.setElevationAt(i, j, (float) cem.elevationAt(intersectPosition));
                panoramaBuilder.setSlopeAt(i, j, (float) cem.slopeAt(intersectPosition));
            }
        }
        return panoramaBuilder.build();
    }

    /**
     * Creates a function which computes the distance between the ground and a ray.
     *
     * @param profile  function for the ground
     * @param ray0     initial height of the ray
     * @param raySlope initial slope of the ray (in radians)
     * @return a function
     */
    public static DoubleUnaryOperator rayToGroundDistance(ElevationProfile profile, double ray0, double raySlope)
    {
        return x -> ray0 + x * raySlope - profile.elevationAt(x) + Math2.sq(x) * RAY_TO_GROUND_DISTANCE_COEFFICIENT;
    }
}