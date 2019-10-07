package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.GameObject;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.interfaces.Vaultable;
import objects.interfaces.VaultableListener;
import objects.interfaces.WindowListener;
import physics.MathsMethods;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Vault implements Behaviour, VaultableListener {
    private Vaultable vaultable;
    private Enemy enemy;
    private Point2D.Double start;
    private Point2D.Double end;
    private boolean activated;
    private boolean vaulting = false;

    public Vault(Enemy enemy){
        this.enemy = enemy;
    }

    private void setAnchors(){
        Point2D.Double[] anchors = HelperFunctions.getOrderedAnchors(enemy.getPoint(),vaultable.getAnchorPoints());
        start = anchors[0];
        end = anchors[1];
        activated = false;
    }

    @Override
    public void start() {
        enemy.navigator.setTargetDistance(5);
        System.out.println("enemy vaulting");
        setAnchors();
        enemy.setCollidable(false);
        enemy.setVelX(enemy.speed/3);
        enemy.setVelY(enemy.speed/3);
        vaulting = true;
    }

    @Override
    public boolean needsControl() {
        if(activated)return true;
        if(vaultable != null && vaultable.useable() && enemy.isFacing((GameObject)vaultable)){
            activated = true;
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(enemy.navigator.moveToPoint(end)){
            vaulting = false;
            enemy.setVelX(enemy.speed);
            enemy.setVelY(enemy.speed);
            enemy.setCollidable(true);
            activated = false;
        }
    }

    @Override
    public int getPriority() {
        if(vaulting){
            return 100;
        }
        else {
            return 10;
        }
    }

    @Override
    public void stop() {
        activated = false;
        vaulting = false;
        enemy.navigator.setTargetDistance(1.5);
    }

    @Override
    public void setVaultableTouched(Vaultable vaultable) {
        this.vaultable = vaultable;
    }
}
