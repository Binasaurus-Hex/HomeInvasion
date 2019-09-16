package objects.interfaces;

import objects.gameObjects.Anime;
import objects.gameObjects.Windows.Window;

public interface Character {

    void onWindowTouched(Window window);
    void onAnimeTouched(Anime anime);
}
