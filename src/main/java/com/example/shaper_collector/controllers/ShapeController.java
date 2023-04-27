package com.example.shaper_collector.controllers;

import com.example.DataForMachine;
import com.example.Result;
import com.example.shaper_collector.EfficientDistribution;
import com.example.shaper_collector.creator.QueueCreator;
import com.example.shaper_collector.services.Constants;
import com.example.shaper_collector.services.FileFromFrontend;
import com.example.shaper_collector.services.PreparationForSendOnDispenser;
import com.example.shaper_collector.services.ResultsCollector;
import com.example.shaper_collector.testCountForQueue.CountTimeCheckQueue;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
@RequestMapping("shape")
public class ShapeController {
    FileFromFrontend fileFromFrontend;
    PreparationForSendOnDispenser preparationForSendOnDispenser;
    Constants constants;
    ResultsCollector resultsCollector;
    Integer amountOfSubtask;
    Integer generalSum;
    private static Logger log = Logger.getLogger(ShapeController.class.getName());

    public ShapeController(ResultsCollector resultsCollector,FileFromFrontend fileFromFrontend, PreparationForSendOnDispenser preparationForSendOnDispenser, Constants constants) {
        this.fileFromFrontend = fileFromFrontend;
        this.preparationForSendOnDispenser = preparationForSendOnDispenser;
        this.constants = constants;
        this.resultsCollector=resultsCollector;
    }

    @PostMapping(value = "postDataForShaper/{countOfPassengers}/{countOfTown}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public boolean loadPhotoForMessage(@PathVariable("countOfPassengers") Integer countOfPassengers,
                                    @PathVariable("countOfTown") Integer countOfTown,
                                    @RequestParam("file") MultipartFile multipartFile) {
        DataForMachine dataForMachine=fileFromFrontend.takeFileFromFrontend(multipartFile,countOfTown,countOfPassengers);
        if(dataForMachine!=null){
            log.info("[Файл присланный пользователем прошел проверку]");

            //подсчет выполнения одной очереди с указанными параметрами матрицы, положения такси, количества пассажиров,путей следоывния пассажиров
            CountTimeCheckQueue countTimeCheckQueue=new CountTimeCheckQueue(countOfPassengers,dataForMachine.getGraph(),dataForMachine.getArrayLists(),dataForMachine.getTaxi());
            Double timeOfCheckOneQueueInSecond=countTimeCheckQueue.countTimeForExecuteQueue();
            log.info("[Время проверки одной очереди]"+timeOfCheckOneQueueInSecond);
            generalSum=countTimeCheckQueue.getGeneralSum();
            //подсчет подзадач
            EfficientDistribution efficientDistribution=new EfficientDistribution(countOfPassengers,1.5,timeOfCheckOneQueueInSecond,countOfTown);
            Integer amountOfSubtask=efficientDistribution.countOfSubtask();
            log.info("[КОЛИЧЕСТВО ПОДЗАДАЧ]: "+amountOfSubtask);
            this.amountOfSubtask=amountOfSubtask;

            //подсчет количества очередей обрабатываемых по на одной подзадаче
            QueueCreator queueCreator=new QueueCreator(countOfPassengers,amountOfSubtask);

            //Подготовка файлов для передачи на раздаватель
            MultiValueMap<String, Object> requestMap=preparationForSendOnDispenser.prepareFiles(dataForMachine);
            HttpHeaders headers = preparationForSendOnDispenser.createHeaders();
            RestTemplate restTemplate= new RestTemplate();
            restTemplate.exchange(constants.getUrlForDispenser()+amountOfSubtask, HttpMethod.POST, new HttpEntity<>(requestMap, headers), String.class);

        }else {
            return false;
        }
        return true;
    }

    @PostMapping("/getResult")
    public void takeAllData(@RequestBody Result result) {
        resultsCollector.addInResults(result);
        if(resultsCollector.getResults().size()==amountOfSubtask){
            resultsCollector.findSolution();
            Result solution=resultsCollector.getSolution();
            int integer=Integer.parseInt(solution.getSum())+generalSum;
            solution.setSum(Integer.toString(integer));
            log.info("[Решение найдено]: "+solution);
        }

    }

    /*public void sendAllFilesToDispense(DataForMachine dataForMachine) throws IOException {

        //формирование jar файла

        HttpEntity<ByteArrayResource> partsEntity=createFile("F:/bigdata_projects/taxiAndPassengers.jar");

        HttpEntity<ByteArrayResource> allQueueFile_partsEntity=createFile("newTest.txt");
        //формирование матрицы, такси и путей как объекта
        try (final FileOutputStream fout = new FileOutputStream("dataForMachine.txt");
             final ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(dataForMachine);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File pathsFile=new File("dataForMachine.txt");
        byte[] pathsFile_bytes= Files.readAllBytes(Paths.get(pathsFile.getAbsolutePath()));
        final ByteArrayResource pathsFile_byteArrayResource = new ByteArrayResource(pathsFile_bytes) {
            @Override
            public String getFilename() {
                return pathsFile.getName();
            }
        };
        HttpHeaders pathsFile_parts = new HttpHeaders();
        final HttpEntity<ByteArrayResource> dataForMachineHttpEntity = new HttpEntity<>(pathsFile_byteArrayResource, pathsFile_parts);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("jar", partsEntity);
        requestMap.add("allQueue",allQueueFile_partsEntity);
        requestMap.add("paths",dataForMachineHttpEntity);

        ResponseEntity<String> response=restTemplate.exchange(uri+8081+"/getAllData/10", HttpMethod.POST, new HttpEntity<>(requestMap, headers), String.class);
    }

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
    }*/

}
