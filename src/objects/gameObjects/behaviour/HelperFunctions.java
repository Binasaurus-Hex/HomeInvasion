package objects.gameObjects.behaviour;

import java.awt.geom.Point2D;

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


}
