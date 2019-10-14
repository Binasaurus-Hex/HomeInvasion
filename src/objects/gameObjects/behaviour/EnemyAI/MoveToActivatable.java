package objects.gameObjects.behaviour.EnemyAI;

import javafx.util.Pair;
import objects.gameObjects.Enemy;
import objects.gameObjects.GameObject;
import objects.gameObjects.behaviour.Behaviour;
import objects.gameObjects.behaviour.HelperFunctions;
import objects.interfaces.Activatable;
import objects.interfaces.ActivatableListener;

import java.awt.geom.Point2D;

public class MoveToActivatable implements Behaviour, ActivatableListener {
    private Enemy enemy;
    private Activatable activatable;
    private boolean finished = false;
    private boolean activated = false;
    private Pair<Point2D.Double,Double> pose;

    public MoveToActivatable(Enemy enemy){
        this.enemy = enemy;
    }

    private void setPose(){
        pose = HelperFunctions.getOrderedAnchorPairs(enemy.getPoint(),activatable.getAnchors()).get(0);
    }

    private boolean isInPosition(){
        Point2D.Double anchor = HelperFunctions.getOrderedAnchorPairs(enemy.getPoint(),activatable.getAnchors()).get(0).getKey();
        if(enemy.getPoint().distance(anchor) <= 1.5){
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        setPose();
        finished = false;
        activated = true;
    }

    @Override
    public boolean needsControl() {
        if(activated)return true;
        if(activatable!=null && (enemy.isFacing((GameObject)activatable)) && !finished && !isInPosition()){
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(enemy.navigator.moveToPoint(pose.getKey())){
            finished = true;
            activated = false;
            enemy.setRotation(pose.getValue());
        }
    }

    @Override
    public int getPriority() {
        return 12;
    }

    @Override
    public void stop() {
        finished = false;
        activated = false;
    }

    @Override
    public void setActivatable(Activatable activatable) {
        this.activatable = activatable;
    }
}
