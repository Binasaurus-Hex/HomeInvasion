package objects.gameObjects;

import game.CameraID;
import game.Game;
import game.Main;
import javafx.util.Pair;
import objects.interfaces.Drawable;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivatableBounds extends GameObject {

    public interface Action{
        void action(GameObject o);
    }

    private Map<GameObject,Boolean> objectsEntered;

    private Action enter;
    private Action exit;
    private Rectangle2D.Double bounds;
    private List<GameObjectID> searchObjects;
    private List<Class> searchClasses;

    public ActivatableBounds(double x, double y,double width,double height,Game game) {
        super(x, y,0, 0, GameObjectID.ActivatableBounds, game);
        enter = null;
        exit = null;
        bounds = new Rectangle2D.Double(x,y,width,height);
        searchObjects = new ArrayList<>();
        searchClasses = new ArrayList<>();
        objectsEntered = new HashMap<>();
    }

    /**
     * adds an id of an object capable of activating this object
     */
    public void addObjectID(GameObjectID id){
        searchObjects.add(id);
    }

    public void addObjectClass(Class cls){
        searchClasses.add(cls);
    }

    public void setOnEnter(Action a){
        enter = a;
    }

    public void setOnExit(Action a){
        exit = a;
    }

    private boolean containsClass(Class cls){
        for(Class c : searchClasses){
            if(c.isAssignableFrom(cls)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        for(GameObject object : game.objectHandler.objects){
            if(searchObjects.contains(object.id) || containsClass(object.getClass())){
                if(isColliding(object)){
                    boolean entered = objectsEntered.getOrDefault(object,false);
                    if(!entered){
                        if(objectsEntered.containsKey(object)){
                            objectsEntered.replace(object,true);
                        }
                        else{
                            objectsEntered.put(object,true);
                        }
                        if(enter != null)enter.action(object);
                    }
                }
                else if(objectsEntered.getOrDefault(object,false)){
                    objectsEntered.replace(object,false);
                    if(exit != null)exit.action(object);
                }
            }
        }
    }

    public boolean isEmpty(){
        for(Map.Entry<GameObject,Boolean> entry : objectsEntered.entrySet()){
            if(entry.getValue() == true){
                return false;
            }
        }
        return true;
    }

    @Override
    public void render(Graphics g) {
        Drawable debug = (graphics)->{
            graphics.setColor(Color.red);
            graphics.draw(bounds);
        };
        Graphics2D g2d = (Graphics2D)g;
        if(Main.debug){
            renderToCamera(debug,g2d,game.cameraMap.get(CameraID.Main));
        }
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return bounds;
    }
}
