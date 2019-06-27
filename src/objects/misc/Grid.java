package objects.misc;

import game.Game;
import objects.gameObjects.GameObject;
import objects.gameObjects.Node;
import objects.gameObjects.Wall;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Grid {
    
    private Game game;
    private ObjectList<ObjectList<Node>> nodeList;
    private int[][] matrix;

    public Grid(Game game){
        this.game = game;
        nodeList = new ObjectList<>();
        try {
            makeImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImageLoader loader = new BufferedImageLoader();
        readGridFromFile(loader.loadImage("/map/generatedMap.png"));
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
                Node temp = new Node(column*Node.size,row*Node.size,game);
                if(matrix[row][column] == 1){
                    temp.setColor(Color.green);
                } else if(matrix[row][column] == 0){
                    temp.setColor(Color.red);
                } else if(matrix[row][column] == 2){
                    temp.setColor(Color.blue);
                }
                rowNodes.add(temp);
            }
            nodes.add(rowNodes);
        }
        setJunctions(nodes);
        this.nodeList = nodes;
    }

    @SuppressWarnings("Duplicates")
    public void setJunctions(ObjectList<ObjectList<Node>> nodeList){
        for(ObjectList<Node> row : nodeList){
            for(Node temp : row){
                if(temp.getColor().equals(Color.red) || temp.getColor().equals(Color.blue)){
                    continue;
                }
                else{
                    temp.junction = true;
                }
            }
        }

        for(int row = 0;row < nodeList.size();row++){
            for(int column = 0;column < nodeList.get(0).size();column++){
                Node temp = nodeList.get(row).get(column);
                if(temp.junction){
                    int upCount = 1;
                    int downCount = 1;
                    int leftCount = 1;
                    int rightCount = 1;
                    while (true){
                        Node next = nodeList.get(row-upCount).get(column);
                        if(next.getColor().equals(Color.red) || next.getColor().equals(Color.blue)){
                            break;
                        }
                        else if(next.junction){
                            next.parent = temp;
                            temp.children.add(next);
                            break;
                        }
                        upCount++;
                    }
                    while (true){
                        Node next = nodeList.get(row+downCount).get(column);
                        if(next.getColor().equals(Color.red)||next.getColor().equals(Color.blue)){
                            break;
                        }
                        else if(next.junction){
                            next.parent = temp;
                            temp.children.add(next);
                            break;
                        }
                        downCount++;
                    }
                    while (true){
                        Node next = nodeList.get(row).get(column-leftCount);
                        if(next.getColor().equals(Color.red)||next.getColor().equals(Color.blue)){
                            break;
                        }
                        else if(next.junction){
                            next.parent = temp;
                            temp.children.add(next);
                            break;
                        }
                        leftCount++;
                    }
                    while (true){
                        Node next = nodeList.get(row).get(column+rightCount);
                        if(next.getColor().equals(Color.red)||next.getColor().equals(Color.blue)){
                            break;
                        }
                        else if(next.junction){
                            next.parent = temp;
                            temp.children.add(next);
                            break;
                        }
                        rightCount++;
                    }
                }
            }
        }
    }

    @Deprecated
    private boolean isCorridor(Node test){
        Point2D.Double testPoint = test.getPoint();
        try{
            boolean up = matrix[(int)(testPoint.y/Node.size) +1][(int)(testPoint.x/Node.size)] != 0;
            boolean down = matrix[(int)(testPoint.y/Node.size)-1][(int)(testPoint.x/Node.size)] != 0;
            boolean left = matrix[(int)(testPoint.y/Node.size)][(int)(testPoint.x/Node.size)-1] != 0;
            boolean right = matrix[(int)(testPoint.y/Node.size)][(int)(testPoint.x/Node.size)+1] != 0;

            if((up && down && !left && !right) || (left && right && !up && !down)){
                return false;
            }
            else {
                return false;
            }
        }catch (Exception e){
            System.out.println("error");
            return false;
        }



    }

    public void makeImage() throws IOException {
        int val = 300;
        int offset = -150;
        BufferedImage image = new BufferedImage(val,val,BufferedImage.TYPE_INT_RGB);
        Rectangle2D.Double template = new Rectangle2D.Double(0,0,Node.size,Node.size);
        for(int i = 0; i < val; i++) {
            template.y = (i * 5) - offset;
            for (int j = 0; j < val; j++) {
                image.setRGB(j,i, Color.green.getRGB());
                template.x = (j * 5) - offset;
                for (GameObject obj : game.objectHandler.objects) {
                    if (obj.getBounds() != null) {
                        if (obj.getBounds().intersects(template)) {
                            if (obj instanceof Wall) {
                                Wall wall = (Wall) obj;
                                wall.color = Color.blue;
                                image.setRGB(j, i, Color.RED.getRGB());
                                System.out.println("x = " + j + "  y = " + i);
                                break;
                            }
                        }
                    }
                }
            }
        }
        for(int i = 0; i < image.getHeight();i++){
            image.setRGB(0,i,Color.red.getRGB());
            image.setRGB(image.getWidth()-1,i,Color.red.getRGB());
            image.setRGB(i,0,Color.red.getRGB());
            image.setRGB(i,image.getHeight()-1,Color.red.getRGB());
        }

        File file = new File("res/map/generatedMap.png");
        ImageIO.write(image,"png",file);
    }
    
    private void readGridFromFile(BufferedImage image) {
        int w = image.getWidth(); //gets the width of the image
        int h = image.getHeight(); //gets the height of the image

        matrix = new int[h][w]; //y, x

        for(int y = 0; y < h; y++) { //first for loop, starts in top left, progressively goes to the down
            for(int x = 0; x < w; x++) { //second for loop, starts in top left, progressively moves right.
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

//                System.out.println(blue);

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
                if(!j.junction)continue;
                else if(nearest == null)nearest = j;
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
            if(nodeList.get(randomRow).get(randomColumn).junction) {
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
