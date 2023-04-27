package com.example.shaper_collector.creator;

import java.util.ArrayList;
import java.util.List;

public class TaxiCreator {
    Integer size;
    List<Integer> taxi=new ArrayList<>();

    public TaxiCreator(Integer size) {
        this.size = size;
    }

    public List<Integer> createTaxi(){
        for(int i=0;i<size;i++){
            taxi.add(getRandom());
        }
        return taxi;
    }

    int getRandom(){
        return (int) (0 + Math.random()*2);
    }

}
