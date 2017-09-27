package my_tests;



import ch.epfl.alpano.*;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import ch.epfl.alpano.gui.ChannelPainter;
import static ch.epfl.alpano.gui.ImagePainter.hsb;
import static org.junit.Assert.assertEquals;

import ch.epfl.alpano.gui.ImagePainter;
import ch.epfl.alpano.gui.PanoramaRenderer;
import ch.epfl.alpano.gui.PredefinedPanoramas;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.junit.Test;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO add javadoc
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class ThreadPerformanceTest {

    private static final PanoramaParameters P = PredefinedPanoramas.NIESEN.panoramaParameters();
    private static final ContinuousElevationModel CEM = new ContinuousElevationModel(new HgtDiscreteElevationModel(new File("N46E007.hgt")));
    private static final Map<String,PanoramaParameters> PARAMETERS_MAP = new HashMap<>();
    private static final PanoramaComputer TPC = new PanoramaComputer(CEM);
    private static final PanoramaComputer PC = new PanoramaComputer(CEM);

    @Test
    public void compareRuntime() {
        PARAMETERS_MAP.put("\'Finsteraarhorn\'",PredefinedPanoramas.FINSTERAARHORN.panoramaParameters());
        PARAMETERS_MAP.put("\'Alpes du Jura\'",PredefinedPanoramas.JURA_ALPS.panoramaParameters());
        PARAMETERS_MAP.put("\'Mont Racine\'",PredefinedPanoramas.MOUNT_RACINE.panoramaParameters());
        PARAMETERS_MAP.put("\'Niesen\'",PredefinedPanoramas.NIESEN.panoramaParameters());
        PARAMETERS_MAP.put("\'Plage du Pélican\'",PredefinedPanoramas.PELICAN_BEACH.panoramaParameters());
        PARAMETERS_MAP.put("\'Tour de Sauvablin\'",PredefinedPanoramas.SAUVABLIN_TOWER.panoramaParameters());
        long avgThread=0;
        long avg=0;
        List<String> runtimesBySummit = new ArrayList<>();
        int iterations = 5;
        for (String parametersName : PARAMETERS_MAP.keySet()){
            long avgByTPano = 0;
            long avgByPano = 0;
            for (int i = 0; i < iterations; i++) {
                long t = System.currentTimeMillis();
                TPC.computePanorama(PARAMETERS_MAP.get(parametersName));
                System.out.println(">> finished computing threaded panorama for "+parametersName+" : "+ (System.currentTimeMillis()-t) +" [ms]");
                avgThread+=System.currentTimeMillis()-t;
                avgByTPano+=System.currentTimeMillis()-t;
                t = System.currentTimeMillis();
                PC.computePanorama(PARAMETERS_MAP.get(parametersName));
                System.out.println(">> finished computing unthreaded panorama for "+parametersName+" : "+ (System.currentTimeMillis()-t) +" [ms]");
                avg+=System.currentTimeMillis()-t;
                avgByPano+=System.currentTimeMillis()-t;
            }
            runtimesBySummit.add(0,">>>> Average threaded runtime for "+iterations+" iterations of "+parametersName+" : "+((double)avgByTPano/iterations)+" [ms]");
            runtimesBySummit.add(">>>> Average unthreaded runtime for "+iterations+" iterations of "+parametersName+" : "+((double)avgByPano/iterations)+" [ms]");
        }
        for (String s : runtimesBySummit)
            System.out.println(s);
        System.out.println("Average total threaded runtime : "+((double) avgThread/(iterations*PARAMETERS_MAP.keySet().size()))+" [ms]");
        System.out.println("Average total unthreaded runtime : "+((double) avg/(iterations*PARAMETERS_MAP.keySet().size()))+" [ms]");
    }

    @Test
    public void compareImages() {
        Panorama tPano = TPC.computePanorama(P);
        ChannelPainter th = ChannelPainter.distanceAt(tPano).divBy(100000).cycle().multiply(360);
        ChannelPainter ts = ChannelPainter.distanceAt(tPano).divBy(200000).clamp().inverted();
        ChannelPainter tb0 = ((x,y) -> tPano.slopeAt(x,y)*(float)(2f/Math.PI));
        ChannelPainter tb = tb0.inverted().multiply(0.7f).add(0.3f);
        ChannelPainter to = ChannelPainter.distanceAt(tPano).map((x) -> x == Double.POSITIVE_INFINITY ? 0:1);
        ImagePainter tImagePainter = hsb(th,ts,tb,to);
        Image tImage = PanoramaRenderer.returnPanorama(tPano,tImagePainter);

        Panorama pano = PC.computePanorama(P);
        ChannelPainter h = ChannelPainter.distanceAt(pano).divBy(100000).cycle().multiply(360);
        ChannelPainter s = ChannelPainter.distanceAt(pano).divBy(200000).clamp().inverted();
        ChannelPainter b0 = ((x,y) -> pano.slopeAt(x,y)*(float)(2f/Math.PI));
        ChannelPainter b = b0.inverted().multiply(0.7f).add(0.3f);
        ChannelPainter o = ChannelPainter.distanceAt(pano).map((x) -> x == Double.POSITIVE_INFINITY ? 0:1);
        ImagePainter ImagePainter = hsb(h,s,b,o);
        Image image = PanoramaRenderer.returnPanorama(pano,ImagePainter);



        assertEquals(image.getHeight(),tImage.getHeight(),0);
        assertEquals(image.getWidth(), tImage.getWidth(),0);

        for(int i= 0; i< image.getHeight(); i++){
            for (int j = 0; j < image.getWidth(); j++) {
                assertEquals(image.getPixelReader().getColor(j,i),tImage.getPixelReader().getColor(j,i));
            }
        }

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(tImage, null),"png", new File("images/niesen-profile_threaded.png"));
            ImageIO.write(SwingFXUtils.fromFXImage(image, null),"png", new File("images/niesen-profile_unthreaded.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
