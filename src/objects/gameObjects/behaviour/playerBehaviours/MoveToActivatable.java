package objects.gameObjects.behaviour.playerBehaviours;

import javafx.util.Pair;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.handlers.KeyHandler;
import objects.interfaces.Activatable;
import objects.interfaces.ActivatableListener;

import java.awt.geom.Point2D;

public class MoveToActivatable implements Behaviour, ActivatableListener {
    private Player player;
    private Activatable activatable;
    private boolean activated = false;
    private Point2D.Double anchor;
    private double rotation;

    public MoveToActivatable(Player player){
        this.player = player;

    }


    @Override
    public void start() {
        Pair<Point2D.Double,Double> anchorPair = activatable.getAnchor();
        anchor = anchorPair.getKey();
        rotation = anchorPair.getValue();
    }

    @Override
    public boolean needsControl() {
        if(activatable != null){
            if(KeyHandler.isKeyPressed("F") || activated){
                activated = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!player.moveToPoint(anchor));
        else {
            player.setRotation(rotation);
            activated = false;
        }
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public void stop() {
        activated = false;
    }

    @Override
    public void setActivatable(Activatable activatable) {
        this.activatable = activatable;
    }
}
