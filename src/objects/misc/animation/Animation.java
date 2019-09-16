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

    public Animation(String filepath,int fps,int frames){
        spriteList = loadSprites(filepath,frames,false);
        spriteIterator = spriteList.listIterator();
        currentSprite = spriteList.get(0);
        this.fps = fps;
    }

    public Animation(String filepath,int fps, int frames, boolean reversed){
        spriteList = loadSprites(filepath,frames,reversed);
        spriteIterator = spriteList.listIterator();
        currentSprite = spriteList.get(0);
        this.fps = fps;
    }

    private List<BufferedImage> loadSprites(String filepath,int frames, boolean reversed){
        BufferedImageLoader loader = new BufferedImageLoader();
        List<BufferedImage> images = new ArrayList<>();
        if(!reversed){
            for(int i = 1; i <= frames ; i++){
                images.add(loader.loadImage(filepath+"/"+i+".png"));
            }
        }
        else{
            for(int i = frames; i > 0 ; i--){
                images.add(loader.loadImage(filepath+"/"+i+".png"));
            }
        }
        return images;

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

    public boolean isFinished(){
        return !spriteIterator.hasNext();
    }

    public BufferedImage getSprite(){
        return currentSprite;
    }

    public void reset(){
        spriteIterator = spriteList.listIterator();
        currentSprite = spriteList.get(0);
    }
}
