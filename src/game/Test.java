package game;

import javafx.util.Pair;
import objects.gameObjects.Node;
import objects.misc.PathGenerator;
import objects.misc.PathList;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Test {

    public static List<Pair<Point2D.Double,Double>> getOrderedAnchorPairs(Point2D.Double entity, List<Pair<Point2D.Double,Double>> pairs){
        Collections.sort(pairs,(a, b)-> (int)(a.getKey().distance(entity) - b.getKey().distance(entity)));
        return pairs;
    }

    public static void main(String[] args) {
        List<Pair<Point2D.Double,Double>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(new Point2D.Double(4, 5), 2.0));
        pairs.add(new Pair<>(new Point2D.Double(3,3),2.0));
        pairs = getOrderedAnchorPairs(new Point2D.Double(0,0),pairs);
        for(Pair<Point2D.Double,Double> pair : pairs){
            System.out.println(pair.getKey());
        }
    }
}
