package objects.gameObjects.AI;

import java.util.*;

public class Arbitrator {
    public List<Behaviour> behaviours;
    public Behaviour current;

    public Arbitrator(){
        behaviours = new ArrayList<>();
    }

    public void addBehaviour(Behaviour b){
        if(current == null){
            current = b;
            current.start();
        }
        behaviours.add(b);
    }

    public void update(){
        Map<Integer,Behaviour> contenders = new HashMap<>();
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
        current.update();
    }


}
