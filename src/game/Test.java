package game;

import objects.gameObjects.Node;
import objects.misc.PathGenerator;
import objects.misc.PathList;

import java.awt.geom.Point2D;

public class Test {

    public static void main(String[] args) {
        Game game = new Game();
        Node goal = game.grid.getNearestNode(new Point2D.Double(1250,1300));
        for(Node i : goal.children){
            System.out.println(i.getPoint());
        }
    }
}
