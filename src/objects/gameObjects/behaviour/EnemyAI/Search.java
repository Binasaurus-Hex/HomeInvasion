package objects.gameObjects.behaviour.EnemyAI;

import game.Game;
import objects.gameObjects.Enemy;
import objects.gameObjects.Illusion;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.misc.Grid;

import java.awt.geom.Point2D;

public class Search implements Behaviour {
    Navigator navigator;
    Grid grid;
    Enemy enemy;
    Game game;
    Illusion illusion;


    public Search(Enemy enemy,Game game){
        this.enemy = enemy;
        navigator = enemy.navigator;
        grid = navigator.grid;
        this.game = game;
    }


    @Override
    public void start() {
        Player player = enemy.getPlayer();
        Point2D.Double playerPos = enemy.getLastPlayerPosition();

        navigator.setGoal(grid.getNearestJunction(playerPos).getPoint());
        illusion = new Illusion(playerPos.x,playerPos.y,player.width,player.height,player.getRotation(),player.currentSprite,game);
        game.objectHandler.add(illusion);
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
        game.objectHandler.remove(illusion);
    }
}
