package objects.gameObjects.AI;

import objects.gameObjects.Enemy;
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
        System.out.println("sets");
        navigator.setGoal(grid.getRandomGoal(grid.getNearestNode(enemy.getPoint())).getPoint());
    }

    @Override
    public boolean needsControl() {
        return true;
    }

    @Override
    public void update() {

        if(navigator.reachedGoal()) {
            navigator.setGoal(grid.getRandomGoal(grid.getNearestNode(navigator.currentPos.getPoint())).getPoint());
        }
        navigator.update();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {

    }
}
