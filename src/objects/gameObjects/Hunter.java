package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.interfaces.Drawable;
import objects.misc.BufferedImageLoader;
import objects.misc.PathGenerator;
import objects.misc.PathList;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Hunter extends Enemy {
    BufferedImage sprite;

    public Hunter(int x, int y, Game game) {
        super(x, y, game);
        velX = 2;
        velY = 2;
        BufferedImageLoader loader = new BufferedImageLoader();
        sprite = loader.loadImage("/sprites/enemy/enemy.png");
        arbitrator.addBehaviour(explore);
        arbitrator.addBehaviour(attack);
        arbitrator.addBehaviour(search);
    }

    @Override
    public void update() {
        collision();
        arbitrator.update();


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
                        //player.kill();
                        break;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Drawable enemy = (graphics)->{
            graphics.setColor(Color.blue);
            graphics.rotate(getRotation(),x,y);
            graphics.drawImage(sprite, (int)(x-width/2),(int)(y-height/2),(int)width,(int)height, null);
        };
        Graphics2D g2d = (Graphics2D)g;
        renderToCamera(enemy,g2d,game.cameraMap.get(CameraID.Main));

    }

}
