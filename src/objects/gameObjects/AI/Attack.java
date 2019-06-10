package objects.gameObjects.AI;

import objects.gameObjects.Enemy;
import objects.misc.Grid;

public class Attack implements Behaviour{
    private Enemy enemy;
    private Grid grid;
    private Navigator navigator;

    public Attack(Enemy enemy){
        this.enemy = enemy;
        this.navigator = enemy.navigator;
        this.grid = navigator.grid;
    }

    @Override
    public void start() {
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
        enemy.setLastPlayerPosition();
        navigator.moveToPoint(enemy.getLastPlayerPosition());
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public void stop() {
    }
}
