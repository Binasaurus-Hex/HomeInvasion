package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.interfaces.Drawable;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class ActivatableBounds extends GameObject {

    public interface Action{
        void action();
    }

    private boolean entered;
    private Action enter;
    private Action exit;
    private Rectangle2D.Double bounds;
    private List<GameObjectID> searchObjects;

    public ActivatableBounds(double x, double y,double width,double height,Game game) {
        super(x, y,0, 0, GameObjectID.ActivatableBounds, game);
        enter = null;
        exit = null;
        bounds = new Rectangle2D.Double(x,y,width,height);
        entered = false;
        searchObjects = new ArrayList<>();
    }

    /**
     * adds an id of an object capable of activating this object
     */
    public void addObjectID(GameObjectID id){
        searchObjects.add(id);
    }

    public void setOnEnter(Action a){
        enter = a;
    }

    public void setOnExit(Action a){
        exit = a;
    }

    @Override
    public void update() {
        for(GameObject object : game.objectHandler.objects){
            if(searchObjects.contains(object.id)){
                if(isColliding(object)){
                    if(!entered){
                        entered = true;
                        if(enter != null)enter.action();
                    }
                    return;
                }
                else if(entered){
                    entered = false;
                    if(exit != null)exit.action();
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Drawable debug = (graphics)->{
            graphics.setColor(Color.red);
            graphics.draw(bounds);
        };
        Graphics2D g2d = (Graphics2D)g;
        //renderToCamera(debug,g2d,game.cameraMap.get(CameraID.Main));
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return bounds;
    }
}
