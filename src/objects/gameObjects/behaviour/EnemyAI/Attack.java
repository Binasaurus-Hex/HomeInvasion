package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;
import objects.misc.animation.Animation;

public class Attack implements Behaviour {
    private Enemy enemy;
    private Navigator navigator;
    private Animation move;
    private double speed;
    private double fast;

    public Attack(Enemy enemy){
        this.enemy = enemy;
        this.navigator = enemy.navigator;
        move = new Animation("/sprites/enemy/moving",20,10);
    }

    @Override
    public void start() {
        speed = enemy.speed;
        fast = speed * 1.2;
        enemy.setSpeed(fast);
        enemy.playerPosition.hide();
        move.reset();
    }

    @Override
    public boolean needsControl() {
        if(enemy.canSeePlayer()){
            enemy.seenPlayer = true;
            return true;
        }
        else return false;
    }

    @Override
    public void update() {
        move.update();
        enemy.currentSprite = move.getSprite();
        navigator.moveToPoint(enemy.getPlayerLastPosition());
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public void stop() {
        enemy.setSpeed(enemy.speed);
    }
}
