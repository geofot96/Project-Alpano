package ch.epfl.alpano;

import static java.lang.Math.*;

/**
 * Represents a point in the surface of the earth and contains multiple methods
 * to compute the distance or the angle between two such points
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charllais (264231)
 */
public final class GeoPoint
{
    private final double longitude;
    private final double latitude;

    /**
     * constructor of the class GeoPoint that initializes the longitude and latitude attributes
     *
     * @param longitude the longitude coordinate of the point
     * @param latitude  the latitude coordinate of the point
     * @throws IllegalArgumentException if the longitude isn't in the range of [-PI to PI] or
     *                                  if the latitude isn't in the range of [-PI / 2 to PI / 2]
     */
    public GeoPoint(double longitude, double latitude) throws IllegalArgumentException
    {
        Preconditions.checkArgument((-PI <= longitude && longitude <= PI) && (-PI / 2 <= latitude && latitude <= PI / 2));
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * getter of the class for the longitude attribute
     *
     * @return the longitude of the point
     */
    public double longitude()
    {
        return longitude;
    }

    /**
     * getter of the class for the latitude attribute
     *
     * @return the latitude of the point
     */
    public double latitude()
    {
        return latitude;
    }

    /**
     * calculates the distance between two points
     *
     * @param that the point to which the distance will be calculated
     * @return the distance between the two points
     */
    public double distanceTo(GeoPoint that)
    {
        return Distance.toMeters(Azimuth.canonicalize(2 * asin(sqrt(Math2.haversin(this.latitude() - that.latitude()) + cos(this.latitude()) * cos(that.latitude()) * Math2.haversin(this.longitude() - that.longitude())))));
    }

    /**
     * calculates the azimuth angle between two points
     *
     * @param that the point to which the azimuth angle will be calculated
     * @return the azimuth angle between the two points
     */
    public double azimuthTo(GeoPoint that)
    {
        return Azimuth.fromMath(Azimuth.canonicalize(atan2(sin(this.longitude() - that.longitude()) * cos(that.latitude()), (cos(this.latitude()) * sin(that.latitude()) - sin(this.latitude()) * cos(that.latitude()) * cos(this.longitude() - that.longitude())))));
    }

    /**
     * returns the coordinates of the point as a string of a specific format
     *
     * @return the string that describes the coordinates
     */
    @Override
    public String toString()
    {
        double longitudeInDeg = Math.toDegrees(longitude());
        double latitudeInDeg = Math.toDegrees(latitude());
        return String.format("(%.4f,%.4f)", longitudeInDeg, latitudeInDeg);
    }
}
