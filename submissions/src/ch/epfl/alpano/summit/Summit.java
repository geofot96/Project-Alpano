package ch.epfl.alpano.summit;

import ch.epfl.alpano.GeoPoint;

import java.util.Objects;

/**
 * Class to represent a summit(name, position, elevation)
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class Summit
{
    private final String name;
    private final GeoPoint position;
    private final int elevation;

    /**
     * Creates an instance os a summit, containing its name, position, and elevation
     *
     * @param name      name of the summit
     * @param position  position of the summit
     * @param elevation elevation of the summit
     * @throws NullPointerException if name or position is null
     */
    public Summit(String name, GeoPoint position, int elevation) throws IllegalArgumentException, NullPointerException
    {
        this.name = Objects.requireNonNull(name);
        this.position = Objects.requireNonNull(position);
        this.elevation = elevation;
    }

    /**
     * getter for the name of the summit
     *
     * @return the name of the summit
     */
    public String name()
    {
        return name;
    }

    /**
     * getter for the position of the summit
     *
     * @return the position of the summit
     */
    public GeoPoint position()
    {
        return position;
    }

    /**
     * getter for the elevation of the summit
     *
     * @return the elevation of the summit
     */
    public int elevation()
    {
        return elevation;
    }

    @Override
    public String toString()
    {
        return name + " " + position + " " + elevation;
    }
}
