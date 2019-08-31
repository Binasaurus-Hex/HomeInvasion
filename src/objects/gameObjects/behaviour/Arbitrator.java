package objects.gameObjects.behaviour;

import java.util.*;

public class Arbitrator {
    public List<Behaviour> behaviours;
    public Behaviour current;
    Map<Integer,Behaviour> contenders;

    public Arbitrator(){
        behaviours = new ArrayList<>();
        contenders = new HashMap<>();
    }

    public void addBehaviour(Behaviour b){
        if(current == null){
            current = b;
            current.start();
        }
        behaviours.add(b);
    }

    public void update(){
        for(Behaviour b : behaviours){
            if(b.needsControl()) contenders.put(b.getPriority(),b);
            else{
                b.stop();
            }
        }
        if(contenders.isEmpty())return;
        Integer highest = Integer.MIN_VALUE;
        for(Map.Entry<Integer,Behaviour> b : contenders.entrySet()){
            if(b.getKey() > highest) highest = b.getKey();
        }
        Behaviour next = contenders.get(highest);
        if(!next.equals(current)){
            current.stop();
            next.start();
            current = next;
        }
        contenders.clear();
        current.update();
    }


}
