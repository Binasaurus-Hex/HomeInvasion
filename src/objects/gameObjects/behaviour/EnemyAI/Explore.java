package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;
import objects.misc.Grid;

public class Explore implements Behaviour {
    private Navigator navigator;
    private Grid grid;
    private Enemy enemy;

    public Explore(Enemy enemy){
        this.enemy = enemy;
        navigator = enemy.navigator;
        grid = navigator.grid;
    }

    @Override
    public void start() {
        navigator.setGoal(grid.getRandomGoal(grid.getNearestJunction(enemy.getPoint())).getPoint());
        enemy.playerPosition.hide();
    }

    @Override
    public boolean needsControl() {
        return true;
    }

    @Override
    public void update() {
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
