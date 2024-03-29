package objects.gameObjects.behaviour;

import javafx.util.Pair;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;

public class HelperFunctions {

    public static Point2D.Double[] getOrderedAnchors(Point2D.Double entity,Point2D.Double[] anchors){
        Point2D.Double[] orderedAnchors = new Point2D.Double[2];
        if(anchors[0].distance(entity) < anchors[1].distance(entity)){
            orderedAnchors[0] = anchors[0];
            orderedAnchors[1] = anchors[1];
        }
        else{
            orderedAnchors[0] = anchors[1];
            orderedAnchors[1] = anchors[0];
        }
        return orderedAnchors;
    }

    public static List<Pair<Point2D.Double,Double>> getOrderedAnchorPairs(Point2D.Double entity, List<Pair<Point2D.Double,Double>> pairs){
        Collections.sort(pairs,(a,b)-> (int)(a.getKey().distance(entity) - b.getKey().distance(entity)));
        return pairs;
    }


}
