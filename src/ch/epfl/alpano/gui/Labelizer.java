package ch.epfl.alpano.gui;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Math2;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.ElevationProfile;
import ch.epfl.alpano.summit.Summit;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.*;
import java.util.function.DoubleUnaryOperator;

/**
 * Represents the labels that appear over the summits on the panorama
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class Labelizer
{
    private final ContinuousElevationModel cem;
    private final List<Summit> summitList;

    private static final int DX = 64;
    private static final int SUMMIT_TO_RAY_TOLERANCE = 200;
    private static final int VERTICAL_LIMIT_FROM_TOP_EDGE = 170;
    private static final int MINIMUM_SPACE_FROM_VERTICAL_EDGE = 20;
    private static final int DISTANCE_OF_TEXT_FROM_SUMMIT = 22;
    private static final int DISTANCE_BETWEEN_ELEMENTS = 2;
    private static final int TEXT_ROTATION_ANGLE = -60;

    /**
     * constructor of the class
     *
     * @param cem        the Continuous Elevation Model on which the labels will be computed on
     * @param summitList the list of all the summits of cem
     */
    public Labelizer(ContinuousElevationModel cem, List<Summit> summitList)
    {
        this.summitList = Objects.requireNonNull(Collections.unmodifiableList(summitList));
        this.cem = Objects.requireNonNull(cem);
    }

    /**
     * creates the labels
     *
     * @param params the current panorama parameters
     * @return a lis of all the labels of the panorama
     */
    public List<Node> labels(PanoramaParameters params)
    {
        final double width = params.width();

        final List<SummitEnhanced> summitEnhancedList = visibleSummits(params);
        final List<Node> nodeList = new ArrayList<>();
        final BitSet alreadyAssigned = new BitSet((int) Math.ceil(width));
        final int textYCoordinate = summitEnhancedList.get(0).y() - DISTANCE_OF_TEXT_FROM_SUMMIT;

        for(SummitEnhanced se : summitEnhancedList)
        {
            int x = se.x();

            if(!alreadyAssigned.get(x) && !alreadyAssigned.get(x + 20))
            {
                Node text = new Text(se.summitName());
                text.getTransforms().addAll(new Translate(x, textYCoordinate), new Rotate(TEXT_ROTATION_ANGLE, 0, 0));
                Node line = new Line(x, textYCoordinate + DISTANCE_BETWEEN_ELEMENTS, x, se.y() - DISTANCE_BETWEEN_ELEMENTS);
                nodeList.add(text);
                nodeList.add(line);
                alreadyAssigned.set(x, x + 20);
            }
        }
        return Collections.unmodifiableList(nodeList);
    }

    /**
     * calculates which summits are eligible to have labels
     *
     * @param params the current panorama parameters
     * @return the list of "visible summits"
     */
    private List<SummitEnhanced> visibleSummits(PanoramaParameters params)
    {
        List<SummitEnhanced> summitEnhancedList = new ArrayList<>();
        final GeoPoint obsPosition = params.observerPosition();
        final double halfHorizontalFieldOfView = params.horizontalFieldOfView() / 2;
        final double halfVerticalFieldOfView = params.verticalFieldOfView() / 2;
        final double centerAzimuth = params.centerAzimuth();
        final double observerElevation = params.observerElevation();
        final double maxDistance = params.maxDistance();
        final double width = params.width();

        for(Summit s : summitList)
        {
            double obsToSummitDistance = params.observerPosition().distanceTo(s.position());
            double obsToSummitAzimuth = params.observerPosition().azimuthTo(s.position());

            if(obsToSummitDistance <= maxDistance
                    && Math.abs(obsToSummitAzimuth - centerAzimuth) <= halfHorizontalFieldOfView)
            {
                ElevationProfile elevationProfileToSummit = new ElevationProfile(cem, obsPosition, obsToSummitAzimuth, maxDistance);

                DoubleUnaryOperator heightDiffComputer = PanoramaComputer.rayToGroundDistance(elevationProfileToSummit, observerElevation, 0);

                double angle = Math.atan(-heightDiffComputer.applyAsDouble(obsToSummitDistance) / obsToSummitDistance);

                DoubleUnaryOperator distanceToSummit = PanoramaComputer.rayToGroundDistance(elevationProfileToSummit, observerElevation, angle);

                double intersectDistance = Math2.firstIntervalContainingRoot(distanceToSummit, 0, maxDistance, DX);

                //checks whether the summit is within the panorama limits
                if((intersectDistance >= obsToSummitDistance - SUMMIT_TO_RAY_TOLERANCE || intersectDistance == Double.POSITIVE_INFINITY)
                        && Math.abs(angle) < halfVerticalFieldOfView)
                {
                    int y = (int) Math.round(params.yForAltitude(angle));
                    //checks the summit is too high for the label to be displayed
                    if(y > VERTICAL_LIMIT_FROM_TOP_EDGE)
                    {
                        int x = (int) params.xForAzimuth(obsPosition.azimuthTo(s.position()));
                        //checks the summit is too close to the edges for the label to be displayed
                        //if(x % (width - MINIMUM_SPACE_FROM_VERTICAL_EDGE) >= MINIMUM_SPACE_FROM_VERTICAL_EDGE)
                        if(x > MINIMUM_SPACE_FROM_VERTICAL_EDGE && x < (width - MINIMUM_SPACE_FROM_VERTICAL_EDGE))
                        {
                            summitEnhancedList.add(new SummitEnhanced(x, y, s.elevation(), s.name() + " (" + s.elevation() + " m)"));
                        }
                    }
                }
            }
        }
        Collections.sort(summitEnhancedList, (se1, se2) -> (se1.y() == se2.y() ? se2.summitElevation() - se1.summitElevation() : se1.y() - se2.y()));
        return Collections.unmodifiableList(summitEnhancedList);
    }

    private static class SummitEnhanced
    {
        private final int x, y, summitElevation;
        private final String summitName;

        private SummitEnhanced(int x, int y, int summitElevation,
                               String summitName)
        {
            this.x = x;
            this.y = y;
            this.summitElevation = summitElevation;
            this.summitName = summitName;
        }

        private int x()
        {
            return x;
        }

        private int y()
        {
            return y;
        }

        private int summitElevation()
        {
            return summitElevation;
        }

        private String summitName()
        {
            return summitName;
        }
    }
}
