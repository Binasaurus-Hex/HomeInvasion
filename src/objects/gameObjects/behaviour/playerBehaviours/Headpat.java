package objects.gameObjects.behaviour.playerBehaviours;

import objects.gameObjects.Anime;
import objects.gameObjects.Player;
import objects.gameObjects.behaviour.Behaviour;
import objects.handlers.KeyHandler;
import objects.interfaces.AnimeListener;
import objects.misc.animation.Animation;

import java.awt.event.KeyEvent;

public class Headpat implements Behaviour, AnimeListener {
    private Anime anime;
    private Animation headpat;
    private boolean activated;
    private Player player;

    public Headpat(Player player){
        headpat = new Animation("/sprites/player/headpat",10,22);
        this.player = player;
    }

    @Override
    public void start() {
        activated = true;
        headpat.reset();
        player.currentSprite = headpat.getSprite();
    }

    @Override
    public boolean needsControl() {
        if(anime != null){
            if(KeyHandler.isKeyPressed("F") || activated){
                activated = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!headpat.isFinished()){
            anime.setPatting(true);
            headpat.update();
            player.currentSprite = headpat.getSprite();
        }
        else {
            activated = false;
            headpat.reset();
            anime.setPatting(false);
            player.currentSprite = headpat.getSprite();
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void stop() {
        activated = false;
    }

    @Override
    public void setAnimeTouched(Anime anime) {
        this.anime = anime;
    }
}
