package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.interfaces.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Illusion extends GameObject {

    private boolean visible = false;
    private Character character;
    private BufferedImage image;
    private double rotation = 0;

    public Illusion(double x, double y, Character character, Game game) {
        super(x, y, 0, character.getRotation(), GameObjectID.Illusion, game);
        this.character = character;
    }

    public BufferedImage tint(BufferedImage image, Color color) {
        BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y), true);
                int r = (pixelColor.getRed() + color.getRed()) / 2;
                int g = (pixelColor.getGreen() + color.getGreen()) / 2;
                int b = (pixelColor.getBlue() + color.getBlue()) / 2;
                int a = pixelColor.getAlpha();
                int rgba = (a << 24) | (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, rgba);
            }
        }
        return newImage;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setPosition(Point2D.Double point){
        this.x = point.getX();
        this.y = point.getY();
        image = tint(character.currentSprite,Color.blue);
        rotation = character.getRotation();
    }

    public void show(){
        visible = true;

    }

    public void hide(){
        visible = false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        Drawable illusion = graphics -> {
            graphics.rotate(rotation,x,y);
            graphics.drawImage(image, (int)(x-(character.width/2)), (int)(y-(character.height/2)), (int)character.width, (int)character.height, null);
        };
        Graphics2D g2d = (Graphics2D)g;
        if(visible){
            renderToCamera(illusion,g2d,game.cameraMap.get(CameraID.Main));
        }
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
}
