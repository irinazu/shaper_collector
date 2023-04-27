package com.example.shaper_collector.services;


import com.example.Result;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResultsCollector {
    private List<Result> results=new ArrayList<>();
    private Result solution=null;

    public void addInResults(Result result){
        results.add(result);
    }

    public List<Result> getResults() {
        return new ArrayList<>(results);
    }

    public void findSolution() {
        Result min=results.get(0);
        for(int i=1;i<results.size();i++){
            if(Integer.parseInt(results.get(i).getSum())<Integer.parseInt(min.getSum())){
                min=results.get(i);
            }
        }
        solution=min;
    }

    public Result getSolution(){
        return solution;
    }
}
