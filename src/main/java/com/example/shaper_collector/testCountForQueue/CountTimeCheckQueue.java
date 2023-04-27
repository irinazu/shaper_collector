package com.example.shaper_collector.testCountForQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CountTimeCheckQueue {
    List<ArrayList<Integer>> rangeQueue=new ArrayList<>();
    Integer countOfPassenger;
    List<Integer> sum=new ArrayList<>();
    int graph[][];
    List<ArrayList<Integer>> arrayLists=new ArrayList<>();
    List<Integer> taxi=new ArrayList<>();

    Integer generalSum;
    public CountTimeCheckQueue(Integer countOfPassenger, int[][] graph, List<ArrayList<Integer>> arrayLists, List<Integer> taxi) {
        this.countOfPassenger= countOfPassenger;
        createOneQueue();
        this.graph = graph;
        this.arrayLists = arrayLists;
        this.taxi = taxi;
    }

    public Double countTimeForExecuteQueue(){
        long time = System.nanoTime();
        Subtask subtask=new Subtask(graph,arrayLists,taxi,rangeQueue);
        sum=subtask.getAllOfSums();
        findMinimalSumForTaxiCompany();
        Double timeCount= (System.nanoTime()-time)/Math.pow(10,9);
        countGeneralPath(subtask.getMinPath());
        return timeCount;
    }

    private void findMinimalSumForTaxiCompany(){
        int minSum=sum.get(0);
        int minQueue=0;
        for(int i=1;i<sum.size();i++){
            if(sum.get(i)<minSum){
                minSum=sum.get(i);
                minQueue=i;
            }
        }
        //System.out.println(minSum);
        //System.out.println(rangeQueue.get(minQueue));
    }

    private void createOneQueue(){
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<countOfPassenger;i++){
            list.add(i);
        }
        rangeQueue.add(new ArrayList<>(list));
    }

    public void countGeneralPath(Map<Integer,int []> integerMap){
        Integer sum=0;
        for (Map.Entry<Integer, int []> entry : integerMap.entrySet()) {
            Integer placeTo=arrayLists.get(entry.getKey()).get(1);
            int arr[]=entry.getValue();
            sum=sum+arr[placeTo];

        }
        generalSum=sum;
    }

    public Integer getGeneralSum() {
        return generalSum;
    }
}
