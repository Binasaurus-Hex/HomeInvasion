package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.interfaces.WindowListener;

import java.awt.geom.Point2D;

public class Vault implements Behaviour, WindowListener {
    private Window touchedWindow;
    private Enemy enemy;

    public Vault(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public void start() {
        Point2D.Double[] anchorPoints = touchedWindow.getAnchorPoints();
        Point2D.Double myPoint = enemy.getPoint();
        Point2D.Double start;
        Point2D.Double finish;
        if(anchorPoints[0].distance(myPoint) < anchorPoints[1].distance(myPoint)){
            start = anchorPoints[0];
            finish = anchorPoints[1];
        }
        else {
            start = anchorPoints[1];
            finish = anchorPoints[0];
        }
    }

    @Override
    public boolean needsControl() {
        if(touchedWindow != null){
            if(touchedWindow.isOpen()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!enemy.navigator.reachedGoal()){
            enemy.navigator.update();
        }
        else{
            touchedWindow = null;
        }
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setWindowTouched(Window window) {
        touchedWindow = window;
    }
}
