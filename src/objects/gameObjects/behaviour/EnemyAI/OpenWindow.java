package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.interfaces.WindowListener;

import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

public class OpenWindow implements Behaviour, WindowListener {
    private Enemy enemy;
    private Window touchedWindow;
    private int counter = 0;

    public OpenWindow(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean needsControl() {
        if(touchedWindow != null){
            if (!touchedWindow.isBarricaded() && touchedWindow.isClosed()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(counter > 60){
            touchedWindow.open();
            counter = 0;
        }
        counter++;
    }

    @Override
    public int getPriority() {
        return 6;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setWindowTouched(Window window){
        touchedWindow = window;
    }
}
