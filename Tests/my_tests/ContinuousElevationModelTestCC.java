package my_tests;


import ch.epfl.alpano.dem.DrawDEM;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ContinuousElevationModelTestCC
{
    @BeforeClass
    public static void drawDEM()
    {
        try
        {
            DrawDEM.main(null);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    private void testAreSame(BufferedImage expected, BufferedImage actual)
    {
        testHaveSameDimensions(expected, actual);

        for(int y = 0; y < expected.getHeight(); y++)
        {
            for(int x = 0; x < expected.getWidth(); x++)
            {
                assertEquals(expected.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    private void testHaveSameDimensions(BufferedImage expected, BufferedImage actual)
    {
        assertTrue(expected.getWidth() == actual.getWidth() && expected.getHeight() == actual.getHeight());
    }
}
