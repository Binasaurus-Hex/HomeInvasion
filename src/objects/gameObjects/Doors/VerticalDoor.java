package objects.gameObjects.Doors;

import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObjectID;

import java.awt.geom.Rectangle2D;

public class VerticalDoor extends Door {

    public VerticalDoor(double x, double y,boolean inward, Game game) {
        super(x, y, game);
        width = 6;
        height = 50;

        this.inward = inward;
        init();
    }

    public VerticalDoor(double x,double y,double width,double height,boolean inward,Game game){
        super(x,y,game);
        this.width = width;
        this.height = height;
        this.inward = inward;
        init();

    }

    private void init(){
        closedBounds = new Rectangle2D.Double(x,y,width,height);
        if(!inward){
            openBounds = new Rectangle2D.Double(x,y,height,width);
        }
        else {
            openBounds = new Rectangle2D.Double(x+(width)-height,y,height,width);
        }

        doorBounds = closedBounds;
        doorActivate = new ActivatableBounds(x-(height/2)+(width/2),y,height,height,game);
        doorActivate.addObjectID(GameObjectID.Enemy);
        doorActivate.addObjectID(GameObjectID.Player);
        doorActivate.setOnEnter((object)->open());
        doorActivate.setOnExit((object)->close());
        game.objectHandler.add(doorActivate);
    }
}
