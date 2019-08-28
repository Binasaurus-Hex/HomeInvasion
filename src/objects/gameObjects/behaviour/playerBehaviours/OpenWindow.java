package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Player;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.handlers.KeyHandler;
import objects.interfaces.WindowListener;
import objects.misc.animation.Animation;

public class OpenWindow implements Behaviour, WindowListener {
    private Player player;
    private Animation openWindow;
    private Window window;

    public OpenWindow(Player player){
        this.player = player;
        openWindow = new Animation("/sprites/player/openWindow",10);
        window = null;
    }
    @Override
    public void start() {
        player.currentSprite = openWindow.getSprite();
    }

    @Override
    public boolean needsControl() {
        if(KeyHandler.isKeyPressed("M")){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        openWindow.update();
        player.currentSprite = openWindow.getSprite();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setWindowTouched(Window window) {
        this.window = window;
    }
}
