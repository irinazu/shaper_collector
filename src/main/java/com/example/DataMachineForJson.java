package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataMachineForJson {
    @SerializedName("graph")
    @Expose
    int graph[][];
    @SerializedName("arrayLists")
    @Expose
    List<ArrayList<Integer>> arrayLists=new ArrayList<>();
    @SerializedName("taxi")
    @Expose
    List<Integer> taxi=new ArrayList<>();

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public List<ArrayList<Integer>> getArrayLists() {
        return arrayLists;
    }

    public List<Integer> getTaxi() {
        return taxi;
    }

    public int[][] getGraph() {
        return graph;
    }

    public void setArrayLists(List<ArrayList<Integer>> arrayLists) {
        this.arrayLists = arrayLists;
    }

    public void setTaxi(List<Integer> taxi) {
        this.taxi = taxi;
    }


    @Override
    public String toString() {
        return "DataMachineForJson{" +
                "graph=" + Arrays.deepToString(graph) +
                ", arrayLists=" + arrayLists +
                ", taxi=" + taxi +
                '}';
    }
}
