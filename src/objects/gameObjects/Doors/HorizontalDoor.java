package objects.gameObjects.Doors;

import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObjectID;

import java.awt.geom.Rectangle2D;

public class HorizontalDoor extends Door {

    public HorizontalDoor(double x, double y,boolean inward, Game game) {
        super(x, y, game);
        width = 50;
        height = 6;
        this.inward = inward;
        init();
    }

    public HorizontalDoor(double x,double y,double width,double height,boolean inward,Game game){
        super(x,y,game);
        this.width = width;
        this.height = height;
        this.inward = inward;
        init();
    }

    private void init(){
        closedBounds = new Rectangle2D.Double(x,y,width,height);
        if(!inward){
            openBounds = new Rectangle2D.Double(x,y+height,height,width);
        }
        else{
            openBounds = new Rectangle2D.Double(x,y+(height)-width,height,width);
        }

        doorBounds = closedBounds;
        doorActivate = new ActivatableBounds(x,y-(width/2)+(height/2),width,width,game);
        initActivate();
    }


}
