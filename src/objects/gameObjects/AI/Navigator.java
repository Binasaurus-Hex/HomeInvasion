package objects.gameObjects.AI;

import game.Game;
import objects.gameObjects.Enemy;
import objects.gameObjects.GameObject;
import objects.gameObjects.Node;
import objects.misc.Grid;
import objects.misc.PathGenerator;
import objects.misc.PathList;
import physics.MathsMethods;

import java.awt.geom.Point2D;

public class Navigator {
    GameObject object;
    PathGenerator generator;
    PathList path;
    Node goal;
    Node currentPos;
    Node nextPoint;
    Grid grid;
    Game game;

    public Navigator(GameObject object, Game game){
        this.object = object;
        this.game = game;
        this.generator = new PathGenerator(game);
        grid = game.grid;
        currentPos = game.grid.getNearestNode(object.getPoint());

    }

    public boolean reachedGoal(){
        if(currentPos.getPoint().distance(goal.getPoint())<1.1){
            return true;
        }
        else return false;
    }

    public void setGoal(Point2D.Double point){
        path = generator.getPathList(object.getPoint(), point);
        goal = grid.getNearestNode(point);
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
        if(MathsMethods.distance(x,y, point.getX(), point.getY())>1) {
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
        currentPos.getPoint().setLocation(object.getX(),object.getY());
        if(path.hasReachedNext(currentPos)) {
            nextPoint = path.getNextNode();
        }
        else if(nextPoint != null){
            moveToPoint(nextPoint.getPoint());
        }
    }

    public void update(){
        followPath();
    }


}
