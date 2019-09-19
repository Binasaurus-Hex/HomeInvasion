package objects.gameObjects.Windows;

import game.Game;
import javafx.util.Pair;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObjectID;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class VerticalWindow extends Window {

    public VerticalWindow(double x, double y, int z, Game game) {
        super(x, y, z, game);
        width = 6;
        height = 50;
        activation = new ActivatableBounds(x,y,6,50,game);
        activation.addObjectID(GameObjectID.Player);
        activation.addObjectID(GameObjectID.Enemy);
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isBarricaded() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }

    @Override
    public Point2D.Double[] getAnchorPoints() {
        return new Point2D.Double[0];
    }

    @Override
    public boolean useable() {
        return isOpen();
    }

    @Override
    public List<Pair<Point2D.Double, Double>> getAnchors() {
        return null;
    }
}
