package my_tests.Etape9;

/**
 * TODO Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
import static ch.epfl.alpano.gui.PredefinedPanoramas.*;

import ch.epfl.alpano.gui.PanoramaParametersBean;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.stage.Stage;
import static ch.epfl.alpano.gui.PredefinedPanoramas.NIESEN;

public final class BeansUse extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PanoramaParametersBean bean =
                new PanoramaParametersBean(NIESEN);
        ObjectProperty<Integer> prop =
                bean.observerLatitudeProperty();

        prop.addListener((o, oV, nV) ->
                System.out.printf("  %d -> %d (%s)%n", oV, nV, o));
        System.out.println("set to 1");
        prop.set(1);
        System.out.println("set to 2");
        prop.set(2);

        Platform.exit();
    }
}
