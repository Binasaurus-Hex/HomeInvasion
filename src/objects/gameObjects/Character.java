package objects.gameObjects;

import game.Game;
import objects.gameObjects.Windows.Window;

import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public abstract class Character extends GameObject {
    public BufferedImage currentSprite;

    public Character(double x, double y, int z, double rotation, GameObjectID id, Game game) {
        super(x, y, z, rotation, id, game);
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
}
