package my_tests.etape5;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.summit.GazetteerParser;
import ch.epfl.alpano.summit.Summit;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class GazetteerParserTests
{
    private static boolean threw;

    @Test(expected = IOException.class)
    public void readSummitsFromThrowsException() throws IOException
    {
        GazetteerParser.readSummitsFrom(new File("Ressources/etape5/file1.txt"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiable() throws UnmodifiableClassException, IOException
    {
        List<Summit> list = GazetteerParser.readSummitsFrom(new File("Ressources/alps.txt"));
        list.add(new Summit("ksks", new GeoPoint(0, 0), 422));
    }

    @Test
    public void noColumnsFileThrowsException()
    {
        threw = false;
        try
        {
            List<Summit> list = GazetteerParser.readSummitsFrom(new File("Ressources/BrokenRessourcesForTests/noColumns.txt"));
        }
        catch(IOException e)
        {
            threw = true;
            assertEquals(e.getMessage(), "not a \":\" ");
        }
        assertEquals(true, threw);
    }

    @Test
    public void notANumberInLat()
    {
        threw = false;
        try
        {
            List<Summit> list = GazetteerParser.readSummitsFrom(new File("Ressources/BrokenRessourcesForTests/notANumberInLat.txt"));
        }
        catch(IOException e)
        {
            threw = true;
            assertEquals(e.getMessage(), "Not a number");
        }
        assertEquals(true, threw);
    }

    @Test
    public void notANumberInElevation()
    {
        threw = false;
        try
        {
            List<Summit> list = GazetteerParser.readSummitsFrom(new File("Ressources/BrokenRessourcesForTests/notANumberInElevation.txt"));
        }
        catch(IOException e)
        {
            threw = true;
            assertEquals(e.getMessage(), "Not a number");
        }
        assertEquals(true, threw);
    }

    @Test
    public void noName()
    {
        threw = false;
        try
        {
            List<Summit> list = GazetteerParser.readSummitsFrom(new File("Ressources/BrokenRessourcesForTests/noName.txt"));
        }
        catch(IOException e)
        {
            threw = true;
            assertEquals(e.getMessage(), "Invalid line : name empty");
        }
        assertEquals(true, threw);
    }

    @Test
    public void tooManyColumns()
    {
        threw = false;
        try
        {
            List<Summit> list = GazetteerParser.readSummitsFrom(new File("Ressources/BrokenRessourcesForTests/tooManyColumns.txt"));
        }
        catch(IOException e)
        {
            threw = true;
            assertEquals(e.getMessage(), "too many :");
        }
        assertEquals(true, threw);
    }


}
