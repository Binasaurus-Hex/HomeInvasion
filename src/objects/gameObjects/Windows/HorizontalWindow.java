package objects.gameObjects.Windows;

import game.CameraID;
import game.Game;
import objects.gameObjects.ActivatableBounds;
import objects.gameObjects.GameObjectID;
import objects.interfaces.Character;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

public class HorizontalWindow extends Window {

    public HorizontalWindow(double x, double y, int z, Game game) {
        super(x, y, z, game);
        width = 50;
        height = 6;

        bounds = new Rectangle2D.Double(x,y,width,height);
        activation = new ActivatableBounds(x,y,width,height,game);

        activation.addObjectID(GameObjectID.Player);
        activation.addObjectID(GameObjectID.Enemy);

        activation.setOnEnter((object)->{
            Character character = (Character)object;
            character.onWindowTouched(this);
        });
        activation.setOnExit((object)->{

        });

        game.objectHandler.add(activation);
    }

    @Override
    public void open() {
        windowState = State.open;
    }

    @Override
    public void close() {
        windowState = State.closed;
    }

    @Override
    public boolean isOpen() {
        return windowState == State.open;
    }

    @Override
    public boolean isClosed() {
        return windowState == State.closed;
    }

    @Override
    public boolean isBarricaded() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        Color color;
        if(windowState == State.closed){
            color = new Color(6, 16, 119);
        }
        else{
            color = new Color(145, 178,255);
        }
        renderToCamera(graphics -> {
            graphics.setColor(color);
            graphics.fill(bounds);
        }, (Graphics2D)g,game.cameraMap.get(CameraID.Main));
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return bounds;
    }
}
