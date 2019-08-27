package objects.gameObjects.AI;

import objects.gameObjects.Enemy;
import objects.gameObjects.Windows.Window;

import java.util.Timer;
import java.util.TimerTask;

public class OpenWindow implements Behaviour,WindowListener{
    private Enemy enemy;
    private Window touchedWindow;

    public OpenWindow(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public void start() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                touchedWindow.open();
                touchedWindow = null;
            }
        },1000);
    }

    @Override
    public boolean needsControl() {
        if(touchedWindow != null){
            if (!touchedWindow.isBarricaded() && touchedWindow.isClosed()){
                System.out.println("i want to open it");
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
