package objects.misc.animation;

import objects.FileIO.BufferedImageLoader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Animation {

    private List<BufferedImage> spriteList;
    private ListIterator<BufferedImage> spriteIterator;
    private BufferedImage currentSprite;
    private int frameCount = 0;
    private int fps;

    public Animation(String filepath,int fps){
        spriteList = loadSprites(filepath);
        spriteIterator = spriteList.listIterator();
        currentSprite = spriteList.get(0);
        this.fps = fps;
    }

    private List<BufferedImage> loadSprites(String filepath){
        File folder = new File("res"+filepath).getAbsoluteFile();
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles, (o1, o2) -> {
            int a = Integer.valueOf(o1.getName().replace(".png",""));
            int b = Integer.valueOf(o2.getName().replace(".png",""));
            return a-b;
        });

        List<BufferedImage> sprites = new ArrayList<>();
        BufferedImageLoader loader = new BufferedImageLoader();
        for(File file : listOfFiles){
            System.out.println(filepath+"/"+file.getName());
            sprites.add(loader.loadImage(filepath+"/"+file.getName()));
        }
        return sprites;
    }

    public void update(){
        if(!spriteIterator.hasNext()){
            spriteIterator = spriteList.listIterator();
        }
        else{
            frameCount++;
            if(frameCount >= 60/fps){
                currentSprite = spriteIterator.next();
                frameCount = 0;
            }
        }
    }

    public BufferedImage getSprite(){
        return currentSprite;
    }

    public void reset(){
        spriteIterator = spriteList.listIterator();
    }
}
