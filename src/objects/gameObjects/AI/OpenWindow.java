package objects.gameObjects.AI;

import objects.gameObjects.Windows.Window;

public class OpenWindow implements Behaviour,WindowListener{

    private Window touchedWindow;

    public OpenWindow(){

    }

    @Override
    public void start() {

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
        touchedWindow.open();
        touchedWindow = null;
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
