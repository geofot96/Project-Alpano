package ch.epfl.alpano.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Platform.runLater;

/**
 * javaFX bean containing the user parameters
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class PanoramaParametersBean
{
    private ObjectProperty<PanoramaUserParameters> parametersProperty;

    private Map<UserParameters, ObjectProperty<Integer>> parameterMap;

    /**
     * constructor of the class, instantiates the bean's attributes
     *
     * @param parametersProperty the current user's parameters
     */
    public PanoramaParametersBean(PanoramaUserParameters parametersProperty)
    {
        parameterMap = new HashMap<>();
        this.parametersProperty = new SimpleObjectProperty<>(parametersProperty);

        for(UserParameters k : UserParameters.values())
        {
            parameterMap.put(k, new SimpleObjectProperty<>(parametersProperty.get(k)));
        }

        for(UserParameters k : UserParameters.values())
        {
            parameterMap.get(k).addListener((prop, oldVal, newVal) -> runLater(this::synchronizeParameters));
        }
    }

    /**
     * getter for the parameters property
     *
     * @return the parameters property
     */
    public ReadOnlyObjectProperty<PanoramaUserParameters> parametersProperty()
    {
        return parametersProperty;
    }

    /**
     * getter for the longitude property
     *
     * @return the longitude property
     */
    public ObjectProperty<Integer> observerLongitudeProperty()
    {
        return parameterMap.get(UserParameters.OBSERVER_LONGITUDE);
    }

    /**
     * getter for the latitude property
     *
     * @return the latitude property
     */
    public ObjectProperty<Integer> observerLatitudeProperty()
    {
        return parameterMap.get(UserParameters.OBSERVER_LATITUDE);
    }

    /**
     * getter for the elevation property
     *
     * @return the elevation property
     */
    public ObjectProperty<Integer> observerElevationProperty()
    {
        return parameterMap.get(UserParameters.OBSERVER_ELEVATION);
    }

    /**
     * getter for the center azimuth property
     *
     * @return the center azimuth property
     */
    public ObjectProperty<Integer> centerAzimuthProperty()
    {
        return parameterMap.get(UserParameters.CENTER_AZIMUTH);
    }

    /**
     * getter for the horizontal field of view property
     *
     * @return the horizontal field of view property
     */
    public ObjectProperty<Integer> horizontalFieldOfViewProperty()
    {
        return parameterMap.get(UserParameters.HORIZONTAL_FIELD_OF_VIEW);
    }

    /**
     * getter for the maximum distance property
     *
     * @return the maximum distance property
     */
    public ObjectProperty<Integer> maxDistanceProperty()
    {
        return parameterMap.get(UserParameters.MAX_DISTANCE);
    }

    /**
     * getter for the width property
     *
     * @return the width property
     */
    public ObjectProperty<Integer> widthProperty()
    {
        return parameterMap.get(UserParameters.WIDTH);
    }

    /**
     * getter for the height property
     *
     * @return the height property
     */
    public ObjectProperty<Integer> heightProperty()
    {
        return parameterMap.get(UserParameters.HEIGHT);
    }

    /**
     * getter for the super-sampling property
     *
     * @return the super-sampling property
     */
    public ObjectProperty<Integer> superSamplingExponentProperty()
    {
        return parameterMap.get(UserParameters.SUPER_SAMPLING_EXPONENT);
    }

    private void synchronizeParameters()
    {
        EnumMap<UserParameters, Integer> mymap = new EnumMap<>(UserParameters.class);

        for(UserParameters k : UserParameters.values())
        {
            mymap.put(k, parameterMap.get(k).get());
        }

        PanoramaUserParameters parameters = new PanoramaUserParameters(mymap);

        for(UserParameters k : UserParameters.values())
        {
            parameterMap.get(k).set(parameters.get(k));
        }

        this.parametersProperty.set(parameters);
    }
}
