package objects.gameObjects;

import game.Game;
import objects.gameObjects.behaviour.EnemyAI.*;
import objects.gameObjects.behaviour.Arbitrator;
import objects.gameObjects.behaviour.Navigator;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Enemy extends Character {
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
    protected OpenWindow openWindow;
    protected Vault vault;

    private Rectangle2D.Double bounds;

    public Enemy(int x, int y, Game game, Color color) {
        super(x, y, 1, 0, GameObjectID.Enemy, game);
        width = 50;
        height = 50;
        bounds = new Rectangle2D.Double(x-width/4, y-height/4, width/2, height/2);
        playerLastPosition = new Point2D.Double();
        navigator = new Navigator(this,game);

        arbitrator = new Arbitrator();
        explore = new Explore(this);
        attack = new Attack(this);
        search = new Search(this,game);
        openWindow = new OpenWindow(this);
        vault = new Vault(this);
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
     * works out if there are any walls in between the enemy and the playerBehaviours
     * @return
     */
    public boolean isSightClear(){
        try{
            Player player = getPlayer();
            if(!player.visible)return false;
            return hasLineOfSight(player);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * sets the last known position of the playerBehaviours
     */
    public void setLastPlayerPosition(){
        Player player = getPlayer();
        playerLastPosition.setLocation(player.getX(), player.getY());
    }

    public Point2D.Double getLastPlayerPosition(){
        return playerLastPosition;
    }

    public Player getPlayer(){
        for(GameObject object : game.objectHandler.objects) {
            if(object.id == GameObjectID.Player) {
                return (Player)object;
            }
        }
        return null;
    }

    @Override
    public Rectangle2D.Double getBounds() {
        bounds.setRect(x-width/4, y-height/4, width/2, height/2);
        return bounds;
    }
}
