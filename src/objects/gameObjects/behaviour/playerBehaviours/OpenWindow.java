package objects.gameObjects.behaviour.playerBehaviours;

import game.Game;
import objects.gameObjects.Player;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.gameObjects.behaviour.Navigator;
import objects.handlers.KeyBindings;
import objects.handlers.KeyHandler;
import objects.interfaces.WindowListener;
import objects.misc.animation.Animation;

import java.awt.geom.Point2D;

public class OpenWindow implements Behaviour, WindowListener {
    private Player player;
    private Animation openWindow;
    private Window window;
    private Point2D.Double start;
    private Point2D.Double end;
    private boolean activated = false;

    public OpenWindow(Player player){
        this.player = player;
        openWindow = new Animation("/sprites/player/openWindow",10,7);
        window = null;
        start = null;
        end = null;

    }
    @Override
    public void start() {
        player.currentSprite = openWindow.getSprite();
    }

    @Override
    public boolean needsControl() {
        if(window != null){
            if(window.isClosed() && KeyHandler.isKeyPressed(KeyBindings.INTERACT) || activated){
                activated = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!openWindow.isFinished()){
            openWindow.update();
            player.currentSprite = openWindow.getSprite();
        }
        else {
            window.open();
            openWindow.reset();
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
