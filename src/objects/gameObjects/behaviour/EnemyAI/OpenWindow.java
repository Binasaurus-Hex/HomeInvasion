package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.interfaces.WindowListener;

import java.util.Timer;
import java.util.TimerTask;

public class OpenWindow implements Behaviour, WindowListener {
    private Enemy enemy;
    private Window touchedWindow;

    public OpenWindow(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public void start() {
        touchedWindow.open();
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

    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setWindowTouched(Window window){
        touchedWindow = window;
    }
}
