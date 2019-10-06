package objects.gameObjects.behaviour.petBehaviours;

import objects.gameObjects.Pet;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.misc.animation.Animation;

public class SitDown implements Behaviour {
    private Player master;
    private Animation sitDown;
    private Pet pet;

    public SitDown(Pet pet, Player master){
        this.pet = pet;
        this.master = master;
        sitDown = new Animation("/sprites/cat/sitUp",6,6,true);
    }

    private boolean isNearEnough(){
        return (master.getPoint().distance(pet.getPoint()) < pet.comfortDistance);
    }

    @Override
    public void start() {
        pet.currentSprite = sitDown.getSprite();
    }

    @Override
    public boolean needsControl() {
        if(isNearEnough() && !pet.sitting){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(sitDown.isFinished()){
            pet.sitting = true;
            return;
        }
        else{
            sitDown.update();
            pet.currentSprite = sitDown.getSprite();
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {
        sitDown.reset();
    }
}
