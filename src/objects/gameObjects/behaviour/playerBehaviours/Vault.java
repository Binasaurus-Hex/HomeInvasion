package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Player;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.handlers.KeyHandler;
import objects.interfaces.Vaultable;
import objects.interfaces.VaultableListener;
import objects.interfaces.WindowListener;
import objects.misc.animation.Animation;

import java.awt.geom.Point2D;

public class Vault implements Behaviour, VaultableListener {
    private Player player;
    private Vaultable vaultable;
    private Animation vault;
    private Point2D.Double start;
    private Point2D.Double end;
    private boolean activated;

    public Vault(Player player){
        this.player = player;
    }


    @Override
    public void start() {
        Point2D.Double[] anchors = HelperFunctions.getOrderedAnchors(player.getPoint(),vaultable.getAnchorPoints());
        start = anchors[0];
        end = anchors[1];
        player.setCollidable(false);
    }

    @Override
    public boolean needsControl() {
        if(vaultable != null && vaultable.useable()){
            if(KeyHandler.isKeyPressed("Space") || activated){
                activated = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(player.moveToPoint(end)){
            activated = false;
            player.setCollidable(true);
        }
    }

    @Override
    public int getPriority() {
        return 0;
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
