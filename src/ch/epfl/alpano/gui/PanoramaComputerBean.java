package ch.epfl.alpano.gui;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.summit.Summit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.List;

import static javafx.application.Platform.runLater;

/**
 * JavaFX Bean containing the parameters attached to the drawn panorama
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class PanoramaComputerBean
{
    private ObjectProperty<PanoramaUserParameters> parametersProperty;
    private ObjectProperty<Panorama> panoramaProperty;
    private ObjectProperty<Image> imageProperty;
    private ObjectProperty<ObservableList<Node>> labelsListProperty;

    /**
     * Constructor of the Bean, adds listener to parametersProperty, generates a new panorama each time the parametersProperty are changed.
     * Warning : all values are null before calling {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)}
     *
     * @param cem        ContinuousElevationModel used for the panoramas to be drawn
     * @param summitList List of the summit to overlay to the panorama
     */
    public PanoramaComputerBean(ContinuousElevationModel cem, List<Summit> summitList)
    {
        this.parametersProperty = new SimpleObjectProperty<>();
        this.panoramaProperty = new SimpleObjectProperty<>();
        this.imageProperty = new SimpleObjectProperty<>();
        this.labelsListProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());

        parametersProperty.addListener((prop, oldVal, newVal) -> runLater(() ->
        {
            this.panoramaProperty.set(new PanoramaComputer(cem).computePanorama(parametersProperty.get().panoramaParameters()));
            this.imageProperty.set(PanoramaRenderer.returnPanorama(panoramaProperty().get(), imagePainter()));
            this.labelsListProperty.get().setAll(new Labelizer(cem, summitList).labels(parametersProperty().get().displayedPanoramaParameters()));
        }));
    }

    /**
     * Getter for the parametersProperty of the computed panorama
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return the parametersProperty of the computed image
     */
    public ObjectProperty<PanoramaUserParameters> parametersProperty()
    {
        return parametersProperty;
    }

    /**
     * Getter for the PanoramaUserParameters of the computed panorama
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return the PanoramaUserParameters of the computed image
     */
    public PanoramaUserParameters getParameters()
    {
        return parametersProperty.get();
    }

    /**
     * Getter for the panoramaProperty of the computed panoarma
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return the panoramaProperty of the computed panoarma
     */
    public ReadOnlyObjectProperty<Panorama> panoramaProperty()
    {
        return panoramaProperty;
    }

    /**
     * Getter for the Panorama of the computed panorama
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return the Panorama of the computed panorama
     */
    public Panorama getPanorama()
    {
        return panoramaProperty.get();
    }

    /**
     * Getter for the imageProperty of the computed panorama
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return the imageProperty of the computed panorama
     */
    public ReadOnlyObjectProperty<Image> imageProperty()
    {
        return imageProperty;
    }

    /**
     * Getter for the image of the computed panorama
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return the image of the computed panorama
     */
    public Image getImage()
    {
        return imageProperty.get();
    }

    /**
     * Getter for the property containing the list of the nodes labeling the visible summits
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return the property containing the list of the nodes labeling the visible summits
     */
    public ReadOnlyObjectProperty<ObservableList<Node>> labelsProperty()
    {
        return labelsListProperty;
    }

    /**
     * Getter for the list of the nodes labeling the visible summits
     * Warning : null if {@link PanoramaComputerBean#setParameters(ReadOnlyObjectProperty)} has not been called
     *
     * @return for the list of the nodes labeling the visible summits
     */
    public ObservableList<Node> getLabels()
    {
        return labelsListProperty.get();
    }

    /**
     * Sets the panoramaUserParameters to new ones and computes a new panorama with them
     *
     * @param panoramaUserParametersProperty the new panorama parameters properties to update the PanoramaComputerBean with
     */
    public void setParameters(ReadOnlyObjectProperty<PanoramaUserParameters> panoramaUserParametersProperty)
    {
        this.parametersProperty().setValue(panoramaUserParametersProperty.get());
    }

    private ImagePainter imagePainter()
    {
        ChannelPainter h = ChannelPainter.distanceAt(getPanorama()).divBy(100000).cycle().multiply(360);
        ChannelPainter s = ChannelPainter.distanceAt(getPanorama()).divBy(200000).clamp().inverted();
        ChannelPainter b0 = ((x, y) -> getPanorama().slopeAt(x, y) * (float) (2f / Math.PI));
        ChannelPainter b = b0.inverted().multiply(0.7f).add(0.3f);
        ChannelPainter o = ChannelPainter.distanceAt(getPanorama()).map((x) -> x == Double.POSITIVE_INFINITY ? 0 : 1);
        return ImagePainter.hsb(h, s, b, o);
    }
}
