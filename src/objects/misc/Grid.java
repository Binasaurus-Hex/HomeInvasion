package objects.misc;

import game.Game;
import objects.FileIO.BufferedImageLoader;
import objects.gameObjects.Node;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Grid {
    
    private Game game;
    private ObjectList<ObjectList<Node>> nodeList;
    private int[][] matrix;

    public Grid(Game game){
        this.game = game;
        nodeList = new ObjectList<>();
        BufferedImageLoader loader = new BufferedImageLoader();
        readGridFromFile(loader.loadImage("/map/Floor1.png"));
        generateNodes();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public ObjectList<ObjectList<Node>> getNodes(){
        return nodeList;
    }

    private void generateNodes(){
        ObjectList<ObjectList<Node>> nodes = new ObjectList<>();
        for (int row = 0;row < matrix.length;row++){
            ObjectList<Node> rowNodes = new ObjectList<>();
            for (int column = 0; column < matrix[0].length; column ++){
                double xOffset = 0;
                double yOffset = 0;
                Node temp = new Node((column*Node.size)-xOffset,(row*Node.size)-yOffset,game);
                if(matrix[row][column] == 1){
                    temp.setColor(Color.green);
                    temp.score = 1;
                } else if(matrix[row][column] == 0){
                    temp.setColor(Color.red);
                    temp.score = Integer.MAX_VALUE;
                } else if(matrix[row][column] == 2){
                    temp.setColor(Color.blue);
                    temp.score = 500;
                }
                rowNodes.add(temp);
            }
            nodes.add(rowNodes);
        }
        setJunctions(nodes);
        this.nodeList = nodes;
    }

    public void setJunctions(ObjectList<ObjectList<Node>> nodeList){
        for(int row = 0; row < nodeList.size(); row++){
            for(int column = 0; column < nodeList.get(0).size(); column++){
                Node temp = nodeList.get(row).get(column);
                Node north = getNodeAtLocation(nodeList,row-1,column);
                Node south = getNodeAtLocation(nodeList,row+1,column);
                Node east = getNodeAtLocation(nodeList,row,column+1);
                Node west = getNodeAtLocation(nodeList,row,column-1);

                if(north != null) temp.children.add(north);
                if(south != null) temp.children.add(south);
                if(east != null) temp.children.add(east);
                if(west != null) temp.children.add(west);
            }
        }
    }

    private Node getNodeAtLocation(ObjectList<ObjectList<Node>> nodeList,int row,int column){
        if(row >= 0 && row < nodeList.size()){
            ObjectList<Node> nodeRow = nodeList.get(row);
            if(column >= 0 && column < nodeRow.size()){
                return nodeRow.get(column);
            }
        }
        return null;
    }

    private void readGridFromFile(BufferedImage image) {
        int w = image.getWidth(); //gets the width of the image
        int h = image.getHeight(); //gets the height of the image
        matrix = new int[w][h];

        for(int y = 0; y < h; y++) { //first for loop, starts in top left, progressively goes to the down
            for(int x = 0; x < w; x++) { //second for loop, starts in top left, progressively moves right.
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255 && green == 0 && blue == 0) {
                    matrix[y][x] = 0;
                }
                if(red == 0 && green == 255 && blue == 0) {
                    matrix[y][x] = 1;
                }
                if(red == 0 && green == 0 && blue == 255) {
                    matrix[y][x] = 2;
                }
            }
        }
    }

    public void printGrid(){
        for(int[] row: matrix){
            for(int column : row){
                System.out.print(column+" ");
            }
            System.out.print("\n");
        }
    }

    public Node getNearestNode(Point2D.Double point){
        int row = (int)Math.round(point.y/Node.size);
        int column = (int)Math.round(point.x/Node.size);
        return nodeList.get(row).get(column);
    }

    public Node getNearestJunction(Point2D.Double point){
        Node nearest = null;
        double lowestDist = Double.MAX_VALUE;
        for(ObjectList<Node> i : nodeList){
            for(Node j : i){
                if(!(j.getColor() == Color.green || j.getColor() == Color.blue))continue;
                if(nearest == null)nearest = j;
                else {
                    double jDist = j.getPoint().distance(point);
                    if(jDist < lowestDist){
                        lowestDist = jDist;
                        nearest = j;
                    }
                }

            }
        }
        return nearest;
    }

    public Node getRandomNode() {
        while(true) {
            int randomRow = (int)Math.floor(Math.random() * nodeList.size());
            int randomColumn = (int)Math.floor(Math.random() * nodeList.get(0).size());
            if(nodeList.get(randomRow).get(randomColumn).getColor().equals(Color.green)) {
                return nodeList.get(randomRow).get(randomColumn);
            }
        }
    }

    public Node getRandomGoal(Node start){
        while (true){
            Node random = getRandomNode();
            if(!random.equals(start)){
                return random;
            }
        }
    }
}
