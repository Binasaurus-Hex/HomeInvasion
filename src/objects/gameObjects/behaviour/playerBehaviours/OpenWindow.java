package objects.gameObjects.behaviour.playerBehaviours;

import game.Game;
import objects.gameObjects.Player;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;
import objects.handlers.KeyHandler;
import objects.interfaces.WindowListener;
import objects.misc.animation.Animation;

import java.awt.geom.Point2D;

public class OpenWindow implements Behaviour, WindowListener {
    private Player player;
    private Animation openWindow;
    private Window window;
    private Navigator navigator;
    private Point2D.Double start;
    private Point2D.Double end;
    private boolean finishedMove;

    public OpenWindow(Player player, Game game){
        this.player = player;
        openWindow = new Animation("/sprites/player/openWindow",10);
        window = null;
        navigator = new Navigator(player,game);
        start = null;
        end = null;

    }
    @Override
    public void start() {
        player.currentSprite = openWindow.getSprite();
        Point2D.Double[] anchors = window.getAnchorPoints();
        if(anchors[0].distance(player.getPoint())< anchors[1].distance(player.getPoint())){
            start = anchors[0];
            end = anchors[1];
        }
        else{
            start = anchors[1];
            end = anchors[0];
        }
        finishedMove = false;
    }

    @Override
    public boolean needsControl() {
        if(window != null){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(!finishedMove){
            player.setVelX(1);
            player.setVelY(1);
            if(navigator.moveToPoint(start)){
                System.out.println("true");
                player.setRotation(Math.atan2(end.getY()-start.getY(),end.getX()-start.getX()));
                player.setVelX(player.speed);
                player.setVelY(player.speed);
                finishedMove = true;
            }
        }
        else {
            if(!openWindow.isFinished()){
                openWindow.update();
                player.currentSprite = openWindow.getSprite();
            }
            else {
                openWindow.reset();
                window = null;
            }
        }
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
