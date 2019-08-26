package objects.misc.animation;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Animation {

    private List<BufferedImage> spriteList;
    private Point2D.Double startPos;
    private Point2D.Double endPos;

    public Animation(String filepath, Point2D.Double startPos, Point2D.Double endPos,long timeMillis){
        loadSprites(filepath);
    }

    private void loadSprites(String filepath){
        try (Stream<Path> walk = Files.walk(Paths.get(filepath))) {
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            result.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
