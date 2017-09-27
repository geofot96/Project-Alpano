package ch.epfl.alpano.gui;

import javafx.scene.paint.Color;

/**
 * Functional interface representing an image painter
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */

@FunctionalInterface
public interface ImagePainter
{
    /**
     * Gets the color of certain pixel
     *
     * @param x horizontal index of the pixel
     * @param y horizontal index of the pixel
     * @return {@link javafx.scene.paint.Color javafx.scene.paint.Color} of the pixel
     */
    Color colorAt(int x, int y);

    /**
     * returns an image whose pixels are represented in HSB
     *
     * @param hue        ChannelPainter representing the hue of each of the pixels
     * @param saturation ChannelPainter representing the saturation of each of the pixels
     * @param brightness ChannelPainter representing the brightness of each of the pixels
     * @param opacity    ChannelPainter representing the opacity of each of the pixels
     * @return an image whose pixels are the combinations of all the ChannelPainters
     */
    static ImagePainter hsb(ChannelPainter hue, ChannelPainter saturation,
                            ChannelPainter brightness, ChannelPainter opacity)
    {
        return (x, y) -> Color.hsb(hue.valueAt(x, y), saturation.valueAt(x, y),
                brightness.valueAt(x, y), opacity.valueAt(x, y));
    }

    /**
     * returns an image whose pixels are represented in greyscale
     *
     * @param hue     ChannelPainter representing the hue of each of the pixels
     * @param opacity ChannelPainter representing the opacity of each of the pixels
     * @return an image whose pixels are the combinations of all the ChannelPainters
     */
    static ImagePainter gray(ChannelPainter hue, ChannelPainter opacity)
    {
        return ((x, y) -> Color.gray(hue.valueAt(x, y), opacity.valueAt(x, y)));
    }
}

