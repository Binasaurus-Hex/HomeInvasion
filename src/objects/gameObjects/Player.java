package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.gameObjects.Windows.Window;
import objects.interfaces.Character;
import objects.interfaces.Drawable;
import objects.handlers.KeyHandler;
import objects.FileIO.BufferedImageLoader;
import objects.misc.Camera;
import objects.misc.animation.Animation;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends GameObject implements Character {
    public double width, height;
    private boolean movable = true;
    private Camera camera;
    private boolean moving;
    private boolean ded;
    public boolean visible = false;

    private final int MAXDETECT=300;
    private final int TRANSITIONTIME=1;

    private int moveTime = 0;
    public int moveState = 1;
    public Map<Integer, BufferedImage> spriteMap;


    public Player(double x, double y, int z, double width, double height, Game game) {
        super(x, y, z,0, GameObjectID.Player,game);
        camera = game.cameraMap.get(CameraID.Main);
        this.width = width;
        this.height = height;
        velX = 2.5;
        velY = 2.5;
        speed = 2.5;
        spriteMap = new HashMap<>();
        BufferedImageLoader loader = new BufferedImageLoader();
        spriteMap.put(0, loader.loadImage("/sprites/player/1.png"));
        spriteMap.put(1, loader.loadImage("/sprites/player/2.png"));
        spriteMap.put(2, loader.loadImage("/sprites/player/3.png"));
        spriteMap.put(3, loader.loadImage("/sprites/player/4.png"));
        spriteMap.put(4, loader.loadImage("/sprites/player/5.png"));
        spriteMap.put(5, loader.loadImage("/sprites/player/6.png"));
        spriteMap.put(6, loader.loadImage("/sprites/player/7.png"));
        spriteMap.put(7, loader.loadImage("/sprites/player/8.png"));
        spriteMap.put(8, loader.loadImage("/sprites/player/9.png"));
        spriteMap.put(9, loader.loadImage("/sprites/player/10.png"));

        Animation a = new Animation("C:\\Users\\White Wolf\\Documents\\Uni Work Year 2\\Java\\HomeInvasion\\res\\sprites\\player",null,null,0);
    }

    @Override
    public void update() {
        camera.setX(x);
        camera.setY(y);
        if(movable&&!ded) {
            collision();
            move();
        }
        if(moving){
        }else{
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
                        Window window = (Window)object;
                        if(window.isClosed()){
                            resolveCollision(window);
                        }
                        break;
                }
            }
        }
    }

    private void move() {
        if(KeyHandler.isKeyPressed("I")){
            visible = true;
        }
        if(KeyHandler.isKeyPressed("P")){
            visible = false;
        }
        if(KeyHandler.isKeyPressed("W") || KeyHandler.isKeyPressed("S") || KeyHandler.isKeyPressed("A") || KeyHandler.isKeyPressed("D")) {
            moving = true;
            moveTime++;
            if(moveTime <= 50) {
                if(moveTime == 5) {
                    moveState = 1;
                } else if(moveTime == 10) {
                    moveState = 2;
                } else if(moveTime == 15) {
                    moveState = 3;
                } else if(moveTime == 20) {
                    moveState = 4;
                } else if(moveTime == 25) {
                    moveState = 5;
                } else if(moveTime == 30) {
                    moveState = 6;
                } else if(moveTime == 35) {
                    moveState = 7;
                } else if(moveTime == 40) {
                    moveState = 8;
                } else if(moveTime == 45) {
                    moveState = 9;
                }
            } else {
                moveState = 0;
                moveTime = 0;
            }
        } else {
            moving = false;
        }
        if(KeyHandler.isKeyPressed("W")){
            y-=velY;
            setRotation(-1.5);
        }
        if(KeyHandler.isKeyPressed("S")){
            y+=velY;
            setRotation(1.5);
        }
        if(KeyHandler.isKeyPressed("A")){
            x-=velX;
            setRotation(-3);
        }
        if(KeyHandler.isKeyPressed("D")){
            x+=velX;
            setRotation(0);
        }

        if(KeyHandler.isKeyPressed("M")){
            game.objectHandler.clear();
            game.stateHandler.init(game);
        }

        if(KeyHandler.isKeyPressed("W") && KeyHandler.isKeyPressed("D")) {
            setRotation(-0.75);
        } else if(KeyHandler.isKeyPressed("S") && KeyHandler.isKeyPressed("D")) {
            setRotation(0.75);
        } else if(KeyHandler.isKeyPressed("W") && KeyHandler.isKeyPressed("A")) {
            setRotation(-2.5);
        } else if(KeyHandler.isKeyPressed("S") && KeyHandler.isKeyPressed("A")) {
            setRotation(2.5);
        }
    }

    public void kill(){
        if(!ded) {
            moving = false;
            PopupLose lose = new PopupLose(0, 0, 0, 0, GameObjectID.Popup, game);
            game.objectHandler.add(lose);
            game.repaint();
            ded=true;
        }
    }

    @Override
    public void render(Graphics g) {
        Drawable player = (graphics)->{
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.rotate(getRotation(), x, y);
            if(moving) {
                graphics.drawImage(spriteMap.get(moveState), (int)(x-(width/2)), (int)(y-(width/2)), (int)width, (int)height, null);
            } else {
                graphics.drawImage(spriteMap.get(0), (int)(x-(width/2)), (int)(y-(width/2)), (int)width, (int)height, null);
            }
            graphics.rotate(-getRotation(), x, y);
        };
        Drawable debugPos = (graphics)->{
            graphics.setColor(Color.red);
            graphics.drawOval((int)x-5,(int)y-5,10,10);
        };

        Graphics2D g2d = (Graphics2D) g;
        renderToCamera(player, g2d, camera);
        //renderToCamera(debugPos,g2d,camera);
    }

    @Override
    public Rectangle2D.Double getBounds() {
        if(getRotation() == 0 || getRotation() == -3) {
            return new Rectangle2D.Double(x-(width/4), (y-(height/4))-5, width/2, (height/2)+10);
        }
        return new Rectangle2D.Double((x-(width/4)-5), y-(height/4), (width/2)+10, height/2);
    }

    @Override
    public void onWindowTouched(Window window) {

    }
}
