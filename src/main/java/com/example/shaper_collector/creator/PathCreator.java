package com.example.shaper_collector.creator;

import java.util.ArrayList;
import java.util.List;

public class PathCreator {
    Integer countOfPassengers;
    Integer countOfTown;
    List<ArrayList<Integer>> arrayLists=new ArrayList<>();


    public PathCreator(Integer countOfPassengers, Integer countOfTown) {
        this.countOfPassengers = countOfPassengers;
        this.countOfTown = countOfTown;
    }

    public List<ArrayList<Integer>> createPath(){
        for(int i=0;i<countOfPassengers;i++){
            arrayLists.add(new ArrayList<>(List.of(getRandom(),getRandom())));
        }
        return arrayLists;
    }

    int getRandom(){
        return (int) (1 + Math.random()*countOfTown-1);
    }

}
