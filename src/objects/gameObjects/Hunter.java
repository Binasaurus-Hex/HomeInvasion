package objects.gameObjects;

import game.CameraID;
import game.Game;
import game.Main;
import objects.interfaces.WindowListener;
import objects.gameObjects.Windows.Window;
import objects.interfaces.Drawable;
import objects.FileIO.BufferedImageLoader;


import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Hunter extends Enemy {

    public Hunter(int x, int y, Game game, Color color) {
        super(x, y, game,color);
        this.color = color;
        speed = 2;
        velX = 2;
        velY = 2;
        BufferedImageLoader loader = new BufferedImageLoader();
        currentSprite = loader.loadImage("/sprites/enemy/moving/1.png");
        arbitrator.addBehaviour(explore);
        arbitrator.addBehaviour(attack);
        arbitrator.addBehaviour(search);
        arbitrator.addBehaviour(openWindow);
        arbitrator.addBehaviour(vault);
        arbitrator.addBehaviour(moveToActivatable);

    }

    @Override
    public void update() {
        if(canSeePlayer())setPlayerLastPosition();
        arbitrator.update();
        if(collidable)collision();
    }

    @Override
    public void setRotation(double rotation) {
        super.setRotation(rotation);

    }

    private void collision() {
        for(GameObject object : game.objectHandler.objects){
            if(this.isColliding(object)){
                switch (object.id){
                    case Wall:
                        resolveCollision(object);
                        break;
                    case Player:
                        Player player = (Player)object;
                        player.kill();
                        break;
                    case Door:
                        resolveCollision(object);
                        break;
                    case Window:
                        resolveCollision(object);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Drawable debugPos = (graphics)->{
            graphics.setColor(Color.red);
            graphics.drawOval((int)x-5,(int)y-5,10,10);
        };
        Drawable enemy = (graphics)->{
            graphics.setColor(Color.blue);
            graphics.rotate(getRotation(),x,y);
            graphics.drawImage(currentSprite, (int)(x-width/2),(int)(y-height/2),(int)width,(int)height, null);
        };
        Graphics2D g2d = (Graphics2D)g;
        renderToCamera(enemy,g2d,game.cameraMap.get(CameraID.Main));
        if(Main.debug){
            renderToCamera(debugPos,g2d,game.cameraMap.get(CameraID.Main));
        }
    }

    @Override
    public void onWindowTouched(Window window) {
        moveToActivatable.setActivatable(window);
        openWindow.setWindowTouched(window);
        vault.setVaultableTouched(window);
    }

    @Override
    public void onAnimeTouched(Anime anime) {

    }

    @Override
    public void attack() {
        for(GameObject object: game.objectHandler.objects){
            if(object.isColliding(getRay(100)) && object instanceof Character){
                Character character = (Character)object;
                character.onAttacked(getFacing(),100);
            }
        }
    }

    @Override
    public void onAttacked(Point2D.Double force, double damage) {

    }

    @Override
    public boolean isSelectable() {
        return true;
    }
}
