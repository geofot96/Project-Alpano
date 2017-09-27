package ch.epfl.alpano.gui;

import ch.epfl.alpano.Azimuth;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import ch.epfl.alpano.summit.GazetteerParser;
import ch.epfl.alpano.summit.Summit;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import static ch.epfl.alpano.gui.Localisation.French.*;


/**
 * Main class of the program responsible to design the GUI
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class Alpano extends Application
{
    private final static FixedPointStringConverter FOUR_PLACES = new FixedPointStringConverter(4);
    private final static FixedPointStringConverter ONE_PLACE = new FixedPointStringConverter(1);
    private final static FixedPointStringConverter ZERO_PLACE = new FixedPointStringConverter(0);
    private final ObjectProperty<String> mousePosition = new SimpleObjectProperty<>();

    /**
     * launches the program
     *
     * @param args launch parameters
     */
    public static void main(String[] args)
    {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        List<Summit> summitsList = GazetteerParser.readSummitsFrom(new File("alps.txt"));
        DiscreteElevationModel hgt45_06 = new HgtDiscreteElevationModel(new File("N45E006.hgt"));
        DiscreteElevationModel hgt46_06 = new HgtDiscreteElevationModel(new File("N46E006.hgt"));
        DiscreteElevationModel hgt45_07 = new HgtDiscreteElevationModel(new File("N45E007.hgt"));
        DiscreteElevationModel hgt46_07 = new HgtDiscreteElevationModel(new File("N46E007.hgt"));
        DiscreteElevationModel hgt45_08 = new HgtDiscreteElevationModel(new File("N45E008.hgt"));
        DiscreteElevationModel hgt46_08 = new HgtDiscreteElevationModel(new File("N46E008.hgt"));
        DiscreteElevationModel hgt45_09 = new HgtDiscreteElevationModel(new File("N45E009.hgt"));
        DiscreteElevationModel hgt46_09 = new HgtDiscreteElevationModel(new File("N46E009.hgt"));

        DiscreteElevationModel compositeDem = hgt45_06.union(hgt45_07).union(hgt45_08).union(hgt45_09).union((hgt46_06).union(hgt46_07).union(hgt46_08).union(hgt46_09));
        ContinuousElevationModel cem = new ContinuousElevationModel(compositeDem);

        PanoramaParametersBean panoramaParametersBean = new PanoramaParametersBean(PredefinedPanoramas.JURA_ALPS);
        PanoramaComputerBean panoramaComputerBean = new PanoramaComputerBean(cem, summitsList);

        Pane labelsPane = labelsPane(new Pane(), panoramaParametersBean, panoramaComputerBean);

        ImageView panoView = panoView(new ImageView(), panoramaParametersBean, panoramaComputerBean);

        StackPane panoGroup = new StackPane(panoView, labelsPane);

        ScrollPane panoScrollPane = new ScrollPane(panoGroup);

        StackPane updateNotice = updateNotice(new StackPane(), panoramaParametersBean, panoramaComputerBean);

        StackPane panoPane = new StackPane(panoScrollPane, updateNotice);

        GridPane paramsGrid = paramsGrid(new GridPane(), panoramaParametersBean);

        BorderPane root = new BorderPane();
        root.setCenter(panoPane);
        root.setBottom(paramsGrid);

        Scene scene = new Scene(root);
        primaryStage.setTitle(ALPANO_STAGE_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane labelsPane(Pane pane, PanoramaParametersBean panoramaParametersBean, PanoramaComputerBean panoramaComputerBean)
    {
        pane.prefWidthProperty().bind(panoramaParametersBean.widthProperty());
        pane.prefHeightProperty().bind(panoramaParametersBean.heightProperty());
        pane.setMouseTransparent(true);
        Bindings.bindContent(pane.getChildren(), panoramaComputerBean.getLabels());
        return pane;
    }

    private ImageView panoView(ImageView panoView, PanoramaParametersBean panoramaParametersBean, PanoramaComputerBean panoramaComputerBean)
    {
        panoView.fitWidthProperty().bind(panoramaParametersBean.widthProperty());
        panoView.fitHeightProperty().bind(panoramaParametersBean.heightProperty());
        panoView.imageProperty().bind(panoramaComputerBean.imageProperty());
        panoView.setPreserveRatio(true);
        panoView.setSmooth(true);

        //filling of mouseArea
        panoView.setOnMouseMoved(event ->
        {
            int superSampling = (int) Math.pow(2, panoramaParametersBean.superSamplingExponentProperty().get());
            //Values initialization

            String latitudeCard = NORTH_SYMBOL;
            String longitudeCard = EAST_SYMBOL;
            StringBuilder sb = new StringBuilder();
            int altitude = (int) panoramaComputerBean.getPanorama().elevationAt((int) (event.getX() * superSampling), (int) (event.getY() * superSampling));
            double azimuthDouble = (panoramaComputerBean.getParameters().panoramaParameters().azimuthForX((event.getX() * superSampling)));
            double latitudeDouble = Math.toDegrees(panoramaComputerBean.getPanorama().latitudeAt((int) (event.getX() * superSampling), (int) (event.getY() * superSampling)));
            double longitudeDouble = Math.toDegrees(panoramaComputerBean.getPanorama().longitudeAt((int) (event.getX() * superSampling), (int) (event.getY() * superSampling)));

            //cardinal symbol inference and positivity asserted
            if(latitudeDouble < 0)
            {
                latitudeDouble *= -1;
                latitudeCard = SOUTH_SYMBOL;
            }
            if(latitudeDouble < 0)
            {
                longitudeDouble *= -1;
                longitudeCard = WEST_SYMBOL;
            }

            //String construction
            String latitude = FOUR_PLACES.toString((int) (latitudeDouble * 10000));
            String longitude = FOUR_PLACES.toString((int) (longitudeDouble * 10000));
            String distance = ONE_PLACE.toString((int) panoramaComputerBean.getPanorama().distanceAt((int) (event.getX() * superSampling), (int) (event.getY() * superSampling)) / 100);
            String azimuth = ONE_PLACE.toString((int) (Math.toDegrees(azimuthDouble) * 10));
            String azimuthCard = Azimuth.toOctantString(azimuthDouble, NORTH_SYMBOL, EAST_SYMBOL, SOUTH_SYMBOL, WEST_SYMBOL);
            String elevation = ONE_PLACE.toString((int) Math.toDegrees(panoramaComputerBean.getParameters().panoramaParameters().altitudeForY((event.getY() * superSampling))));

            //TextField's content fixed
            sb.append(POSITION + " : ").append(latitude).append("°")
                    .append(latitudeCard).append(" ").append(longitude)
                    .append("°").append(longitudeCard).append("\n");
            sb.append(DISTANCE + " : ").append(distance).append(" km\n");
            sb.append(ALTITUDE + " : ").append(altitude).append(" m\n");
            sb.append(AZIMUTH + " : ").append(azimuth).append("° (")
                    .append(azimuthCard).append(") ").append(ELEVATION)
                    .append(" : ").append(elevation).append("°");
            mousePosition.set(sb.toString());
        });

        //Browser opening
        panoView.setOnMouseClicked(event ->
        {
            int superSampling = (int) Math.pow(2, panoramaParametersBean.superSamplingExponentProperty().get());

            double latitude = Math.toDegrees(panoramaComputerBean.getPanorama().latitudeAt((int) (event.getX() * superSampling), (int) (event.getY() * superSampling)));
            double longitude = Math.toDegrees(panoramaComputerBean.getPanorama().longitudeAt((int) (event.getX() * superSampling), (int) (event.getY() * superSampling)));
            String qy = String.format((Locale) null, "mlat=%.4f&mlon=%.4f", latitude, longitude);
            String fg = String.format((Locale) null, "#map=15/%.4f/%.4f", latitude, longitude);
            try
            {
                URI map = new URI("http", "www.openstreetmap.org", "/", qy, fg);
                Desktop.getDesktop().browse(map);
            }
            catch(URISyntaxException e)
            {
                System.err.println("bad URI format, open map in browser aborted.");
            }
            catch(IOException e)
            {
                System.err.println("IOException in Desktop.browse, open map in browser aborted.");
            }
        });

        return panoView;
    }

    private StackPane updateNotice(StackPane updateNotice, PanoramaParametersBean panoramaParametersBean, PanoramaComputerBean panoramaComputerBean)
    {
        BooleanExpression paramBeanIsNotEqualToComputerBean = panoramaParametersBean.parametersProperty().isNotEqualTo(panoramaComputerBean.parametersProperty());
        BooleanExpression computerBeanHasBeenInitialized = panoramaComputerBean.imageProperty().isNotNull();
        When textChanger = new When(computerBeanHasBeenInitialized);

        Text updateText = new Text();
        updateText.textProperty().bind(textChanger.then(UPDATE_NOTICE_AFTER_INIT).otherwise(UPDATE_NOTICE_BEFORE_INIT));
        updateText.setFont(Font.font(40));
        updateText.setTextAlignment(TextAlignment.CENTER);
        updateNotice.getChildren().add(updateText);
        updateNotice.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 0.8), null, null)));
        updateNotice.visibleProperty().bind(paramBeanIsNotEqualToComputerBean);
        updateNotice.setOnMouseClicked(event -> panoramaComputerBean.setParameters(panoramaParametersBean.parametersProperty()));
        return updateNotice;
    }

    private GridPane paramsGrid(GridPane paramsGrid, PanoramaParametersBean panoramaParametersBean)
    {

        paramsGrid.setHgap(10);
        paramsGrid.setVgap(3);
        paramsGrid.setAlignment(Pos.CENTER);
        paramsGrid.setPadding(new Insets(3, 10, 3, 10));

        Label latitude = textFieldLabel(LATITUDE + " (°) : ");
        Label longitude = textFieldLabel(LONGITUDE + " (°) : ");
        Label azimuth = textFieldLabel(AZIMUTH + " (°) : ");
        Label fieldOfView = textFieldLabel(FIELD_OF_VIEW + " (°) : ");
        Label width = textFieldLabel(WIDTH + " (px) : ");
        Label height = textFieldLabel(HEIGHT + " (px) : ");
        Label elevation = textFieldLabel(ALTITUDE + " (m) : ");
        Label visibility = textFieldLabel(VISIBILITY + " (km) : ");

        TextField latitudeTextField = textField(panoramaParametersBean.observerLatitudeProperty(), FOUR_PLACES, 7);
        TextField longitudeTextField = textField(panoramaParametersBean.observerLongitudeProperty(), FOUR_PLACES, 7);
        TextField azimuthTextField = textField(panoramaParametersBean.centerAzimuthProperty(), ZERO_PLACE, 3);
        TextField fieldOfViewTextField = textField(panoramaParametersBean.horizontalFieldOfViewProperty(), ZERO_PLACE, 3);
        TextField visibilityTextField = textField(panoramaParametersBean.maxDistanceProperty(), ZERO_PLACE, 3);
        TextField widthTextField = textField(panoramaParametersBean.widthProperty(), ZERO_PLACE, 4);
        TextField heightTextField = textField(panoramaParametersBean.heightProperty(), ZERO_PLACE, 4);
        TextField elevationTextField = textField(panoramaParametersBean.observerElevationProperty(), ZERO_PLACE, 4);


        ChoiceBox<Integer> superSamplingChoiceBox = new ChoiceBox<>();
        superSamplingChoiceBox.getItems().addAll(0, 1, 2);
        Label superSampling = new Label(SUPERSAMPLING + " : ");

        LabeledListStringConverter superSamplingConverter = new LabeledListStringConverter(NO, "2×", "4×");
        superSamplingChoiceBox.setConverter(superSamplingConverter);
        superSamplingChoiceBox.valueProperty().bindBidirectional(panoramaParametersBean.superSamplingExponentProperty());

        TextArea mouseArea = new TextArea();
        mouseArea.setEditable(false);
        mouseArea.setPrefRowCount(3);
        mouseArea.textProperty().bind(mousePosition);

        paramsGrid.addRow(0, latitude, latitudeTextField, longitude, longitudeTextField, elevation, elevationTextField);
        paramsGrid.addRow(1, azimuth, azimuthTextField, fieldOfView, fieldOfViewTextField, visibility, visibilityTextField);
        paramsGrid.addRow(2, width, widthTextField, height, heightTextField, superSampling, superSamplingChoiceBox);
        paramsGrid.add(mouseArea, 6, 0, 1, 3);

        return paramsGrid;
    }

    private TextField textField(ObjectProperty<Integer> propertyToBindWith, FixedPointStringConverter stringConverter, int prefColCount)
    {
        TextField textField = new TextField();

        TextFormatter<Integer> tf = new TextFormatter<>(stringConverter);
        textField.setTextFormatter(tf);
        tf.valueProperty().bindBidirectional(propertyToBindWith);

        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.setPrefColumnCount(prefColCount);
        return textField;
    }

    private Label textFieldLabel(String name)
    {
        Label label = new Label(name);
        label.setAlignment(Pos.CENTER_RIGHT);
        return label;
    }
}
