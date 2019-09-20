package objects.gameObjects.Windows;

import game.CameraID;
import game.Game;
import javafx.util.Pair;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObjectID;
import objects.interfaces.Character;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class HorizontalWindow extends Window {

    private Point2D.Double startPoint;
    private Point2D.Double endPoint;
    private Point2D.Double[] anchorPoints;
    private Point2D.Double point;

    public HorizontalWindow(double x, double y, int z, Game game) {
        super(x, y, z, game);
        width = 50;
        height = 6;

        point = new Point2D.Double(x+width/2,y+height/2);
        startPoint = new Point2D.Double(x+width/2,y-14);
        endPoint = new Point2D.Double(x+width/2,y+height+14);
        anchorPoints = new Point2D.Double[]{startPoint,endPoint};
        bounds = new Rectangle2D.Double(x,y,width,height);
        activation = new ActivatableBounds(x-10,y-20,width+20,height+40,game);

        activation.addObjectID(GameObjectID.Player);
        activation.addObjectID(GameObjectID.Enemy);

        activation.setOnEnter((object)->{
            Character character = (Character)object;
            character.onWindowTouched(this);
        });
        activation.setOnExit((object)->{
            Character character = (Character)object;
            character.onWindowTouched(null);
        });

        game.objectHandler.add(activation);
    }

    @Override
    public void open() {
        windowState = State.open;
    }

    @Override
    public void close() {
        windowState = State.closed;
    }

    @Override
    public boolean isOpen() {
        return windowState == State.open;
    }

    @Override
    public boolean isClosed() {
        return windowState == State.closed;
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
        Color color;
        if(windowState == State.closed){
            color = new Color(6, 16, 119);
        }
        else{
            color = new Color(145, 178,255);
        }
        renderToCamera(graphics -> {
            graphics.setColor(color);
            graphics.fill(bounds);
        }, (Graphics2D)g,game.cameraMap.get(CameraID.Main));
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return bounds;
    }

    @Override
    public Point2D.Double getPoint(){
        return point;
    }

    @Override
    public Point2D.Double[] getAnchorPoints() {
        return anchorPoints;
    }

    @Override
    public boolean useable() {
        return isOpen();
    }

    @Override
    public List<Pair<Point2D.Double, Double>> getAnchors() {
        List<Pair<Point2D.Double,Double>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(startPoint,Math.PI/2));
        pairs.add(new Pair<>(endPoint,-Math.PI/2));
        return pairs;
    }
}
