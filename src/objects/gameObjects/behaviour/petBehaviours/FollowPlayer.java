package objects.gameObjects.behaviour.petBehaviours;

import objects.gameObjects.Pet;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;
import objects.handlers.KeyHandler;
import objects.misc.animation.Animation;

public class FollowPlayer implements Behaviour {
    private Pet pet;
    private Player player;
    private Navigator navigator;
    private boolean seenPlayer = false;
    private Animation animation;

    public FollowPlayer(Pet pet, Player player){
        this.pet = pet;
        this.player = player;
        this.navigator = pet.navigator;
        animation = new Animation("/sprites/cat/moving",1,1);
    }

    private boolean isNearEnough(){
        return (player.getPoint().distance(pet.getPoint()) < 60);
    }

    @Override
    public void start() {
        pet.currentSprite = animation.getSprite();
        navigator.setGoal(player.getPoint());
    }

    @Override
    public boolean needsControl() {
        if(!isNearEnough() && !pet.sitting){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(pet.hasLineOfSight(player)){
            navigator.moveToPoint(player.getPoint());
            seenPlayer = true;
        }
        else {
            if(seenPlayer){
                navigator.setGoal(player.getPoint());
                seenPlayer = false;
            }
            else if(navigator.reachedGoal()){
                navigator.setGoal(player.getPoint());
            }
            navigator.update();
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {

    }
}
