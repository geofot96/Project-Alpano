package ch.epfl.alpano.gui;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.PanoramaParameters;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a Panorama Parameter.
 * Used to make the link between the panorama parameters and the user interface.
 * <p>
 * All values are stored in integers:<br/>
 * - latitude, longitude in ten-thousandth of degrees<br/>
 * - center azimuth, Horizontal field of view in degrees<br/>
 * - elevation in meters<br/>
 * - maximum distance in kilometers<br/>
 * - height and width of the panorama in pixels<br/>
 * - supersampling exponent : 0, 1, or 2<br/>
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 * @see <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ"> More info on supersampling here </a>.
 */
public final class PanoramaUserParameters
{
    private Map<UserParameters, Integer> params;

    /**
     * Constructor for the panorama user parameters
     *
     * @param params Enum map containing the values of the panorama user parameters to be.
     */
    public PanoramaUserParameters(EnumMap<UserParameters, Integer> params)
    {
        Map<UserParameters, Integer> paramTemp = new EnumMap<>(params);
        paramTemp.put(UserParameters.OBSERVER_LONGITUDE, UserParameters.OBSERVER_LONGITUDE.sanitize(params.get(UserParameters.OBSERVER_LONGITUDE)));
        paramTemp.put(UserParameters.OBSERVER_LATITUDE, UserParameters.OBSERVER_LATITUDE.sanitize(params.get(UserParameters.OBSERVER_LATITUDE)));
        paramTemp.put(UserParameters.OBSERVER_ELEVATION, UserParameters.OBSERVER_ELEVATION.sanitize(params.get(UserParameters.OBSERVER_ELEVATION)));
        paramTemp.put(UserParameters.CENTER_AZIMUTH, UserParameters.CENTER_AZIMUTH.sanitize(params.get(UserParameters.CENTER_AZIMUTH)));
        paramTemp.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, UserParameters.HORIZONTAL_FIELD_OF_VIEW.sanitize(params.get(UserParameters.HORIZONTAL_FIELD_OF_VIEW)));
        paramTemp.put(UserParameters.MAX_DISTANCE, UserParameters.MAX_DISTANCE.sanitize(params.get(UserParameters.MAX_DISTANCE)));

        paramTemp.put(UserParameters.WIDTH, UserParameters.WIDTH.sanitize(params.get(UserParameters.WIDTH)));

