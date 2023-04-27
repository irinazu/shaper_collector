package com.example.shaper_collector.creator;

public class MatrixCreator {
    Integer size;
    int graph[][];

    public MatrixCreator(Integer size) {
        this.size = size;
        graph=new int[size][size];
    }
    public int[][] createMatrix(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(i==j){
                    graph[i][j]=0;
                }else {
                    graph[i][j]=getRandom();
                }
            }
        }

        return graph;
    }


    int getRandom(){
        return (int) (1 + Math.random()*100);
    }

}
