package objects.gameObjects;

import game.Game;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Arbitrator;
import objects.gameObjects.behaviour.Navigator;
import objects.interfaces.Selectable;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Character extends GameObject implements Selectable {
    public BufferedImage currentSprite;
    protected boolean collidable = true;
    public boolean visible = true;
    public Navigator navigator;
    protected Arbitrator arbitrator;
    public double width,height;

    public Character(double x, double y,double width, double height, double rotation, GameObjectID id, Game game) {
        super(x, y, 0, rotation, id, game);
        arbitrator = new Arbitrator();
        navigator = new Navigator(this,game);
        this.width = width;
        this.height = height;
    }

    public void setCollidable(boolean value){
        collidable = value;
    }

    public void onWindowTouched(Window window){

    }
    public void onAnimeTouched(Anime anime){

    }

    public boolean hasLineOfSight(GameObject object){
        Line2D.Double line = new Line2D.Double(x,y,object.getX(),object.getY());
        for(GameObject obstacle : game.objectHandler.objects) {
            if(obstacle.id == GameObjectID.Wall || obstacle.id == GameObjectID.Door) {
                if(line.intersects(obstacle.getBounds())) {
                    return false;
                }
            }
        }
        return true;
    }

    public abstract void attack();
    public abstract void onAttacked(Point2D.Double force,double damage);
}
