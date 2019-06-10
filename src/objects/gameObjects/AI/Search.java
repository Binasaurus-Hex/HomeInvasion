package objects.gameObjects.AI;

import objects.gameObjects.Enemy;
import objects.misc.Grid;

public class Search implements Behaviour {
    Navigator navigator;
    Grid grid;
    Enemy enemy;


    public Search(Enemy enemy){
        this.enemy = enemy;
        navigator = enemy.navigator;
        grid = navigator.grid;
    }


    @Override
    public void start() {
        navigator.setGoal(grid.getNearestJunction(enemy.getLastPlayerPosition()).getPoint());
    }

    @Override
    public boolean needsControl() {
        if(!enemy.canSeePlayer() && enemy.seenPlayer){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void update() {
        if(navigator.reachedGoal()){
            enemy.seenPlayer = false;
        }
        navigator.update();
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public void stop() {
    }
}
