package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.handlers.KeyBindings;
import objects.handlers.KeyHandler;
import objects.misc.animation.Animation;

public class Move implements Behaviour {
    private Player player;
    private Animation walkingAnimation;

    public Move(Player player){
        this.player = player;
        walkingAnimation = new Animation("/sprites/player/moving",10,10);
        player.currentSprite = walkingAnimation.getSprite();
    }

    @Override
    public void start() {
        System.out.println("Moving");
    }

    @Override
    public boolean needsControl() {
        if(KeyHandler.isKeyPressed(KeyBindings.UP) || KeyHandler.isKeyPressed(KeyBindings.DOWN) || KeyHandler.isKeyPressed(KeyBindings.LEFT) || KeyHandler.isKeyPressed(KeyBindings.RIGHT)){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(KeyHandler.isKeyPressed(KeyBindings.UP)){
            player.setY(player.getY()-player.getVelY());
            player.setRotation(-1.5);
        }
        if(KeyHandler.isKeyPressed(KeyBindings.DOWN)){
            player.setY(player.getY()+player.getVelY());
            player.setRotation(1.5);
        }
        if(KeyHandler.isKeyPressed(KeyBindings.LEFT)){
            player.setX(player.getX()-player.getVelX());
            player.setRotation(-3);
        }
        if(KeyHandler.isKeyPressed(KeyBindings.RIGHT)){
            player.setX(player.getX()+player.getVelX());
            player.setRotation(0);
        }
        if(KeyHandler.isKeyPressed(KeyBindings.UP) && KeyHandler.isKeyPressed(KeyBindings.RIGHT)) {
            player.setRotation(-0.75);
        } else if(KeyHandler.isKeyPressed(KeyBindings.DOWN) && KeyHandler.isKeyPressed(KeyBindings.RIGHT)) {
            player.setRotation(0.75);
        } else if(KeyHandler.isKeyPressed(KeyBindings.UP) && KeyHandler.isKeyPressed(KeyBindings.LEFT)) {
            player.setRotation(-2.5);
        } else if(KeyHandler.isKeyPressed(KeyBindings.DOWN) && KeyHandler.isKeyPressed(KeyBindings.LEFT)) {
            player.setRotation(2.5);
        }
        walkingAnimation.update();
        player.currentSprite = walkingAnimation.getSprite();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {

    }
}
