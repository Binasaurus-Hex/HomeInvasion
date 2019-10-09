package objects.gameObjects.behaviour.EnemyAI;

import game.Game;
import objects.gameObjects.Enemy;
import objects.gameObjects.Illusion;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;
import objects.misc.Grid;

import java.awt.geom.Point2D;

public class Search implements Behaviour {
    Navigator navigator;
    Grid grid;
    Enemy enemy;
    Game game;

    public Search(Enemy enemy,Game game){
        this.enemy = enemy;
        navigator = enemy.navigator;
        grid = navigator.grid;
        this.game = game;
    }


    @Override
    public void start() {
        navigator.setGoal(enemy.playerPosition.getPoint());
        enemy.playerPosition.show();
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
            enemy.playerPosition.hide();
        }
        navigator.update();
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public void stop() {
        enemy.playerPosition.hide();
    }
}
