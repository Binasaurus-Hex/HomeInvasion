package game;

import objects.gameObjects.Node;
import objects.misc.PathGenerator;
import objects.misc.PathList;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Test {

    public static void main(String[] args) {
        List<String> testList = new ArrayList<>();
        testList.add("chicken");
        testList.add("cat");
        testList.add("dog");
        ListIterator<String> iterator = testList.listIterator();
        while (true){
            if(iterator.nextIndex()>= testList.size()){
                iterator = testList.listIterator();
            }
            else{
                System.out.println(iterator.next());
            }
        }
    }
}
