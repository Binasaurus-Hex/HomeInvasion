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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Animation {

    private List<BufferedImage> spriteList;
    private ListIterator<BufferedImage> spriteIterator;
    private BufferedImage currentSprite;
    private Point2D.Double startPos;
    private Point2D.Double endPos;

    public Animation(String filepath, Point2D.Double startPos, Point2D.Double endPos,long timeMillis){
        try {
            spriteList = loadSprites(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        spriteIterator = spriteList.listIterator();
    }

    private List<BufferedImage> loadSprites(String filepath) throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(filepath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        BufferedImageLoader loader = new BufferedImageLoader();
        List<BufferedImage> spriteList = new ArrayList<>();
        for(File file : filesInFolder){
            spriteList.add(loader.loadImage(file.getPath()));
        }
        return spriteList;
    }

    public void update(){
        if(!spriteIterator.hasNext()){
            spriteIterator = spriteList.listIterator();
        }
        else{
            currentSprite = spriteIterator.next();
        }
    }

    public BufferedImage getSprite(){
        return currentSprite;
    }

    public void reset(){
        spriteIterator = spriteList.listIterator();
    }
}
