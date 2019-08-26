package objects.gameObjects.Doors;

import game.CameraID;
import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObject;
import objects.gameObjects.GameObjectID;
import objects.interfaces.Drawable;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Door extends GameObject {

    protected enum DoorState{
        open,closed;
    }

    protected DoorState state;
    protected Rectangle2D.Double doorBounds;
    protected Rectangle2D.Double openBounds;
    protected Rectangle2D.Double closedBounds;
    protected ActivatableBounds doorActivate;
    public double width,height;
    protected boolean inward = false;


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
        }
    }

    protected void close(){
        if(state == DoorState.open && doorActivate.isEmpty()){
            state = DoorState.closed;
            doorBounds = closedBounds;
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
