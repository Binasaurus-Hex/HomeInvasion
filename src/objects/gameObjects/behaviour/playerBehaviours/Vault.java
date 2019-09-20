package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Player;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.gameObjects.behaviour.Navigator;
import objects.handlers.KeyBindings;
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
        vault = new Animation("/sprites/player/vaulting",14,12);
    }

    private boolean reachedStart(){
        setAnchors();
        if(player.getPoint().distance(start) < Navigator.targetDistance)return true;
        else return false;
    }

    private void setAnchors(){
        Point2D.Double[] anchors = HelperFunctions.getOrderedAnchors(player.getPoint(),vaultable.getAnchorPoints());
        start = anchors[0];
        end = anchors[1];
    }


    @Override
    public void start() {
        Point2D.Double[] anchors = HelperFunctions.getOrderedAnchors(player.getPoint(),vaultable.getAnchorPoints());
        start = anchors[0];
        end = anchors[1];
        player.setCollidable(false);
        vault.reset();
        player.currentSprite = vault.getSprite();
        player.setVelY(player.speed/3);
        player.setVelX(player.speed/3);
    }

    @Override
    public boolean needsControl() {
        if(activated)return true;
        if(vaultable != null && vaultable.useable() && reachedStart()){
            if(KeyHandler.isKeyPressed(KeyBindings.VAULT)){
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
            vault.reset();
            player.setVelX(player.speed);
            player.setVelY(player.speed);
        }
        else{
            if(!vault.isFinished()){
                vault.update();
                player.currentSprite = vault.getSprite();
            }
        }
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public void stop() {
        player.setVelY(player.speed);
        player.setVelX(player.speed);
        activated = false;
    }

    @Override
    public void setVaultableTouched(Vaultable vaultable) {
        this.vaultable = vaultable;
    }
}
