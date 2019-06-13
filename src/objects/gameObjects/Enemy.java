package objects.gameObjects;

import game.Game;
import objects.gameObjects.AI.*;
import objects.misc.PathGenerator;
import objects.misc.PathList;
import physics.MathsMethods;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Enemy extends GameObject {
    protected double width,height;
    public boolean seenPlayer = false;

    //attributes for working out line of sight
    protected Point2D.Double playerLastPosition;
    protected Line2D line;
    public Color color;

    public Navigator navigator;

    //Behaviours
    protected Arbitrator arbitrator;
    protected Explore explore;
    protected Attack attack;
    protected Search search;

    public Enemy(int x, int y, Game game, Color color) {
        super(x, y, 1, 0, GameObjectID.Enemy, game);
        width = 50;
        height = 50;
        playerLastPosition = new Point2D.Double();
        navigator = new Navigator(this,game);

        arbitrator = new Arbitrator();
        explore = new Explore(this);
        attack = new Attack(this);
        search = new Search(this);
    }


    public boolean canSeePlayer() {
        Point2D.Double playerPosition = getPlayer().getPoint();
        double playerDistance = getPoint().distance(playerPosition);
        if(isSightClear() && playerDistance < 5000) {
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * works out if there are any walls in between the enemy and the player
     * @return
     */
    public boolean isSightClear(){
        try{
            Player player = getPlayer();
            line = new Line2D.Double(x,y,player.x,player.y);
            for(GameObject object : game.objectHandler.objects) {
                if(object.id == GameObjectID.Wall || object.id == GameObjectID.Door) {
                    if(line.intersects(object.getBounds())) {
                        return false;
                    }
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * sets the last known position of the player
     */
    public void setLastPlayerPosition(){
        Player player = getPlayer();
        playerLastPosition.setLocation(player.getX(), player.getY());
    }

    public Point2D.Double getLastPlayerPosition(){
        return playerLastPosition;
    }

    protected Player getPlayer(){
        for(GameObject object : game.objectHandler.objects) {
            if(object.id == GameObjectID.Player) {
                return (Player)object;
            }
        }
        return null;
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x-width/4, y-height/4, width/2, height/2);
    }
}
