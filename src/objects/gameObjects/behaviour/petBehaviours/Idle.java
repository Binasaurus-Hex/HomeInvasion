package objects.gameObjects.behaviour.petBehaviours;

import objects.gameObjects.Pet;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.misc.animation.Animation;

public class Idle implements Behaviour {
    private Pet pet;
    private Player player;
    private Animation idle = new Animation("/sprites/cat/idle",6,11);

    public Idle(Pet pet, Player player){
        this.pet = pet;
        this.player = player;
    }

    @Override
    public void start() {
        pet.currentSprite = idle.getSprite();
        pet.sitting = true;
    }

    @Override
    public boolean needsControl() {
        return true;
    }

    @Override
    public void update() {
        pet.setRotation(Math.atan2(player.getY()-pet.getY(),player.getX()-pet.getX()));
        idle.update();
        pet.currentSprite = idle.getSprite();
    }

    @Override
    public int getPriority() {
        return -100;
    }

    @Override
    public void stop() {
        idle.reset();
    }
}
