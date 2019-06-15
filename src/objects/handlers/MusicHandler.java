package objects.handlers;

import game.Game;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class MusicHandler {
    private AudioContext ac;
    public HashMap<String,Sample> trackList;
    //music:
    public MusicPlayer playerWalking;

    public MusicHandler(){
        this.ac = new AudioContext();
        trackList = new HashMap<>();
        trackList.put("alarm",SampleManager.sample("res/audio/alarm.mp3"));
        trackList.put("doorOpen",SampleManager.sample("res/audio/doorOpen.wav"));
        trackList.put("doorClose",SampleManager.sample("res/audio/doorClose.wav"));
        trackList.put("music",SampleManager.sample("res/audio/music.mp3"));
        trackList.put("nailing",SampleManager.sample("res/audio/nailing.mp3"));
        trackList.put("night",SampleManager.sample("res/audio/night.mp3"));
        trackList.put("playerSteps",SampleManager.sample("res/audio/playerSteps.mp3"));
        trackList.put("stairs",SampleManager.sample("res/audio/stairs.mp3"));
        trackList.put("tripwire",SampleManager.sample("res/audio/tripwire.mp3"));
        trackList.put("whisper1",SampleManager.sample("res/audio/whisper1.mp3"));
        trackList.put("whisper1Right",SampleManager.sample("res/audio/whisper1Right.mp3"));
        trackList.put("whisper1Left",SampleManager.sample("res/audio/whisper1Left.mp3"));
        trackList.put("whisper2",SampleManager.sample("res/audio/whisper2.mp3"));
        trackList.put("windup",SampleManager.sample("res/audio/windup.mp3"));
        trackList.put("woosh",SampleManager.sample("res/audio/woosh.mp3"));
    }

    public AudioContext getAC(){
        return(ac);
    }

    public Sample getTrack(String s){
        return(trackList.get(s));
    }

    public void start(){
        ac.start();
    }

    public void stop(){
        ac.stop();
    }

}

