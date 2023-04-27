package com.example.shaper_collector.services;

import org.springframework.stereotype.Service;

@Service
public class Constants {
    private final Double timeOfBroadcast1mb=1.5;
    private final String urlForDispenser="http://localhost:8081/getAllData/";
    private final String subtaskFile="subtasks.txt";
    private final String jarFile="F:/bigdata_projects/taxiAndPassengers.jar";
    private final String dataForMachineFile="dataForMachine.txt";

    public Double getTimeOfBroadcast1mb() {
        return timeOfBroadcast1mb;
    }

    public String getUrlForDispenser() {
        return urlForDispenser;
    }

    public String getSubtaskFile() {
        return subtaskFile;
    }

    public String getJarFile() {
        return jarFile;
    }

    public String getDataForMachineFile() {
        return dataForMachineFile;
    }
}
