package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.handlers.KeyHandler;
import objects.interfaces.Vaultable;
import objects.interfaces.VaultableListener;

import java.awt.geom.Point2D;

public class MoveToVaultable implements Behaviour, VaultableListener {
    private Player player;
    private Vaultable vaultable;
    private Point2D.Double start;
    private Point2D.Double end;
    private boolean activated;

    public MoveToVaultable(Player player){
        this.player = player;
    }

    @Override
    public void start() {
        System.out.println("Move To vaultable object");
        Point2D.Double[] anchors = vaultable.getAnchorPoints();
        Point2D.Double[] orderedAnchors = HelperFunctions.getOrderedAnchors(player.getPoint(),anchors);
        start = orderedAnchors[0];
        end = orderedAnchors[1];
        activated = true;
    }

    @Override
    public boolean needsControl() {
        if(this.vaultable != null){
            if(KeyHandler.isKeyPressed("Space")||KeyHandler.isKeyPressed("F")||activated){
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!player.moveToAnchorPoint(start,end));
        else {
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
    public void setVaultableTouched(Vaultable vaultable) {
        this.vaultable = vaultable;
    }
}
