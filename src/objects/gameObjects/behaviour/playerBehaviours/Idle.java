package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.misc.animation.Animation;

public class Idle implements Behaviour {
    private Animation animation;
    private Player player;

    public Idle(Player player){
        animation = new Animation("/sprites/player/idle",1,1);
        this.player = player;
    }

    @Override
    public void start() {
        player.currentSprite = animation.getSprite();
    }

    @Override
    public boolean needsControl() {
        return true;
    }

    @Override
    public void update() {
    }

    @Override
    public int getPriority() {
        return -100;
    }

    @Override
    public void stop() {

    }
}
