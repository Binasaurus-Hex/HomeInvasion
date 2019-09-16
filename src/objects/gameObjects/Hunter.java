package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.interfaces.WindowListener;
import objects.gameObjects.Windows.Window;
import objects.interfaces.Drawable;
import objects.FileIO.BufferedImageLoader;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Hunter extends Enemy {
    BufferedImage sprite;
    List<WindowListener> windowListeners;

    public Hunter(int x, int y, Game game, Color color) {
        super(x, y, game,color);
        this.color = color;
        velX = 2;
        velY = 2;
        speed = 2;
        BufferedImageLoader loader = new BufferedImageLoader();
        sprite = loader.loadImage("/sprites/enemy/walking/enemy.png");
        arbitrator.addBehaviour(explore);
        arbitrator.addBehaviour(attack);
        arbitrator.addBehaviour(search);
        arbitrator.addBehaviour(openWindow);
        arbitrator.addBehaviour(vault);

        windowListeners = new ArrayList<>();
        windowListeners.add(openWindow);
        windowListeners.add(vault);
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
                    case Door:
                        resolveCollision(object);
                        break;
                    case Window:
                        Window window = (Window)object;
                        if(window.isClosed()){
                            resolveCollision(window);
                        }
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
            graphics.drawImage(sprite, (int)(x-width/2),(int)(y-height/2),(int)width,(int)height, null);
        };
        Graphics2D g2d = (Graphics2D)g;
        renderToCamera(enemy,g2d,game.cameraMap.get(CameraID.Main));
        //renderToCamera(debugPos,g2d,game.cameraMap.get(CameraID.Main));

    }

    @Override
    public void onWindowTouched(Window window) {
        for(WindowListener i : windowListeners){
            i.setWindowTouched(window);
        }
    }

    @Override
    public void onAnimeTouched(Anime anime) {

    }
}
