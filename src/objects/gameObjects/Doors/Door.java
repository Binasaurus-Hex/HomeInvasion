package objects.gameObjects.Doors;

import game.CameraID;
import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObject;
import objects.gameObjects.GameObjectID;
import objects.handlers.MusicPlayer;
import objects.interfaces.Drawable;
import objects.misc.BufferedImageLoader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Door extends GameObject {

    protected enum DoorState{
        open,closed;
    }

    protected DoorState state;
    protected Rectangle2D.Double doorBounds;
    protected Rectangle2D.Double openBounds;
    protected Rectangle2D.Double closedBounds;
    protected ActivatableBounds doorActivate;
    protected double width,height;



    public Door(double x, double y, Game game) {
        super(x, y, 0, 0, GameObjectID.Door, game);
        state = DoorState.closed;

    }

    @Override
    public void update() {
    }

    protected void open(){
        if(state == DoorState.closed){
            state = DoorState.open;
            doorBounds = openBounds;
            System.out.println("open");
        }
    }

    protected void close(){
        if(state == DoorState.open){
            state = DoorState.closed;
            doorBounds = closedBounds;
            System.out.println("closed");
        }
    }

    @Override
    public void render(Graphics g) {

        Drawable door = (graphics)->{
            graphics.setColor(new Color(160,82,45));
            graphics.fill(doorBounds);

        };
        Graphics2D g2d = (Graphics2D) g;
        renderToCamera(door,g2d,game.cameraMap.get(CameraID.Main));
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return doorBounds;
    }
}
