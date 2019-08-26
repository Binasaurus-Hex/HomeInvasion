package objects.gameObjects.AI;

import objects.gameObjects.Windows.Window;

public class Vault implements Behaviour,WindowListener{
    private Window touchedWindow;

    public Vault(){

    }

    @Override
    public void start() {

    }

    @Override
    public boolean needsControl() {
        if(touchedWindow != null){
            if(touchedWindow.isOpen()){
                System.out.println("window open");
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        System.out.println("valued bitch");
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
    public void setWindowTouched(Window window) {
        touchedWindow = window;
    }
}
