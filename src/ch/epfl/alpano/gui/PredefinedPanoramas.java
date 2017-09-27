package ch.epfl.alpano.gui;

import java.util.EnumMap;

/**
 * Container of predefined panoramas
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public interface PredefinedPanoramas
{

    PanoramaUserParameters NIESEN = NIESEN();
    PanoramaUserParameters JURA_ALPS = JURA_ALPS();
    PanoramaUserParameters MOUNT_RACINE = MOUNT_RACINE();
    PanoramaUserParameters FINSTERAARHORN = FINSTERAARHORN();
    PanoramaUserParameters SAUVABLIN_TOWER = SAUVABLIN_TOWER();
    PanoramaUserParameters PELICAN_BEACH = PELICAN_BEACH();


    /**
     * Panorama representing the Niesen summit.
     *
     * @return a PredefinedPanorama representing the Niesen summit.
     */
    static PanoramaUserParameters NIESEN()
    {
        EnumMap<UserParameters, Integer> params = new EnumMap<>(UserParameters.class);
        params.put(UserParameters.OBSERVER_LONGITUDE, 76500);
        params.put(UserParameters.OBSERVER_LATITUDE, 467300);
        params.put(UserParameters.OBSERVER_ELEVATION, 600);
        params.put(UserParameters.CENTER_AZIMUTH, 180);
        params.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, 110);
        params.put(UserParameters.HEIGHT, 800);
        params.put(UserParameters.WIDTH, 2500);
        params.put(UserParameters.MAX_DISTANCE, 300);
        params.put(UserParameters.SUPER_SAMPLING_EXPONENT, 0);
        return new PanoramaUserParameters(params);
    }

    /**
     * Panorama representing the Jura Alps.
     *
     * @return a PredefinedPanorama representing the Jura Alps.
     */
    static PanoramaUserParameters JURA_ALPS()
    {
        EnumMap<UserParameters, Integer> params = new EnumMap<>(UserParameters.class);
        params.put(UserParameters.OBSERVER_LONGITUDE, 68087);
        params.put(UserParameters.OBSERVER_LATITUDE, 470085);
        params.put(UserParameters.OBSERVER_ELEVATION, 1380);
        params.put(UserParameters.CENTER_AZIMUTH, 162);
        params.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, 27);
        params.put(UserParameters.HEIGHT, 800);
        params.put(UserParameters.WIDTH, 2500);
        params.put(UserParameters.MAX_DISTANCE, 300);
        params.put(UserParameters.SUPER_SAMPLING_EXPONENT, 0);
        return new PanoramaUserParameters(params);
    }

    /**
     * Panorama representing the mount Racine.
     *
     * @return a PredefinedPanorama representing the mount Racine.
     */
    static PanoramaUserParameters MOUNT_RACINE()
    {
        EnumMap<UserParameters, Integer> params = new EnumMap<>(UserParameters.class);
        params.put(UserParameters.OBSERVER_LONGITUDE, 68200);
        params.put(UserParameters.OBSERVER_LATITUDE, 470200);
        params.put(UserParameters.OBSERVER_ELEVATION, 1500);
        params.put(UserParameters.CENTER_AZIMUTH, 135);
        params.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, 45);
        params.put(UserParameters.HEIGHT, 800);
        params.put(UserParameters.WIDTH, 2500);
        params.put(UserParameters.MAX_DISTANCE, 300);
        params.put(UserParameters.SUPER_SAMPLING_EXPONENT, 0);
        return new PanoramaUserParameters(params);
    }

    /**
     * Panorama representing the Finsteraarhorn.
     *
     * @return a PredefinedPanorama representing the Finsteraarhorn.
     */
    static PanoramaUserParameters FINSTERAARHORN()
    {
        EnumMap<UserParameters, Integer> params = new EnumMap<>(UserParameters.class);
        params.put(UserParameters.OBSERVER_LONGITUDE, 81260);
        params.put(UserParameters.OBSERVER_LATITUDE, 465374);
        params.put(UserParameters.OBSERVER_ELEVATION, 4300);
        params.put(UserParameters.CENTER_AZIMUTH, 205);
        params.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, 20);
        params.put(UserParameters.HEIGHT, 800);
        params.put(UserParameters.WIDTH, 2500);
        params.put(UserParameters.MAX_DISTANCE, 300);
        params.put(UserParameters.SUPER_SAMPLING_EXPONENT, 0);
        return new PanoramaUserParameters(params);
    }

    /**
     * Panorama representing the Sauvablin Tower.
     *
     * @return a PredefinedPanorama representing the Sauvablin Tower.
     */
    static PanoramaUserParameters SAUVABLIN_TOWER()
    {
        EnumMap<UserParameters, Integer> params = new EnumMap<>(UserParameters.class);
        params.put(UserParameters.OBSERVER_LONGITUDE, 66385);
        params.put(UserParameters.OBSERVER_LATITUDE, 465353);
        params.put(UserParameters.OBSERVER_ELEVATION, 700);
        params.put(UserParameters.CENTER_AZIMUTH, 135);
        params.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, 100);
        params.put(UserParameters.HEIGHT, 800);
        params.put(UserParameters.WIDTH, 2500);
        params.put(UserParameters.MAX_DISTANCE, 300);
        params.put(UserParameters.SUPER_SAMPLING_EXPONENT, 0);
        return new PanoramaUserParameters(params);
    }

    /**
     * Panorama representing the Pelican beach.
     *
     * @return a PredefinedPanorama representing the Pelican beach.
     */
    static PanoramaUserParameters PELICAN_BEACH()
    {
        EnumMap<UserParameters, Integer> params = new EnumMap<>(UserParameters.class);
        params.put(UserParameters.OBSERVER_LONGITUDE, 65728);
        params.put(UserParameters.OBSERVER_LATITUDE, 465132);
        params.put(UserParameters.OBSERVER_ELEVATION, 380);
        params.put(UserParameters.CENTER_AZIMUTH, 135);
        params.put(UserParameters.HORIZONTAL_FIELD_OF_VIEW, 60);
        params.put(UserParameters.HEIGHT, 800);
        params.put(UserParameters.WIDTH, 2500);
        params.put(UserParameters.MAX_DISTANCE, 300);
        params.put(UserParameters.SUPER_SAMPLING_EXPONENT, 0);
        return new PanoramaUserParameters(params);
    }
}
