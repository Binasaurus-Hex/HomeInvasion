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
            return true;
        }
        else return false;
    }

    @Override
    public void update() {
        enemy.setLastPlayerPosition();
        if(enemy.getLastPlayerPosition().junction){
            navigator.moveToPoint(enemy.getLastPlayerPosition().getPoint());
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
