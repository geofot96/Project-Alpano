package ch.epfl.alpano.summit;

import ch.epfl.alpano.GeoPoint;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates a list of summits read from a file
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class GazetteerParser
{
    /**
     * Private empty constructor to avoid inheritance from this class.
     */
    private GazetteerParser()
    {
    }

    /**
     * @param file input Summit file to convert into a list containing : Summits @see Summit
     * @return An unmodifiable list containing the summits listed in the input file.
     * @throws IOException if an error occurs during the opening of the file or
     *                     if the file is not in the right format
     */
    public static List<Summit> readSummitsFrom(File file) throws IOException
    {
        double lat, lon;
        int elevation;
        String line;
        AbstractList<Summit> summitList = new ArrayList<>();


        try(BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.US_ASCII)))
        {
            while((line = buffRead.readLine()) != null)
            {
                //checks for : presence
                checkColumns(line);
                lat = getLat(line);
                lon = getLon(line);

                //elevation
                elevation = (int) checkNumber(line.substring(20, 25), 4);

                if(line.length() <= 36)
                    throw new IOException("Invalid line : name empty");

                summitList.add(new Summit(line.substring(36), new GeoPoint(lon, lat), elevation));
            }
        }
        return Collections.unmodifiableList(summitList);
    }


    /**
     * checks whether the columns are present for latitude and longitude
     *
     * @param line line in which the columns presence is to be checked
     * @throws IOException if the columns are not at the expected place
     */
    private static void checkColumns(String line) throws IOException
    {
        checkColumn(line.charAt(3));
        checkColumn(line.charAt(6));
        checkColumn(line.charAt(12));
        checkColumn(line.charAt(15));
    }

    /**
     * infers the latitude from a given string
     *
     * @param line String from which the latitude is inferred
     * @return the latitude corresponding to the input line
     * @throws IOException if the string does not contain a
     */
    private static double getLon(String line) throws IOException
    {
        String[] dms = line.substring(0, 9).split(":");
        if(dms.length != 3)
            throw new IOException("too many :");
        return Math.toRadians(checkNumber(dms[0], 3) + checkNumber(dms[1], 2) / 60 + checkNumber(dms[2], 2) / 3600);
    }

    /**
     * infers the longitude from a given string
     *
     * @param line String from which the longitude is inferred
     * @return the latitude corresponding to the input line
     * @throws IOException if there are not exactly three substrings
     */
    private static double getLat(String line) throws IOException
    {
        String[] dms = line.substring(10, 19).split(":");
        return Math.toRadians(checkNumber(dms[0], 2) + checkNumber(dms[1], 2) / 60 + checkNumber(dms[2], 2) / 3600);
    }

    /**
     * checks whether a given String is below a x-digits number and returns the number
     *
     * @param strToCheck String from which the number is inferred
     * @param digits     max size of the number to check
     * @return the number inferred for the string
     * @throws IOException if the number length is not the expected one
     */
    private static double checkNumber(String strToCheck, int digits) throws IOException
    {
        strToCheck = strToCheck.trim();
        if(strToCheck.length() > digits)
            throw new IOException("invalid number : length checked");
        try
        {
            return (double) Integer.parseInt(strToCheck);
        }
        catch(NumberFormatException e)
        {
            throw new IOException("Not a number");
        }
    }

    /**
     * Checks whether a given character is a column
     *
     * @param charToCheck char to check
     * @throws IOException if the character is not a column
     */
    private static void checkColumn(char charToCheck) throws IOException
    {
        if(charToCheck != ':')
            throw new IOException("not a \":\" ");
    }
}
