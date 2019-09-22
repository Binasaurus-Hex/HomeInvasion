package objects.gameObjects.behaviour.petBehaviours;

import objects.gameObjects.Pet;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;

public class FollowPlayer implements Behaviour {
    private Pet pet;
    private Player player;
    private Navigator navigator;
    private boolean seenPlayer = false;

    public FollowPlayer(Pet pet, Player player){
        this.pet = pet;
        this.player = player;
        this.navigator = pet.navigator;
    }

    @Override
    public void start() {
        navigator.setGoal(player.getPoint());
    }

    @Override
    public boolean needsControl() {
        return true;
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
