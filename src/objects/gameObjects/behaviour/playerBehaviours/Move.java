package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
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
        if(KeyHandler.isKeyPressed("W") || KeyHandler.isKeyPressed("S") || KeyHandler.isKeyPressed("A") || KeyHandler.isKeyPressed("D")){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(KeyHandler.isKeyPressed("W")){
            player.setY(player.getY()-player.getVelY());
            player.setRotation(-1.5);
        }
        if(KeyHandler.isKeyPressed("S")){
            player.setY(player.getY()+player.getVelY());
            player.setRotation(1.5);
        }
        if(KeyHandler.isKeyPressed("A")){
            player.setX(player.getX()-player.getVelX());
            player.setRotation(-3);
        }
        if(KeyHandler.isKeyPressed("D")){
            player.setX(player.getX()+player.getVelX());
            player.setRotation(0);
        }
        if(KeyHandler.isKeyPressed("W") && KeyHandler.isKeyPressed("D")) {
            player.setRotation(-0.75);
        } else if(KeyHandler.isKeyPressed("S") && KeyHandler.isKeyPressed("D")) {
            player.setRotation(0.75);
        } else if(KeyHandler.isKeyPressed("W") && KeyHandler.isKeyPressed("A")) {
            player.setRotation(-2.5);
        } else if(KeyHandler.isKeyPressed("S") && KeyHandler.isKeyPressed("A")) {
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
