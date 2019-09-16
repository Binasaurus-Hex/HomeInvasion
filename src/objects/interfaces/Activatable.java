package objects.interfaces;

import javafx.util.Pair;

import java.awt.geom.Point2D;

public interface Activatable {
    Pair<Point2D.Double,Double> getAnchor();
}
