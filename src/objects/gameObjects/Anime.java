package objects.gameObjects;

import game.CameraID;
import game.Game;
import javafx.util.Pair;
import objects.interfaces.Activatable;
import objects.interfaces.Character;
import objects.interfaces.Drawable;
import objects.misc.animation.Animation;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Anime extends GameObject implements Activatable {
    private ActivatableBounds activatableBounds;
    private double width,height;
    private Animation headpat;
    private boolean patting = false;
    private BufferedImage currentSprite;
    private Point2D.Double anchor;
    private double facing;

    public Anime(double x, double y, Game game) {
        super(x, y, 0, 0, GameObjectID.Anime, game);
        width = 60;
        height = 60;
        anchor = new Point2D.Double(x+width-5,y+(height/2));
        facing = -3;
        activatableBounds = new ActivatableBounds(x,y,width,height,game);
        game.objectHandler.add(activatableBounds);
        headpat = new Animation("/sprites/weeb/headpat",10,4);
        currentSprite = headpat.getSprite();
        activatableBounds.addObjectID(GameObjectID.Player);
        activatableBounds.addObjectID(GameObjectID.Enemy);
        activatableBounds.setOnEnter(o -> {
            Character character = (Character)o;
            character.onAnimeTouched(this);
        });
        activatableBounds.setOnExit(o -> {
            Character character = (Character)o;
            setPatting(false);
            character.onAnimeTouched(null);
        });
    }

    @Override
    public void update() {
        if(patting){
            System.out.println("patting");
            headpat.update();
        }
        else{
            headpat.reset();
        }
    }

    @Override
    public void render(Graphics g) {
        currentSprite = headpat.getSprite();
        Drawable sprite = (graphics) -> {
            graphics.drawImage(currentSprite,(int)x,(int)y,(int)width,(int)height,null);
            graphics.setColor(Color.red);
        };
        renderToCamera(sprite, (Graphics2D)g, game.cameraMap.get(CameraID.Main));
    }

    public void setPatting(boolean value){
        patting = value;
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }

    @Override
    public List<Pair<Point2D.Double, Double>> getAnchors() {
        List<Pair<Point2D.Double,Double>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(anchor,facing));
        return pairs;
    }
}
