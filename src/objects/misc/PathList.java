package objects.misc;

import game.Game;
import objects.gameObjects.Node;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class PathList extends CopyOnWriteArrayList<Node> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Node target;
	private Game game;
	private int listPos = 0;
	
	public PathList(Game game) {
		super();
		this.game = game;
	}

	public Node getTarget(){
		if(target == null)target = this.get(0);
		return target;
	}

	public void next(){
		listPos++;
		if(listPos>=this.size())target = null;
		else{
			target = this.get(listPos);
		}

	}

	
	public Node getClosetNode(Node inputNode) {
		Node closest = new Node(1,1,game);
		for(Node node :this) {
			if(node.getPoint().distance(inputNode.getPoint())<closest.getPoint().distance(inputNode.getPoint())) {
				closest = node;
			}
		}
		return closest;
	}

	public Node getClosetNode(Point2D.Double point) {
		Node closest = new Node(1,1,game);
		for(Node node :this) {
			if(node.getPoint().distance(point)<closest.getPoint().distance(point)) {
				closest = node;
			}
		}
		return closest;
	}
	
	public boolean hasReachedTarget(Point2D.Double point) {
		if(target.getPoint().distance(point)<1.1) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
