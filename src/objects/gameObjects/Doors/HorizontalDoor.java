package objects.gameObjects.Doors;

import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObjectID;

import java.awt.geom.Rectangle2D;

public class HorizontalDoor extends Door {

    public HorizontalDoor(double x, double y,boolean inward, Game game) {
        super(x, y, game);
        width = 50;
        height = 8;

        closedBounds = new Rectangle2D.Double(x,y,width,height);
        if(!inward){
            openBounds = new Rectangle2D.Double(x,y+height,height,width);
        }
        else{
            openBounds = new Rectangle2D.Double(x,y+(height)-width,height,width);
        }

        doorBounds = closedBounds;
        doorActivate = new ActivatableBounds(x,y-(width/2)+(height/2),width,width,game);
        doorActivate.addObjectID(GameObjectID.Enemy);
        doorActivate.addObjectID(GameObjectID.Player);
        doorActivate.setOnEnter(()->open());
        doorActivate.setOnExit(()->close());
        game.objectHandler.add(doorActivate);
    }
}
