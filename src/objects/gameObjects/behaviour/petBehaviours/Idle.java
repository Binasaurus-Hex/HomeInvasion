package objects.gameObjects.behaviour.petBehaviours;

import objects.gameObjects.Pet;
import objects.gameObjects.behaviour.Behaviour;
import objects.misc.animation.Animation;

public class Idle implements Behaviour {
    private Pet pet;
    private Animation idle = new Animation("/sprites/cat/idle",6,11);

    public Idle(Pet pet){
        this.pet = pet;
    }
    @Override
    public void start() {
        pet.currentSprite = idle.getSprite();
    }

    @Override
    public boolean needsControl() {
        return true;
    }

    @Override
    public void update() {
        idle.update();
        pet.currentSprite = idle.getSprite();
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public void stop() {
        idle.reset();
    }
}
