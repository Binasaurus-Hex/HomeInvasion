package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.interfaces.Drawable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Illusion extends GameObject {

    private BufferedImage image;
    private double width,height;

    public Illusion(double x, double y,double width,double height, double rotation, BufferedImage image, Game game) {
        super(x, y, 0, rotation, GameObjectID.Illusion, game);
        this.image = tint(image,Color.blue);
        this.width = width;
        this.height = height;
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

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        Drawable illusion = graphics -> {
            graphics.rotate(getRotation(),x,y);
            graphics.drawImage(image, (int)(x-(width/2)), (int)(y-(width/2)), (int)width, (int)height, null);
        };
        Graphics2D g2d = (Graphics2D)g;
        renderToCamera(illusion,g2d,game.cameraMap.get(CameraID.Main));
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
}
