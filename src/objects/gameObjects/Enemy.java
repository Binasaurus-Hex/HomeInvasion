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
    public boolean seenPlayer = false;
    public Illusion playerPosition;
    private Player player;

    //attributes for working out line of sight
    protected Point2D.Double playerLastPosition;
    protected Line2D line;
    public Color color;

    //Behaviours
    protected Explore explore;
    protected Attack attack;
    protected Search search;
    protected OpenWindow openWindow;
    protected Vault vault;
    protected MoveToActivatable moveToActivatable;

    private Rectangle2D.Double bounds;

    public Enemy(int x, int y, Game game, Color color) {
        super(x, y,50,50,0, GameObjectID.Enemy, game);
        bounds = new Rectangle2D.Double(x-width/4, y-height/4, width/2, height/2);
        playerLastPosition = new Point2D.Double();


        explore = new Explore(this);
        attack = new Attack(this);
        search = new Search(this,game);
        openWindow = new OpenWindow(this);
        vault = new Vault(this);
        moveToActivatable = new MoveToActivatable(this);
        this.player = getPlayer();
        playerPosition = new Illusion(player.x,player.y,player,game);
        game.objectHandler.add(playerPosition);
    }


    public boolean canSeePlayer() {
        if(playerPosition == null)return false;
        double playerDistance = getPoint().distance(playerPosition.getPoint());
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
            if(!player.visible)return false;
            return hasLineOfSight(player);
        }catch (Exception e){
            return false;
        }
    }

    protected Player getPlayer(){
        for(GameObject object : game.objectHandler.objects) {
            if(object.id == GameObjectID.Player) {
                return (Player)object;
            }
        }
        return null;
    }

    public void setPlayerLastPosition(){
        playerPosition.setPosition(player.getPoint());
    }

    public Point2D.Double getPlayerLastPosition(){
        return playerPosition.getPoint();
    }

    @Override
    public Rectangle2D.Double getBounds() {
        bounds.setRect(x-width/4, y-height/4, width/2, height/2);
        return bounds;
    }
}
