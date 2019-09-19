package objects.interfaces;

import javafx.util.Pair;

import java.awt.geom.Point2D;
import java.util.List;

public interface Activatable {
    List<Pair<Point2D.Double,Double>> getAnchors();
}
