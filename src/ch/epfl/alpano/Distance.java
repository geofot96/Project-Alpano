package ch.epfl.alpano;

/**
 * Contains methods to convert distances from meters to radians and vice versa
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charllais (264231)
 */
public interface Distance
{
    double EARTH_RADIUS = 6371000;

    /**
     * converts the distance from meters to radiants
     *
     * @param distanceInMeters the distance to be converted
     * @return the converted distance in meters
     */
    static double toRadians(double distanceInMeters)
    {
        return distanceInMeters / EARTH_RADIUS;
    }

    /**
     * converts the distance from radians to meters
     *
     * @param distanceInRadians the distance to be converted
     * @return the converted distance in radians
     */
    static double toMeters(double distanceInRadians)
    {
        return distanceInRadians * EARTH_RADIUS;
    }
}
