package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;
import objects.misc.Grid;
import objects.misc.animation.Animation;

import java.awt.geom.Point2D;

public class Explore implements Behaviour {
    private Navigator navigator;
    private Grid grid;
    private Enemy enemy;
    private Animation move;

    public Explore(Enemy enemy){
        this.enemy = enemy;
        navigator = enemy.navigator;
        grid = navigator.grid;
        move = new Animation("/sprites/enemy/moving",10,10);
    }

    @Override
    public void start() {
        Point2D.Double facing = enemy.getFacing();
        facing.setLocation(enemy.getX()+(facing.getX()*20),enemy.getY()+(facing.getY()*20));
        navigator.setGoal(facing,grid.getRandomGoal(grid.getNearestJunction(enemy.getPoint())).getPoint());
        enemy.playerPosition.hide();
        move.reset();
    }

    @Override
    public boolean needsControl() {
        return true;
    }

    @Override
    public void update() {
        move.update();
        enemy.currentSprite = move.getSprite();
        navigator.update();
        if(navigator.reachedGoal()) {
            start();
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
