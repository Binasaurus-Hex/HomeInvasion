package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.gameObjects.behaviour.Arbitrator;
import objects.gameObjects.behaviour.Navigator;
import objects.gameObjects.behaviour.petBehaviours.FollowPlayer;
import objects.gameObjects.behaviour.petBehaviours.Idle;
import objects.interfaces.Drawable;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pet extends Character {
    public Navigator navigator;
    private Arbitrator arbitrator;
    private FollowPlayer followPlayer;
    private Idle idle;
    private int width,height;

    public Pet(double x, double y, Player master, Game game) {
        super(x, y, 0, 0, GameObjectID.Pet, game);
        navigator = new Navigator(this,game);
        navigator.setTargetDistance(20);
        arbitrator = new Arbitrator();
        followPlayer = new FollowPlayer(this,master);
        idle = new Idle(this);
        arbitrator.addBehaviour(followPlayer);
        arbitrator.addBehaviour(idle);
        width = 40;
        height = 40;
        velX = 10;
        velY = 10;
    }

    @Override
    public void update() {
        arbitrator.update();
    }

    @Override
    public void render(Graphics g) {
        Drawable drawable = (graphics)->{
            graphics.drawImage(currentSprite,(int)(x-width/2),(int)(y-height/2),width,height,null);
        };
        Graphics2D g2d = (Graphics2D)g;
        renderToCamera(drawable,g2d,game.cameraMap.get(CameraID.Main));
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
}
