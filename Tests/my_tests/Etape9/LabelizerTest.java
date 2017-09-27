package my_tests.Etape9;

import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import ch.epfl.alpano.gui.Labelizer;
import ch.epfl.alpano.gui.PanoramaUserParameters;
import ch.epfl.alpano.gui.PredefinedPanoramas;
import ch.epfl.alpano.summit.GazetteerParser;
import javafx.scene.Node;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * TODO add javadoc
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class LabelizerTest {

    @Test
    public void visibleSummitsWorks()throws IOException{
        Labelizer labelizer = new Labelizer(new ContinuousElevationModel(new HgtDiscreteElevationModel(new File("Ressources/hgt_6-10_45-47/N46E007.hgt"))),
                GazetteerParser.readSummitsFrom(new File("Ressources/alps.txt")));

        List<Node> nodeFromLabelizer = labelizer.labels(PredefinedPanoramas.NIESEN().panoramaParameters());
        List<String> nodesFromFile= new ArrayList<>();
        Scanner s = new Scanner(new File("Ressources/LabelizerTestRessources/VisibleSummitsNodes.txt"));

        while (s.hasNextLine()){
            nodesFromFile.add(s.nextLine());
        }

        /*for (int i = 0; i <10 ;i+=2){
            String[] nodeFromLabelizerSplit = nodeFromLabelizer.get(i).toString().split(",");
            String[] nodeFromFileSplit = nodesFromFile.get(i).split(",");
            for (int j = 0; j < 2; j++) {
                assertEquals(nodeFromLabelizer.get(i).toString(),nodeFromFileSplit[j],nodeFromLabelizerSplit[j]);
            }
        }
        for (int i = 1; i <10 ; i+=2){
            String[] nodeFromLabelizerSplit = nodeFromLabelizer.get(i).toString().split(",");
            String[] nodeFromFileSplit = nodesFromFile.get(i).split(",");
            for (int j = 0; j < 3; j++) {
                assertEquals(nodeFromLabelizer.get(i).toString(),nodeFromFileSplit[j],nodeFromLabelizerSplit[j]);
            }
        }*/
        for (Node n : nodeFromLabelizer){
            System.out.println(n);
        }
    }
}
