package com.example.shaper_collector.services;

import com.example.DataForMachine;
import com.example.shaper_collector.controllers.ShapeController;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileFromFrontend {
    private static Logger log = Logger.getLogger(ShapeController.class.getName());

    public DataForMachine takeFileFromFrontend(MultipartFile multipartFile,Integer countOfTown,Integer countOfPassengers){
        String fileName=multipartFile.getOriginalFilename();
        File fileWithMatrixTaxiPath = new File(fileName);

        try (OutputStream os = new FileOutputStream(fileWithMatrixTaxiPath)) {
            os.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileReader reader;
        try {
             reader = new FileReader(fileName);
        }catch (FileNotFoundException e){
            System.out.println();
            return null;
        }

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(reader);
        DataForMachine dataMachineForJson=gson.fromJson(jsonReader, DataForMachine.class);
        if(checkFileFromFrontend(dataMachineForJson,countOfTown,countOfPassengers)){
            return dataMachineForJson;
        }else {
            return null;
        }
    }


    private boolean checkFileFromFrontend(DataForMachine dataMachineForJson,Integer countOfTown,Integer countOfPassengers){
        if(dataMachineForJson.getTaxi().isEmpty()||dataMachineForJson.getGraph().length==0||
                dataMachineForJson.getArrayLists().isEmpty()){
            log.log(Level.WARNING,"[Файл неполный]");
            return false;
        }

        if(dataMachineForJson.getTaxi().size()!=countOfTown) {
            log.log(Level.WARNING, "[Размер городов не соотвествует количеству такси]");
            return false;
        }

        if(countOfTown!=dataMachineForJson.getGraph().length) {
            log.log(Level.WARNING, "[Размер городов не соотвествует матрице]");
            return false;
        }

        if(dataMachineForJson.getArrayLists().size()!=countOfPassengers) {
            log.log(Level.WARNING, "[Количество пассажиров не соответствует количество путей]");
            return false;
        }

        return true;
    }
}
