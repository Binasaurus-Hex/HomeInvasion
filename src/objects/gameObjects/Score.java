package objects.gameObjects;

import game.CameraID;
import game.Game;
import objects.interfaces.Drawable;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Score extends GameObject{
    private double time = 0;
    private Font font;
    public Score(double x, double y,Game game) {
        super(x, y, 0,0, GameObjectID.Score, game);
        font = new Font("Impact",Font.BOLD,50);
    }

    @Override
    public void update() {
        time += 1.0/60;
    }

    @Override
    public void render(Graphics g) {
        Drawable score = (graphics)->{
            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString(String.valueOf("Score = "+Math.round(time)),(int)x,(int)y);
        };
        Graphics2D g2d = (Graphics2D)g;
        renderToCamera(score,g2d,game.cameraMap.get(CameraID.Screen));
    }

    public int getScore(){
        return (int) Math.round(time);
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
}
