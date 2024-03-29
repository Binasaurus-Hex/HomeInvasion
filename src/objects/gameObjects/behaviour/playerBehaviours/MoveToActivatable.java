package objects.gameObjects.behaviour.playerBehaviours;

import javafx.util.Pair;
import objects.gameObjects.Character;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.handlers.KeyBindings;
import objects.handlers.KeyHandler;
import objects.interfaces.Activatable;
import objects.interfaces.ActivatableListener;
import objects.interfaces.Vaultable;

import java.awt.geom.Point2D;
import java.util.List;

public class MoveToActivatable implements Behaviour, ActivatableListener {
    protected Character character;
    protected Activatable activatable;
    protected boolean activated = false;
    protected Point2D.Double anchor;
    protected double rotation;

    public MoveToActivatable(Character character){
        this.character = character;
    }


    @Override
    public void start() {
        List<Pair<Point2D.Double,Double>> anchorPairs = activatable.getAnchors();
        List<Pair<Point2D.Double,Double>> sortedPairs = HelperFunctions.getOrderedAnchorPairs(character.getPoint(),anchorPairs);

        anchor = sortedPairs.get(0).getKey();
        rotation = sortedPairs.get(0).getValue();
    }

    @Override
    public boolean needsControl() {
        if(activatable != null){
            if(KeyHandler.isKeyPressed(KeyBindings.VAULT)){
                if(activatable instanceof Vaultable){
                    activated = true;
                    return true;
                }
            }
            if(KeyHandler.isKeyPressed(KeyBindings.INTERACT) || activated){
                activated = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!character.navigator.moveToPoint(anchor));
        else {
            character.setRotation(rotation);
            activated = false;
        }
    }

    @Override
    public int getPriority() {
        return 4;
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
