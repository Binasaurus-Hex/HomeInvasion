package objects.gameObjects.Windows;

import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObject;
import objects.gameObjects.GameObjectID;
import objects.interfaces.Activatable;
import objects.interfaces.Vaultable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Window extends GameObject implements Vaultable, Activatable {

    protected enum State{
        open,closed;
    }

    protected State windowState = State.closed;
    protected ActivatableBounds activation;
    protected double width,height;
    protected BufferedImage openSprite;
    protected BufferedImage closedSprite;
    protected Rectangle2D.Double bounds;


    protected Window(double x, double y, int z, Game game) {
        super(x, y, z, 0, GameObjectID.Window, game);
    }

    public static Window Horizontal(double x, double y, Game game){
        return new HorizontalWindow(x,y,0,game);
    }

    public static Window Vertical(double x,double y,Game game){
        return new VerticalWindow(x,y,0,game);
    }

    public abstract void open();

    public abstract void close();

    public abstract boolean isOpen();

    public abstract boolean isClosed();

    public abstract boolean isBarricaded();
}
