package objects.gameObjects;

import game.Game;
import game.Main;
import objects.handlers.MouseHandler;
import objects.interfaces.Selectable;
import objects.misc.Camera;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MouseSelector extends GameObject{
    private Camera camera;
    private MouseHandler mouseHandler;
    private GameObject selection;
    private boolean hasSelection = false;

    public MouseSelector(Game game,Camera camera) {
        super(0, 0, 0, 0, null, game);
        this.camera = camera;
        mouseHandler = game.mouseHandler;
    }

    public GameObject getHover(){
        for(GameObject obj : game.objectHandler.objects){
            if((obj instanceof Selectable) && getPoint().distance(obj.getPoint()) < 50){
                return obj;
            }
        }
        return null;
    }

    public GameObject getSelection(){
        return selection;
    }

    public boolean hasSelection(){
        return hasSelection;
    }

    public GameObject findSelection(){
        GameObject object = getHover();
        if(mouseHandler.isMouseDown()){
            if(object != null){
                selection = object;
                hasSelection = true;
            }
            else {
                hasSelection = false;
            }
        }
        return selection;
    }

    @Override
    public void update() {
        Point2D mousePos = game.mouseHandler.getRelativeMousePos();
        x = mousePos.getX();
        y = mousePos.getY();
        selection = findSelection();
    }

    @Override
    public void render(Graphics g) {
        renderToCamera(graphics -> {
            Color color = Color.red;
            if(mouseHandler.isMouseDown()){
                color = Color.green;
            }
            graphics.setColor(color);
            graphics.drawRect((int)(x-25),(int)(y-25),50,50);
        },(Graphics2D)g,camera);
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
}
