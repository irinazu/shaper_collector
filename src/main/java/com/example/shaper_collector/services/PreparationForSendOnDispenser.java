package com.example.shaper_collector.services;

import com.example.DataForMachine;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Arrays.asList;

@Service
public class PreparationForSendOnDispenser {

    public MultiValueMap<String, Object> prepareFiles(DataForMachine dataForMachine){
        //формирование jar файла
        HttpEntity<ByteArrayResource> partsEntity=createFile("F:/bigdata_projects/taxiAndPassengers.jar");

        //Формирование файла всех очередей обслуживания
        HttpEntity<ByteArrayResource> allQueueFile_partsEntity=createFile("subtasks.txt");

        //формирование матрицы, такси и путей как объекта
        try (final FileOutputStream fout = new FileOutputStream("dataForMachine.txt");
             final ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(dataForMachine);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File pathsFile=new File("dataForMachine.txt");
        byte[] pathsFile_bytes=null;
        try {
            pathsFile_bytes= Files.readAllBytes(Paths.get(pathsFile.getAbsolutePath()));
        }catch (IOException ioException){
            System.out.println(ioException);
        }
        //byte[] pathsFile_bytes= Files.readAllBytes(Paths.get(pathsFile.getAbsolutePath()));
        final ByteArrayResource pathsFile_byteArrayResource = new ByteArrayResource(pathsFile_bytes) {
            @Override
            public String getFilename() {
                return pathsFile.getName();
            }
        };
        HttpHeaders pathsFile_parts = new HttpHeaders();
        final HttpEntity<ByteArrayResource> dataForMachineHttpEntity = new HttpEntity<>(pathsFile_byteArrayResource, pathsFile_parts);

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("jar", partsEntity);
        requestMap.add("allQueue",allQueueFile_partsEntity);
        requestMap.add("paths",dataForMachineHttpEntity);

        return requestMap;
    }

    //заголовки для отправки файлов
    public HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    //создание файлов
    public HttpEntity<ByteArrayResource> createFile(String pathFile){
        File jarFile=new File(pathFile);
        byte[] jarFile_bytes = new byte[0];
        try {
            jarFile_bytes = Files.readAllBytes(Paths.get(jarFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders jarFile_parts = new HttpHeaders();
        jarFile_parts.setContentType(MediaType.MULTIPART_FORM_DATA);
        final ByteArrayResource jarFile_byteArrayResource = new ByteArrayResource(jarFile_bytes) {
            @Override
            public String getFilename() {
                return jarFile.getName();
            }
        };
        return new HttpEntity<>(jarFile_byteArrayResource, jarFile_parts);
    }
}
