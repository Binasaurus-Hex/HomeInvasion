package objects.gameObjects.behaviour.EnemyAI;

import game.Game;
import objects.gameObjects.Enemy;
import objects.gameObjects.Illusion;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.Navigator;
import objects.misc.Grid;
import objects.misc.animation.Animation;

import java.awt.geom.Point2D;

public class Search implements Behaviour {
    Navigator navigator;
    Grid grid;
    Enemy enemy;
    Game game;
    private Animation move;

    public Search(Enemy enemy,Game game){
        this.enemy = enemy;
        navigator = enemy.navigator;
        grid = navigator.grid;
        this.game = game;
        move = new Animation("/sprites/enemy/moving",10,10);
    }


    @Override
    public void start() {
        Point2D.Double facing = enemy.getFacing();
        facing.setLocation(enemy.getX()+(facing.getX()*20),enemy.getY()+(facing.getY()*20));
        navigator.setGoal(facing,enemy.playerPosition.getPoint());
        enemy.playerPosition.show();
        move.reset();
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
        move.update();
        enemy.currentSprite = move.getSprite();
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public void stop() {
        
    }
}
