package objects.gameObjects;

import game.CameraID;
import game.Game;
import game.Main;
import objects.gameObjects.behaviour.Arbitrator;
import objects.gameObjects.behaviour.Navigator;
import objects.gameObjects.behaviour.petBehaviours.FollowPlayer;
import objects.gameObjects.behaviour.petBehaviours.Idle;
import objects.gameObjects.behaviour.petBehaviours.SitDown;
import objects.gameObjects.behaviour.petBehaviours.SitUp;
import objects.interfaces.Drawable;
import physics.MathsMethods;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Pet extends Character {

    //<editor-fold desc="behaviours">
    private FollowPlayer followPlayer;
    private SitUp sitUp;
    private SitDown sitDown;
    private Idle idle;
    //</editor-fold>
    public boolean sitting = true;
    private Rectangle2D.Double bounds;
    private boolean moving = false;
    public final int comfortDistance = 100;

    public Pet(double x, double y, Player master, Game game) {
        super(x, y, 40,40, 0, GameObjectID.Pet, game);

        navigator.setGoalDistance(30);
        navigator.setTargetDistance(5);

        //<editor-fold desc ="behaviours initialization">
        followPlayer = new FollowPlayer(this,master);
        idle = new Idle(this,master);
        sitUp = new SitUp(this,master);
        sitDown = new SitDown(this,master);
        arbitrator.addBehaviour(followPlayer);
        arbitrator.addBehaviour(idle);
        arbitrator.addBehaviour(sitUp);
        arbitrator.addBehaviour(sitDown);
        //</editor-fold>

        velX = 3;
        velY = 3;
        bounds = new Rectangle2D.Double(x,y,width,height);
    }

    @Override
    public void update() {
        arbitrator.update();
        collision();
    }

    @Override
    public void render(Graphics g) {
        Drawable drawable = (graphics)->{
            graphics.rotate(getRotation(),x,y);
            graphics.drawImage(currentSprite,(int)(x-width),(int)(y-height),(int)width*2,(int)height*2,null);
        };
        Drawable debug = (graphics)->{
            graphics.setColor(Color.red);
            graphics.draw(bounds);
        };
        Graphics2D g2d = (Graphics2D)g;
        renderToCamera(drawable,g2d,game.cameraMap.get(CameraID.Main));
        if(Main.debug){
            renderToCamera(debug,g2d,game.cameraMap.get(CameraID.Main));
        }
    }

    private void collision() {
        for(GameObject object : game.objectHandler.objects) {
            if(this.isColliding(object)){
                switch (object.id){
                    case Wall:
                        resolveCollision(object);
                        break;
                    case Door:
                        resolveCollision(object);
                        break;
                    case Window:
                        resolveCollision(object);
                        break;
                }
            }
        }
    }

    @Override
    public Rectangle2D.Double getBounds() {
        if(getRotation() == 0 || getRotation() == -3) {
            bounds.setRect(x-(width/4), (y-(height/4))-5, width/2, (height/2)+10);
        }
        else{
            bounds.setRect((x-(width/4)-5), y-(height/4), (width/2)+10, height/2);
        }
        return bounds;
    }

    @Override
    public Point2D.Double getPoint() {
        return new Point2D.Double(x,y);
    }
}
