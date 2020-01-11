package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.GameObject;
import objects.gameObjects.MouseSelector;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.handlers.KeyBindings;
import objects.handlers.KeyHandler;

public class Fighting implements Behaviour {
    private Player player;
    private MouseSelector selector;
    private GameObject target;
    private double slow;
    private double speed;

    public Fighting(Player player){
        this.player = player;
        selector = player.selector;
        slow = player.speed * 0.90;
        this.speed = player.speed;
    }

    @Override
    public void start() {
    }

    @Override
    public boolean needsControl() {
        if(selector.hasSelection()){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        target = selector.getSelection();
        if(target == null)return;
        player.speed = slow;
        player.setFacing(target);
        if(KeyHandler.isKeyPressed(KeyBindings.UP)){
            player.setY(player.getY()-player.getVelY());
        }
        if(KeyHandler.isKeyPressed(KeyBindings.DOWN)){
            player.setY(player.getY()+player.getVelY());
        }
        if(KeyHandler.isKeyPressed(KeyBindings.LEFT)){
            player.setX(player.getX()-player.getVelX());
        }
        if(KeyHandler.isKeyPressed(KeyBindings.RIGHT)){
            player.setX(player.getX()+player.getVelX());
        }
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    public void stop() {
        player.speed = speed;
    }
}
