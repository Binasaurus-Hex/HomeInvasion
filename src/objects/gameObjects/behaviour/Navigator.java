package objects.gameObjects.behaviour;

import game.Game;
import objects.gameObjects.Enemy;
import objects.gameObjects.GameObject;
import objects.gameObjects.Node;
import objects.misc.Grid;
import objects.misc.PathGenerator;
import objects.misc.PathList;
import physics.MathsMethods;

import java.awt.*;
import java.awt.geom.Point2D;

public class Navigator {
    private double targetDistance;
    private double goalDistance;
    GameObject object;
    PathGenerator generator;
    public PathList path;
    Node goal;
    public Node currentPos;
    public Grid grid;
    Game game;

    public Navigator(GameObject object, Game game){
        this.object = object;
        this.game = game;
        this.generator = new PathGenerator(game);
        grid = game.grid;
        currentPos = game.grid.getNearestJunction(object.getPoint());
        targetDistance = 1.5;
        goalDistance = 1.5;
    }

    public void setTargetDistance(double targetDistance) {
        this.targetDistance = targetDistance;
    }
    public void setGoalDistance(double goalDistance){
        this.goalDistance = goalDistance;
    }

    public boolean reachedGoal(){
        currentPos = game.grid.getNearestJunction(object.getPoint());
        if(currentPos.getPoint().distance(goal.getPoint())<goalDistance){
            return true;
        }
        else return false;
    }

    public void setGoal(Point2D.Double end){
        Node start = grid.getNearestJunction(object.getPoint());
        Node goal = grid.getNearestJunction(end);
        path = generator.getPathList(start, goal);
        this.goal = goal;
    }

    private double findRotation(Point2D.Double start , Point2D.Double point){
        double xDiff = point.x-start.x;
        double yDiff = point.y-start.y;
        double angle = Math.atan2(yDiff,xDiff);
        return angle;
    }

    /**
     * moves to the specified point
     * @param point
     */
    public boolean moveToPoint(Point2D.Double point) {
        Point2D.Double objectPos = object.getPoint();
        if(objectPos.distance(point) < targetDistance){
            return true;
        }
        else{
            double x = object.getX();
            double y = object.getY();
            object.setRotation(findRotation(object.getPoint(),point));
            double[] unitVector = MathsMethods.getUnitVector(x,y,point.getX(),point.getY());
            x +=(unitVector[0]*object.getVelX());
            y +=(unitVector[1]*object.getVelY());
            object.setX(x);
            object.setY(y);
            return false;
        }
    }

    protected void followPath() {
        if(path.getTarget() == null)return;
        if(moveToPoint(path.getTarget().getPoint())){
           path.next();
        }
    }

    public void update(){
        followPath();
    }


}
