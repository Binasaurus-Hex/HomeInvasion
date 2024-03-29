package objects.gameObjects;

import game.CameraID;
import game.Game;
import game.Main;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Arbitrator;
import objects.gameObjects.behaviour.Navigator;
import objects.gameObjects.behaviour.playerBehaviours.*;
import objects.handlers.MouseHandler;
import objects.interfaces.Drawable;
import objects.handlers.KeyHandler;
import objects.misc.Camera;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends Character {
    private boolean movable = true;
    private Camera camera;
    private boolean ded;

    private Rectangle2D.Double bounds;

    //behaviours
    private Move move;
    private OpenWindow openWindow;
    private CloseWindow closeWindow;
    private Vault vault;
    private Headpat headpat;
    private MoveToActivatable moveToActivatable;
    private Idle idle;
    private Fighting fighting;

    //systems
    public MouseSelector selector;


    public Player(double x, double y, int z, double width, double height, Game game) {
        super(x, y, width,height,0, GameObjectID.Player,game);
        camera = game.cameraMap.get(CameraID.Main);
        velX = 2.5;
        velY = 2.5;
        speed = 2.5;
        bounds = new Rectangle2D.Double((x-(width/4)-5), y-(height/4), (width/2)+10, height/2);

        selector = new MouseSelector(game,game.cameraMap.get(CameraID.Main));

        move = new Move(this);
        openWindow = new OpenWindow(this);
        closeWindow = new CloseWindow(this);
        vault = new Vault(this);
        headpat = new Headpat(this);
        moveToActivatable = new MoveToActivatable(this);
        fighting = new Fighting(this,game);
        idle = new Idle(this);
        arbitrator.addBehaviour(move);
        arbitrator.addBehaviour(openWindow);
        arbitrator.addBehaviour(closeWindow);
        arbitrator.addBehaviour(vault);
        arbitrator.addBehaviour(headpat);
        arbitrator.addBehaviour(moveToActivatable);
        arbitrator.addBehaviour(idle);
        arbitrator.addBehaviour(fighting);
    }

    @Override
    public void update() {
        selector.update();
        if(KeyHandler.isKeyPressed("I")){
            visible = true;
        }
        if(KeyHandler.isKeyPressed("O")){
            visible = false;
        }
        if(KeyHandler.isKeyPressed("L")){
            game.stop();
        }
        camera.setX(x);
        camera.setY(y);
        if(movable&&!ded) {
            if(collidable)collision();
            arbitrator.update();
        }
    }

    public boolean moveToPoint(Point2D.Double point){
        return navigator.moveToPoint(point);
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

    public void kill(){
        if(!ded) {
            PopupLose lose = new PopupLose(0, 0, 0, 0, GameObjectID.Popup, game);
            game.objectHandler.add(lose);
            game.repaint();
            ded=true;
        }
    }



    @Override
    public void render(Graphics g) {
        Drawable player = (graphics)->{
            selector.render(graphics);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.rotate(getRotation(), x, y);
            graphics.drawImage(currentSprite, (int)(x-(width/2)), (int)(y-(width/2)), (int)width, (int)height, null);
            graphics.rotate(-getRotation(), x, y);
        };
        Drawable input = (graphics)->{
            graphics.setColor(Color.RED);
            graphics.drawString(KeyHandler.getTypedKey(), (int)(x),(int)(y-50));
        };
        Drawable debugPos = (graphics)->{
            graphics.setColor(Color.red);
            graphics.drawOval((int)x-5,(int)y-5,10,10);
        };

        Graphics2D g2d = (Graphics2D) g;
        renderToCamera(player, g2d, camera);
        if(Main.debug) {
            renderToCamera(input, g2d, camera);
            renderToCamera(debugPos, g2d, camera);
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
    public void onWindowTouched(Window window) {
        openWindow.setWindowTouched(window);
        closeWindow.setWindowTouched(window);
        moveToActivatable.setActivatable(window);
        vault.setVaultableTouched(window);
    }

    @Override
    public void onAnimeTouched(Anime anime) {
        headpat.setAnimeTouched(anime);
        moveToActivatable.setActivatable(anime);
    }

    @Override
    public void attack() {
        for(GameObject object: game.objectHandler.objects){
            if(object instanceof Character && object.isColliding(getRay(50))){
                Character character = (Character)object;
                Point2D.Double facing = getFacing();
                facing.x = facing.getX()*25;
                facing.y = facing.getY()*25;
                character.onAttacked(facing,100);
            }
        }
    }

    @Override
    public void onAttacked(Point2D.Double force, double damage) {

    }

    @Override
    public boolean isSelectable() {
        return false;
    }
}
