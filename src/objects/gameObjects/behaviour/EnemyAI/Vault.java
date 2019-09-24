package objects.gameObjects.behaviour.EnemyAI;

import objects.gameObjects.Enemy;
import objects.gameObjects.Windows.Window;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.interfaces.Vaultable;
import objects.interfaces.VaultableListener;
import objects.interfaces.WindowListener;

import java.awt.geom.Point2D;

public class Vault implements Behaviour, VaultableListener {
    private Vaultable vaultable;
    private Enemy enemy;
    private Point2D.Double start;
    private Point2D.Double end;

    public Vault(Enemy enemy){
        this.enemy = enemy;
    }

    private void setAnchors(){
        Point2D.Double[] anchors = HelperFunctions.getOrderedAnchors(enemy.getPoint(),vaultable.getAnchorPoints());
        start = anchors[0];
        end = anchors[1];
    }

    @Override
    public void start() {
        setAnchors();
        enemy.setCollidable(false);
        enemy.setVelX(enemy.speed/3);
        enemy.setVelY(enemy.speed/3);

    }

    @Override
    public boolean needsControl() {
        if(vaultable != null){
            if(vaultable.useable()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!enemy.navigator.reachedGoal()){
            enemy.navigator.update();
        }
        else{

        }
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void stop() {

    }

    @Override
    public void setVaultableTouched(Vaultable vaultable) {
        this.vaultable = vaultable;
    }
}
