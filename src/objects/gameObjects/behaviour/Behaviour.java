package objects.gameObjects.behaviour;

public interface Behaviour {
    public void start();
    public boolean needsControl();
    public void update();
    public int getPriority();
    public void stop();
}
