package objects.handlers;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler extends KeyAdapter {

    private static Map<String, Boolean> keyMap;

    public KeyHandler() {
        keyMap = new HashMap<>();
    }

    public static boolean isKeyPressed(String key) {
        if(keyMap != null) {
            return keyMap.getOrDefault(key,false);
        } else {
            return false;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        String key = KeyEvent.getKeyText(e.getKeyCode());
        if(!keyMap.containsKey(key)){
            keyMap.put(key,true);
        }
        else {
            keyMap.replace(key,true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String key = KeyEvent.getKeyText(e.getKeyCode());
        if(!keyMap.containsKey(key)){
            keyMap.put(key,false);
        }
        else{
            keyMap.replace(key,false);
        }
    }



}
