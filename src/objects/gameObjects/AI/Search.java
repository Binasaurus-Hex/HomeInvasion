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

    }

    @Override
    public boolean needsControl() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {

    }
}
