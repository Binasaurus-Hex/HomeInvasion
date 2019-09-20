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
    public static double targetDistance = 1.5;
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
        currentPos = game.grid.getNearestNode(object.getPoint());

    }

    public boolean reachedGoal(){
        currentPos = game.grid.getNearestNode(object.getPoint());
        if(currentPos.getPoint().distance(goal.getPoint())<targetDistance){
            goal.setColor(Color.green);
            return true;
        }
        else return false;
    }

    public void setGoal(Point2D.Double end){
        Point2D.Double start = object.getPoint();
        if(!grid.getNearestNode(object.getPoint()).junction){
            start = grid.getNearestJunction(object.getPoint()).getPoint();
        }
        path = generator.getPathList(start, end);
        this.goal = grid.getNearestNode(end);
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
        double x = object.getX();
        double y = object.getY();
        object.setRotation(findRotation(object.getPoint(),point));
        double[] unitVector = MathsMethods.getUnitVector(x,y,point.getX(),point.getY());
        if(MathsMethods.distance(x,y, point.getX(), point.getY())>targetDistance) {
            x +=(unitVector[0]*object.getVelX());
            y +=(unitVector[1]*object.getVelY());
            object.setX(x);
            object.setY(y);
            return false;
        }
        else{
            return true;
        }
    }

    protected void followPath() {
        if(path.getTarget() == null)return;
        moveToPoint(path.getTarget().getPoint());
        if(path.hasReachedTarget(object.getPoint())) {
            //path.getTarget().setColor(Color.green);
            path.next();
        }
    }

    public void update(){
        followPath();
    }


}
