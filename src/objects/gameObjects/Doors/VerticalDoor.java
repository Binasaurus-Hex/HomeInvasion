package objects.gameObjects.Doors;

import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObjectID;

import java.awt.geom.Rectangle2D;

public class VerticalDoor extends Door {

    public VerticalDoor(double x, double y,boolean inward, Game game) {
        super(x, y, game);
        width = 8;
        height = 50;

        closedBounds = new Rectangle2D.Double(x,y,width,height);
        if(!inward){
            openBounds = new Rectangle2D.Double(x+width,y,height,width);
        }
        else {
            openBounds = new Rectangle2D.Double(x+(width)-height,y,height,width);
        }

        doorBounds = closedBounds;
        doorActivate = new ActivatableBounds(x-(height/2)+(width/2),y,height,height,game);
        doorActivate.addObjectID(GameObjectID.Enemy);
        doorActivate.addObjectID(GameObjectID.Player);
        doorActivate.setOnEnter(()->open());
        doorActivate.setOnExit(()->close());
        game.objectHandler.add(doorActivate);
    }
}
