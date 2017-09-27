package ch.epfl.alpano.gui;

import ch.epfl.alpano.Panorama;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Utilitarian interface computing an image from an ImagePaintrer
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */

public interface PanoramaRenderer
{
    /**
     * Computes the image of a panorama according to the imagePainter it is provided
     *
     * @param panorama panorama which is to be drawn
     * @param painter  Channel Painter representing the way the panorama's values are translated into pixels
     * @return a {@link javafx.scene.image.Image javafx.scene.image.Image } representing the panorama
     */
    static Image returnPanorama(Panorama panorama, ImagePainter painter)
    {
        WritableImage image = new WritableImage(panorama.parameters().width(), panorama.parameters().height());
        PixelWriter pw = image.getPixelWriter();
        for(int i = 0; i < panorama.parameters().width(); i++)
        {
            for(int j = 0; j < panorama.parameters().height(); j++)
            {
                pw.setColor(i, j, painter.colorAt(i, j));
            }
        }

        return image;
    }
}
