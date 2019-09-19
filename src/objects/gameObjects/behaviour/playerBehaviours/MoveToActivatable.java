package objects.gameObjects.behaviour.playerBehaviours;

import javafx.util.Pair;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.handlers.KeyHandler;
import objects.interfaces.Activatable;
import objects.interfaces.ActivatableListener;

import java.awt.geom.Point2D;
import java.util.List;

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
        List<Pair<Point2D.Double,Double>> anchorPairs = activatable.getAnchors();
        List<Pair<Point2D.Double,Double>> sortedPairs = HelperFunctions.getOrderedAnchorPairs(player.getPoint(),anchorPairs);

        anchor = sortedPairs.get(0).getKey();
        rotation = sortedPairs.get(0).getValue();
    }

    @Override
    public boolean needsControl() {
        if(activatable != null){
            if(KeyHandler.isKeyPressed("F") || KeyHandler.isKeyPressed("Space") || activated){
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
