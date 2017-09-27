package ch.epfl.alpano.dem;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.gui.ChannelPainter;
import ch.epfl.alpano.gui.ImagePainter;
import ch.epfl.alpano.gui.PanoramaRenderer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static java.lang.Math.*;

/**
 * Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
final class DrawPanorama
{
    final static File HGT_FILE = new File("N46E007.hgt");
    final static File HGT_FILE2 = new File("N45E007.hgt");

    static Panorama p;
    final static int IMAGE_WIDTH = 5000;
    final static int IMAGE_HEIGHT = 3000;

    final static double ORIGIN_LON = toRadians(7.721630);
    final static double ORIGIN_LAT = toRadians(45.971824);
    final static int ELEVATION = 5000;
    final static double CENTER_AZIMUTH = toRadians(320);
    final static double HORIZONTAL_FOV = toRadians(120);
    final static int MAX_DISTANCE = 100_000;

    final static PanoramaParameters PARAMS =
            new PanoramaParameters(new GeoPoint(ORIGIN_LON,
                    ORIGIN_LAT),
                    ELEVATION,
                    CENTER_AZIMUTH,
                    HORIZONTAL_FOV,
                    MAX_DISTANCE,
                    IMAGE_WIDTH,
                    IMAGE_HEIGHT);

    public static void main(String[] as) throws Exception
    {
        DiscreteElevationModel dDEM = //new HgtDiscreteElevationModel(HGT_FILE2);
                new CompositeDiscreteElevationModel(new HgtDiscreteElevationModel(HGT_FILE), new HgtDiscreteElevationModel(HGT_FILE2));
        ContinuousElevationModel cDEM = new ContinuousElevationModel(dDEM);
        long t = System.currentTimeMillis();
        p = new PanoramaComputer(cDEM).computePanorama(PARAMS);
        System.out.println(System.currentTimeMillis() - t);

        BufferedImage i =
                new BufferedImage(IMAGE_WIDTH,
                        IMAGE_HEIGHT,
                        TYPE_INT_RGB);

        for(int x = 0; x < IMAGE_WIDTH; ++x)
        {
            for(int y = 0; y < IMAGE_HEIGHT; ++y)
            {
                float d = p.distanceAt(x, y);
                int c = (d == Float.POSITIVE_INFINITY)
                        ? 0x87_CE_EB
                        : gray((d - 2_000) / 15_000);
                i.setRGB(x, y, c);
            }
        }

        ImageIO.write(i, "png", new File("images/niesen.png"));
        // voir étape 6


        ChannelPainter h = ChannelPainter.distanceAt(p).divBy(100000).cycle().multiply(360);
        ChannelPainter s = ChannelPainter.distanceAt(p).divBy(200000).clamp().inverted();
        ChannelPainter b0 = ((x, y) -> p.slopeAt(x, y) * (float) (2f / Math.PI));
        ChannelPainter b = b0.inverted().multiply(0.7f).add(0.3f);
        ChannelPainter o = ChannelPainter.distanceAt(p).map((x) -> x == Double.POSITIVE_INFINITY ? 0 : 1);

        /*
        ChannelPainter gray = ChannelPainter.maxDistanceToNeighbour(p)
                        .sub(500)
                        .divBy(4500)
                        .clamp()
                        .inverted();

        ChannelPainter opacity =
                ChannelPainter.distanceAt(p).map(dist -> dist == Float.POSITIVE_INFINITY ? 0 : 1);

        ImagePainter l = ImagePainter.gray(gray, opacity);
        */

        ImagePainter l = ImagePainter.hsb(h, s, b, o);
        Image im = PanoramaRenderer.returnPanorama(p, l);
        ImageIO.write(SwingFXUtils.fromFXImage(im, null),
                "png",
                new File("images/niesen-profile_unthreaded.png"));
    }

    private static int gray(double v)
    {
        double clampedV = max(0, min(v, 1));
        int gray = (int) (255.9999 * clampedV);
        return (gray << 16) | (gray << 8) | gray;
    }
}