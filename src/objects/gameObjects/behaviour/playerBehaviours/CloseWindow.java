package objects.gameObjects.behaviour.playerBehaviours;

import game.Game;
import objects.gameObjects.Player;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.gameObjects.behaviour.Navigator;
import objects.handlers.KeyHandler;
import objects.interfaces.WindowListener;
import objects.misc.animation.Animation;

import java.awt.geom.Point2D;

public class CloseWindow implements Behaviour, WindowListener {
    private Player player;
    private Animation closeWindow;
    private Window window;
    private Point2D.Double start;
    private Point2D.Double end;
    private boolean activated = false;

    public CloseWindow(Player player){
        this.player = player;
        closeWindow = new Animation("/sprites/player/openWindow",10,7,true);
        window = null;
        start = null;
        end = null;

    }
    @Override
    public void start() {
        System.out.println("CloseWindow");
        player.currentSprite = closeWindow.getSprite();
    }

    @Override
    public boolean needsControl() {
        if(window != null){
            if(window.isOpen() && KeyHandler.isKeyPressed("F") || activated){
                activated = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!closeWindow.isFinished()){
            closeWindow.update();
            player.currentSprite = closeWindow.getSprite();
        }
        else {
            window.close();
            closeWindow.reset();
            activated = false;
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void stop() {
        activated = false;
    }

    @Override
    public void setWindowTouched(Window window) {
        this.window = window;
    }
}
