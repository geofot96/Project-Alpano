package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval1D;
import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.Preconditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;

/**
 * Describes a discrete Elevation Model with data provided by some .hgt files
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class HgtDiscreteElevationModel implements DiscreteElevationModel
{
    private final String filename;
    private ShortBuffer shortBuffer;
    private final Interval2D demExtent;

    /**
     * constructor of the class, creates a DEM based on some data from a file
     *
     * @param file the file from which the data will be retrieved
     * @throws IllegalArgumentException if the name of the file is invalid or of it's length is not proper
     */
    public HgtDiscreteElevationModel(File file) throws IllegalArgumentException
    {
        Preconditions.checkArgument(file.length() == 2 * (SAMPLES_PER_DEGREE + 1) * (SAMPLES_PER_DEGREE + 1),
                "File invalid : bad length");
        Preconditions.checkArgument((filename = file.getName()).matches(formatSpecifier()));

        try(FileInputStream fInStream = new FileInputStream(file))
        {
            shortBuffer = fInStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length()).asShortBuffer();
        }
        catch(IOException e)
        {
            throw new IllegalArgumentException();
        }
        demExtent = new Interval2D(new Interval1D(getLon() * SAMPLES_PER_DEGREE,
                (getLon() + 1) * SAMPLES_PER_DEGREE),
                new Interval1D(getLat() * SAMPLES_PER_DEGREE,
                        (getLat() + 1) * SAMPLES_PER_DEGREE));
    }

    /**
     * Provides the domain on which the DEM has data
     *
     * @return A two dimensional integer interval whose bounds are the same than the extent of the DEM
     */
    @Override
    public Interval2D extent()
    {
        return demExtent;
    }

    /**
     * Provides the altitude at a given point
     *
     * @param x index of the point whose altitude is asked (on SampleIndex)
     * @param y index of the point whose altitude is asked (on SampleIndex)
     * @return the altitude at the input point
     * @throws IllegalArgumentException if the DEM has no data for the input point
     */
    @Override
    public double elevationSample(int x, int y) throws IllegalArgumentException
    {
        Preconditions.checkArgument(x >= demExtent.iX().includedFrom() && x <= demExtent.iX().includedTo());
        Preconditions.checkArgument(y >= demExtent.iY().includedFrom() && y <= demExtent.iY().includedTo());
        return shortBuffer.get(((demExtent.iY().includedTo()) - y) * (SAMPLES_PER_DEGREE + 1) + x - demExtent.iX().includedFrom());
    }

    @Override
    public void close()
    {
        shortBuffer = null;
    }

    /**
     * getter for the longitude attribute
     *
     * @return the longitude
     */
    private int getLon()
    {
        return Integer.valueOf(filename.substring(4, 7)) * getSignLon();
    }

    /**
     * getter for the latitude attribute
     *
     * @return the latitude
     */
    private int getLat()
    {
        return Integer.valueOf(filename.substring(1, 3)) * getSignLat();
    }

    /**
     * calculates the sign of the longitude based on the direction North or South
     *
     * @return 1 for positive and -1 for negative
     * @throws IllegalArgumentException if the direction is not North nor South
     */
    private int getSignLon() throws IllegalArgumentException
    {
        if(getSign("lon") == 'N')
            return 1;
        if(getSign("lon") == 'S')
            return -1;
        throw new IllegalArgumentException();
    }

    /**
     * calculates the sign of the latitude based on the direction East or West
     *
     * @return 1 for positive and -1 for negative
     * @throws IllegalArgumentException if the direction is not East nor West
     */
    private int getSignLat() throws IllegalArgumentException
    {
        if(getSign("lat") == 'E')
            return 1;
        if(getSign("lat") == 'W')
            return -1;
        throw new IllegalArgumentException();
    }

    /**
     * calculates the signs for longitude or latitude, depending on the input
     *
     * @param latOrLon specifies if the sign will be calculated for the longitude or the latitude
     * @return the direction of the longitude or latitude
     * @throws IllegalArgumentException if the input isn't the longitude nor the latitude
     */
    private char getSign(String latOrLon) throws IllegalArgumentException
    {
        if(latOrLon.equals("lat"))
            return filename.charAt(3);
        if(latOrLon.equals("lon"))
            return filename.charAt(0);
        throw new IllegalArgumentException();
    }

    /**
     * Specifies the required pattern for the filename
     *
     * @return the required format for the filename
     */
    private String formatSpecifier()
    {
        return "(N|S)(\\d)(\\d)(E|W)(\\d)(\\d)(\\d).hgt";
    }
}
