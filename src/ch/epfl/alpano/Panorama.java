package ch.epfl.alpano;

import java.util.Objects;

import static java.util.Arrays.fill;

/**
 * Represents a panorama
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class Panorama
{
    private final PanoramaParameters parameters;
    private final float[] distances, longitudes, latitudes, elevations, slopes;

    /**
     * constructor of the class
     *
     * @param parameters the parameters of the panorama
     * @param distances  distances of a given set of points
     * @param longitudes longitudes of a given set of points
     * @param latitudes  latitudes of a given set of points
     * @param elevations elevations of a given set of points
     * @param slopes     slopes of a given set of points
     */
    private Panorama(PanoramaParameters parameters, float[] distances, float[] longitudes, float[] latitudes, float[] elevations, float[] slopes)
    {
        this.parameters = parameters;
        this.distances = distances;
        this.longitudes = longitudes;
        this.latitudes = latitudes;
        this.elevations = elevations;
        this.slopes = slopes;
    }

    /**
     * getter of the class for the panoramas attributes
     *
     * @return the panoramas parameters
     */
    public PanoramaParameters parameters()
    {
        return parameters;
    }

    /**
     * getter of the class for the distance of a specific point whose index is given
     *
     * @param x coordinate of the index of the point in width
     * @param y coordinate of the index of the point in height
     * @return the distance of the point
     * @throws IndexOutOfBoundsException if the point passes the bounds of the panoramas
     */
    public float distanceAt(int x, int y)
    {
        checkValidIndex(x, y);
        return distances[parameters.linearSampleIndex(x, y)];
    }

    /**
     * getter of the class for the distance of a specific point whose index is given
     * that does not throw an exception if the point passes the bounds of the panoramas
     * but returns a default value instead
     *
     * @param x first part of the index of the point
     * @param y second part of the index of the point
     * @param d the default distance returned in case the point is not part of the panorama
     * @return the distance of the point
     */
    public float distanceAt(int x, int y, float d)
    {
        if(parameters.isValidSampleIndex(x, y))
            return distances[parameters.linearSampleIndex(x, y)];
        return d;
    }

    /**
     * getter of the class for the longitude of a specific point whose index is given
     *
     * @param x first part of the index of the point
     * @param y second part of the index of the point
     * @return the longitude of the point
     * @throws IndexOutOfBoundsException if the point passes the bounds of the panoramas
     */
    public float longitudeAt(int x, int y)
    {
        checkValidIndex(x, y);
        return longitudes[parameters.linearSampleIndex(x, y)];
    }

    /**
     * getter of the class for the latitude of a specific point whose index is given
     *
     * @param x first part of the index of the point
     * @param y second part of the index of the point
     * @return the latitude of the point
     * @throws IndexOutOfBoundsException if the point passes the bounds of the panoramas
     */
    public float latitudeAt(int x, int y)
    {
        checkValidIndex(x, y);
        return latitudes[parameters.linearSampleIndex(x, y)];
    }

    /**
     * getter of the class for the slope of a specific point whose index is given
     *
     * @param x first part of the index of the point
     * @param y second part of the index of the point
     * @return the slope of the point
     * @throws IndexOutOfBoundsException if the point passes the bounds of the panoramas
     */
    public float elevationAt(int x, int y)
    {
        checkValidIndex(x, y);
        return elevations[parameters.linearSampleIndex(x, y)];
    }

    /**
     * getter of the class for the elevation of a specific point whose index is given
     *
     * @param x first part of the index of the point
     * @param y second part of the index of the point
     * @return the elevation of the point
     * @throws IndexOutOfBoundsException if the point passes the bounds of the panoramas
     */
    public float slopeAt(int x, int y)
    {
        checkValidIndex(x, y);
        return slopes[parameters.linearSampleIndex(x, y)];
    }

    private void checkValidIndex(int x, int y)
    {
        if(!parameters.isValidSampleIndex(x, y))
            throw new IndexOutOfBoundsException();
    }

    /**
     * Builds a panorama
     *
     * @author Georgios Fotiadis (271875)
     * @author Clement Charollais (264231)
     */
    public static class Builder
    {
        private boolean buildAlreadyCalled = false;
        private final PanoramaParameters parameters;
        private float[] distances, longitudes, latitudes, elevations, slopes;

        /**
         * constructor of the class
         *
         * @param parameters the parameters of the panorama to be built
         */
        public Builder(PanoramaParameters parameters)
        {
            int tableSize = parameters.width() * parameters.height();

            this.parameters = Objects.requireNonNull(parameters);
            distances = new float[tableSize];
            longitudes = new float[tableSize];
            latitudes = new float[tableSize];
            elevations = new float[tableSize];
            slopes = new float[tableSize];

            fill(distances, Float.POSITIVE_INFINITY);
        }

        /**
         * setter of the class for the distance of a specific point whose index is given
         *
         * @param x        the first part of the index of the point
         * @param y        the second part of the index of the point
         * @param distance the value of the new distance
         * @throws IllegalStateException     if the panorama has been build already
         * @throws IndexOutOfBoundsException if the index provided is not is not in the panorama
         */
        public Builder setDistanceAt(int x, int y, float distance)
        {
            checkBuilt();
            checkValidIndex(x, y);
            distances[parameters.linearSampleIndex(x, y)] = distance;
            return this;
        }

        /**
         * setter of the class for the longitude of a specific point whose index is given
         *
         * @param x         the first part of the index of the point
         * @param y         the second part of the index of the point
         * @param longitude the value of the new longitude
         * @throws IllegalStateException     if the panorama has been build already
         * @throws IndexOutOfBoundsException if the index provided is not is not in the panorama
         */
        public Builder setLongitudeAt(int x, int y, float longitude)
        {
            checkBuilt();
            checkValidIndex(x, y);
            longitudes[parameters.linearSampleIndex(x, y)] = longitude;
            return this;
        }

        /**
         * setter of the class for the latitude of a specific point whose index is given
         *
         * @param x        the first part of the index of the point
         * @param y        the second part of the index of the point
         * @param latitude the value of the new latitude
         * @throws IllegalStateException     if the panorama has been build already
         * @throws IndexOutOfBoundsException if the index provided is not is not in the panorama
         */
        public Builder setLatitudeAt(int x, int y, float latitude)
        {
            checkBuilt();
            checkValidIndex(x, y);
            latitudes[parameters.linearSampleIndex(x, y)] = latitude;
            return this;
        }

        /**
         * setter of the class for the elevation of a specific point whose index is given
         *
         * @param x         the first part of the index of the point
         * @param y         the second part of the index of the point
         * @param elevation the value of the new elevation
         * @throws IllegalStateException     if the panorama has been build already
         * @throws IndexOutOfBoundsException if the index provided is not is not in the panorama
         */
        public Builder setElevationAt(int x, int y, float elevation)
        {
            checkBuilt();
            checkValidIndex(x, y);
            elevations[parameters.linearSampleIndex(x, y)] = elevation;
            return this;
        }

        /**
         * setter of the class for the slope of a specific point whose index is given
         *
         * @param x     the first part of the index of the point
         * @param y     the second part of the index of the point
         * @param slope the value of the new slope
         * @throws IllegalStateException     if the panorama has been build already
         * @throws IndexOutOfBoundsException if the index provided is not is not in the panorama
         */
        public Builder setSlopeAt(int x, int y, float slope)
        {
            checkBuilt();
            checkValidIndex(x, y);
            slopes[parameters.linearSampleIndex(x, y)] = slope;
            return this;
        }

        /**
         * the method to build the panorama but only once
         *
         * @return the new panorama
         * @throws IllegalStateException if the panorama has been build already
         */
        public Panorama build()
        {
            checkBuilt();
            buildAlreadyCalled = true;
            Panorama panorama = new Panorama(parameters, distances, longitudes, latitudes, elevations, slopes);
            distances = null;
            longitudes = null;
            latitudes = null;
            elevations = null;
            elevations = null;
            slopes = null;
            return panorama;
        }

        private void checkBuilt()
        {
            if(buildAlreadyCalled)
                throw new IllegalStateException();
        }

        private void checkValidIndex(int x, int y)
        {
            if(!parameters.isValidSampleIndex(x, y))
                throw new IndexOutOfBoundsException();
        }
    }
}