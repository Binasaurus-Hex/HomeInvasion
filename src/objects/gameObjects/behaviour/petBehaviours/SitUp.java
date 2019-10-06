package objects.gameObjects.behaviour.petBehaviours;

import objects.gameObjects.Pet;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.misc.animation.Animation;

public class SitUp implements Behaviour {
    private Pet pet;
    private Player player;
    private Animation sitUp = new Animation("/sprites/cat/sitUp", 6,6);

    public SitUp(Pet pet,Player player){
        this.pet = pet;
        this.player = player;
    }

    private boolean isNearEnough(){
        return (player.getPoint().distance(pet.getPoint()) < pet.comfortDistance);
    }

    @Override
    public void start() {
        pet.currentSprite = sitUp.getSprite();
    }

    @Override
    public boolean needsControl() {
        if(!isNearEnough() && pet.sitting){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(sitUp.isFinished()){
            pet.sitting = false;
        }
        sitUp.update();
        pet.currentSprite = sitUp.getSprite();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {
        sitUp.reset();
    }
}