        if((paramTemp.get(UserParameters.HEIGHT) - 1) / (paramTemp.get(UserParameters.WIDTH) - 1) * paramTemp.get(UserParameters.HORIZONTAL_FIELD_OF_VIEW) > 170)
            paramTemp.put(UserParameters.HEIGHT, UserParameters.HEIGHT.sanitize(170 * (paramTemp.get(UserParameters.WIDTH) - 1) / paramTemp.get(UserParameters.HORIZONTAL_FIELD_OF_VIEW) + 1));
        else
            paramTemp.put(UserParameters.HEIGHT, UserParameters.HEIGHT.sanitize(paramTemp.get(UserParameters.HEIGHT)));
        paramTemp.put(UserParameters.SUPER_SAMPLING_EXPONENT, UserParameters.SUPER_SAMPLING_EXPONENT.sanitize(params.get(UserParameters.SUPER_SAMPLING_EXPONENT)));
        this.params = Collections.unmodifiableMap(paramTemp);
    }

    /**
     * Constructor for the panorama user parameters
     *
     * @param usrObsLongitude          value for the longitude (in ten-thousandth of degrees)
     * @param usrObsLatitude           value for the latitude (in ten-thousandth of degrees)
     * @param usrObsElevation          value for the elevation (in meters)
     * @param usrCenterAzimuth         value for the center azimuth (in degrees)
     * @param usrHorizontalView        value for the horizontal field of view (in degrees)
     * @param usrMaxDistance           value for the max distance (in kilometers)
     * @param usrPanoramaWidth         value for the panorama width (in pixels)
     * @param usrPanoramaHeight        value for the panorama height (in pixels)
     * @param usrSupersamplingExponent value for the supersampling exponent (0, 1, or 2)
     */
    public PanoramaUserParameters(int usrObsLongitude, int usrObsLatitude, int usrObsElevation, int usrCenterAzimuth,
                                  int usrHorizontalView, int usrMaxDistance, int usrPanoramaWidth, int usrPanoramaHeight,
                                  int usrSupersamplingExponent)
    {
        this(putter(new EnumMap<>(UserParameters.class), usrObsLongitude, usrObsLatitude,
                usrObsElevation, usrCenterAzimuth, usrHorizontalView,
                usrMaxDistance, usrPanoramaWidth, usrPanoramaHeight, usrSupersamplingExponent));
    }


    /**
     * Getter for a certain attribute of the panorama user parameter
     *
     * @param attribute Attribute whose value is needed
     * @return the value of the attribiute which is asked
     */
    public int get(UserParameters attribute)
    {
        return params.get(attribute);
    }

    /**
     * getter for the observer longitude attribute value
     *
     * @return value for the longitude (in ten-thousandth of degrees)
     */
    public int obsLongitude()
    {
        return get(UserParameters.OBSERVER_LONGITUDE);
    }

    /**
     * getter for the observer latitude attribute value
     *
     * @return value for the latitude (in ten-thousandth of degrees)
     */
    public int obsLatitude()
    {
        return get(UserParameters.OBSERVER_LATITUDE);
    }

    /**
     * getter for the observer elevation attribute value
     *
     * @return value for the elevation (in meters)
     */
    public int obsElevation()
    {
        return get(UserParameters.OBSERVER_ELEVATION);
    }

    /**
     * getter for the center azimuth attribute value
     *
     * @return value for the center azimuth (in degrees)
     */
    public int centerAzimuth()
    {
        return get(UserParameters.CENTER_AZIMUTH);
    }

    /**
     * getter for the horizontal field of view attribute value
     *
     * @return value for the horizontal field of view (in degrees)
     */
    public int horizontalFieldOfView()
    {
        return get(UserParameters.HORIZONTAL_FIELD_OF_VIEW);
    }

    /**
     * getter for the maximum visible distance attribute value
     *
     * @return value for the max distance (in kilometers)
     */
    public int maxDistance()
    {
        return get(UserParameters.MAX_DISTANCE);
    }

    /**
     * getter for the panorama width attribute value
     *
     * @return value for the panorama width (in pixels)
     */
    public int panoramaWidth()
    {
        return get(UserParameters.WIDTH);
    }

    /**
     * getter for the panorama height attribute value
     *
     * @return value for the panorama height (in pixels)
     */
    public int panoramaHeight()
    {
        return get(UserParameters.HEIGHT);
    }

    /**
     * getter for the super sampling exponent attribute value
     *
     * @return value for the supersampling exponent (0, 1, or 2)
     */
    public int supersamplingExponent()
    {
        return get(UserParameters.SUPER_SAMPLING_EXPONENT);
    }

    /**
     * Constructs a PanoramaParameter representing the same panorama as the current instance,
     * taking into account the supersampling, this is used when computing the panorama.
     *
     * @return a PanoramaParameter representing the same panorama as the current instance
     * taking into account the supersampling.
     */
    public PanoramaParameters panoramaParameters()
    {
        return new PanoramaParameters(new GeoPoint(Math.toRadians((double) get(UserParameters.OBSERVER_LONGITUDE) / 10000), Math.toRadians((double) get(UserParameters.OBSERVER_LATITUDE) / 10000)),
                get(UserParameters.OBSERVER_ELEVATION),
                Math.toRadians(get(UserParameters.CENTER_AZIMUTH)),
                Math.toRadians(get(UserParameters.HORIZONTAL_FIELD_OF_VIEW)),
                get(UserParameters.MAX_DISTANCE) * 1000,
                get(UserParameters.WIDTH) << get(UserParameters.SUPER_SAMPLING_EXPONENT),
                get(UserParameters.HEIGHT) << get(UserParameters.SUPER_SAMPLING_EXPONENT));
    }

    /**
     * Constructs a PanoramaParameter representing the same panorama as the current instance,
     * as it will be displayed. This is used when displaying the panorama of its parameters.
     *
     * @return a PanoramaParameter representing the same panorama as the current instance,
     * as it will be displayed.
     */
    public PanoramaParameters displayedPanoramaParameters()
    {
        return new PanoramaParameters(new GeoPoint(Math.toRadians((double) get(UserParameters.OBSERVER_LONGITUDE) / 10000), Math.toRadians((double) get(UserParameters.OBSERVER_LATITUDE) / 10000)),
                get(UserParameters.OBSERVER_ELEVATION),
                Math.toRadians(get(UserParameters.CENTER_AZIMUTH)),
                Math.toRadians(get(UserParameters.HORIZONTAL_FIELD_OF_VIEW)),
                get(UserParameters.MAX_DISTANCE) * 1000,
                get(UserParameters.WIDTH),
                get(UserParameters.HEIGHT));
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(!(o instanceof PanoramaUserParameters))
            return false;

        PanoramaUserParameters that = (PanoramaUserParameters) o;

        return params != null ?
                params.equals(that.params) :
                that.params == null;
    }

    @Override
    public int hashCode()
    {
        return params != null ? params.hashCode() : 0;
    }


    private static EnumMap<UserParameters, Integer> putter(EnumMap<UserParameters, Integer> temp, int usrObsLongitude,
                                                           int usrObsLatitude, int usrObsElevation, int usrCenterAzimuth,
                                                           int usrHorizontalView, int usrMaxDistance, int usrPanoramaWidth,
                                                           int usrPanoramaHeight, int usrSupersamplingExponent)
    {
        temp.put(UserParameters.OBSERVER_LONGITUDE, UserParameters.OBSERVER_LONGITUDE.sanitize(usrObsLongitude));
        temp.put(UserParameters.OBSERVER_LATITUDE, UserParameters.OBSERVER_LATITUDE.sanitize(usrObsLatitude));
        temp.put(UserParameters.OBSERVER_ELEVATION, UserParameters.OBSERVER_ELEVATION.sanitize(usrObsElevation));
        temp.put(UserParameters.CENTER_AZIMUTH, UserParameters.CENTER_AZIMUTH.sanitize(usrCenterAzimuth));
        temp.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, UserParameters.HORIZONTAL_FIELD_OF_VIEW.sanitize(usrHorizontalView));
        temp.put(UserParameters.MAX_DISTANCE, UserParameters.MAX_DISTANCE.sanitize(usrMaxDistance));
        temp.put(UserParameters.WIDTH, UserParameters.WIDTH.sanitize(usrPanoramaWidth));
        if((usrPanoramaHeight - 1) / (usrPanoramaWidth - 1) * usrHorizontalView > 170)
            temp.put(UserParameters.HEIGHT, UserParameters.HEIGHT.sanitize(170 * (usrPanoramaWidth - 1) / usrHorizontalView + 1));
        else
            temp.put(UserParameters.HEIGHT, UserParameters.HEIGHT.sanitize(usrPanoramaHeight));
        temp.put(UserParameters.SUPER_SAMPLING_EXPONENT, UserParameters.SUPER_SAMPLING_EXPONENT.sanitize(usrSupersamplingExponent));
        return temp;
    }
}
