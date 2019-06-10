package objects.gameObjects;

import game.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PlayerIllusion extends GameObject {


    public PlayerIllusion(double x, double y, int z, double rotation, GameObjectID id, Game game) {
        super(x, y, z, rotation, id, game);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle2D.Double getBounds() {
        return null;
    }
}
