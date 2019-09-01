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

public class CloseWindow implements Behaviour, WindowListener {
    private Player player;
    private Animation closeWindow;
    private Window window;
    private Navigator navigator;
    private Point2D.Double start;
    private Point2D.Double end;
    private boolean finishedMove;
    private boolean activated = false;

    public CloseWindow(Player player, Game game){
        this.player = player;
        closeWindow = new Animation("/sprites/player/openWindow",10,true);
        window = null;
        navigator = new Navigator(player,game);
        start = null;
        end = null;

    }
    @Override
    public void start() {
        player.currentSprite = closeWindow.getSprite();
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
            if(window.isOpen() && KeyHandler.isKeyPressed("F") || activated){
                activated = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!finishedMove){
            player.setVelX(1);
            player.setVelY(1);
            if(navigator.moveToPoint(start)){
                player.setRotation(Math.atan2(end.getY()-start.getY(),end.getX()-start.getX()));
                player.setVelX(player.speed);
                player.setVelY(player.speed);
                finishedMove = true;
            }
        }
        else {
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
