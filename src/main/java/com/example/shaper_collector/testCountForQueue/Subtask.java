package com.example.shaper_collector.testCountForQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subtask {
    int arrayWithPath[][];
    List<ArrayList<Integer>> mapWithPassengersAndThereFinish=new ArrayList<>();
    List<Integer> taxi=new ArrayList<>();
    List<Integer> taxiChangePosition=new ArrayList<>();
    //List<ArrayList<Integer>> queueList=new ArrayList<>();
    List<ArrayList<Integer>> rowOfQueue=new ArrayList<>();
    List<Integer> sum=new ArrayList<>();

    Map<Integer,int []> minPath=new HashMap<>();

    public Subtask(int[][] arrayWithPath, List<ArrayList<Integer>> mapWithPassengersAndThereFinish, List<Integer> taxi, List<ArrayList<Integer>> rowOfQueue) {
        this.arrayWithPath = arrayWithPath;
        this.mapWithPassengersAndThereFinish = mapWithPassengersAndThereFinish;
        this.taxi = taxi;
        this.taxiChangePosition = new ArrayList<>(taxi);
        this.rowOfQueue = rowOfQueue;
        startCount();
    }

    public List<Integer> getAllOfSums(){
        return sum;
    }

    public void startCount(){
        //Перебираем каждую отдельную очередь
        for(int i=0;i<rowOfQueue.size();i++){
            //Передаем ее для подсчета суммы трат таксокомпании при данном конкретном варианте очереди пассажиров
            pushQueueOfPassengers(rowOfQueue.get(i),i);
            taxiChangePosition=new ArrayList<>(taxi);
        }
    }

    public void pushQueueOfPassengers(List<Integer> q, Integer spotOfQueue){
        //sum.set(spotOfQueue,0);
        sum.add(0);
        //Минимальная дистанция до каждого города для определенного пассажира из очереди
        int[] minPathForCertainPassenger;

        for(int i=0;i<q.size();i++){
            //идет рассчет Минимальная дистанция до каждого города для определенного пассажира из очереди
            //Мы берем список самих пассажиров, который неизменен, потом берем лист с очередью этих пассажиров
            //и ищем в списке пассажиров пассажира по номеру из очереди
            minPathForCertainPassenger=pushCertainPassenger(mapWithPassengersAndThereFinish.get(q.get(i)).get(0));
            minPath.put(q.get(i),minPathForCertainPassenger);
            //нам приходит Минимальная дистанция до каждого города для определенного пассажира из очереди
            //мы вызываем функцию считающую наименьший путь до такси, получаем сумму для данного пасажира и
            //цикл повторяется ибо нам нужна сумма для всей очереди
            sum.set(spotOfQueue,sum.get(spotOfQueue)+takeSumForTaxi(minPathForCertainPassenger));
            taxiChangePosition.set(mapWithPassengersAndThereFinish.get(q.get(i)).get(1),taxiChangePosition.get(mapWithPassengersAndThereFinish.get(q.get(i)).get(1))+1);

        }
    }

    private Integer takeSumForTaxi(int[] minPathForCertainPassenger) {
        int min=Integer.MAX_VALUE;
        Integer index=-1;
        for(int i=0;i<minPathForCertainPassenger.length;i++){
            if(taxiChangePosition.get(i)>0&&minPathForCertainPassenger[i]<min){
                min=minPathForCertainPassenger[i];
                index=i;
            }
        }
        taxiChangePosition.set(index,taxiChangePosition.get(index)-1);
        return min;
    }

    public int[] pushCertainPassenger(Integer src){

        int dist[]=new int [arrayWithPath.length];
        Boolean b[]=new Boolean[arrayWithPath.length];

        for(int i=0;i<dist.length;i++){
            dist[i]=Integer.MAX_VALUE;
            b[i]=false;
        }
        dist[src]=0;
        for(int i=0;i<dist.length;i++){

            int u=minDistInRow(dist,b);
            b[u]=true;
            for(int x=0;x<dist.length;x++){
                if(b[x]==false&&arrayWithPath[u][x]!=0&&dist[u]!=Integer.MAX_VALUE&&dist[u]+arrayWithPath[u][x]<dist[x]){
                    dist[x]=dist[u]+arrayWithPath[u][x];
                }
            }
        }
        return dist;

    }

    public int minDistInRow(int dist[],Boolean b[]) {
        int min=Integer.MAX_VALUE, index=-1;
        for(int i=0;i<dist.length;i++){
            if(!b[i] &&dist[i]<=min){
                min=dist[i];
                index=i;
            }
        }
        return index;
    }


    public Map<Integer,int []> getMinPath(){
        return minPath;
    }
}

