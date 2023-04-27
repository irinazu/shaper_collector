package com.example.shaper_collector.creator;

import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class QueueCreator {
    List<ArrayList<Integer>> allQueue=new ArrayList<>();
    Integer countOfPassengers;
    BigInteger numberQueueInOneSubtask;
    BigInteger counter=BigInteger.valueOf(0);
    Integer counterSubtask=1;
    BigInteger numberOfQueue;

    public QueueCreator(Integer countOfPassengers, Integer amountOfSubtask) {
        try (RandomAccessFile raf = new RandomAccessFile("subtasks.txt", "rw")) {
            raf.setLength(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.countOfPassengers = countOfPassengers;
        countOfQueue(countOfPassengers);
        countNumberQueueInOneSubtask(amountOfSubtask,numberOfQueue);
        findPermutations(countOfPassengers);
    }

    private void countNumberQueueInOneSubtask(Integer amountOfSubtask, BigInteger numberOfQueue){
        numberQueueInOneSubtask=numberOfQueue.divide(BigInteger.valueOf(amountOfSubtask));
    }

    private void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Рекурсивная функция для генерации всех перестановок строки
    private void permutations(int[] arr, int currentIndex)
    {
        if (currentIndex == arr.length - 1) {
            List<Integer> list = IntStream.of(arr)
                    .boxed()
                    .collect(Collectors.toList());
            allQueue.add(new ArrayList<>(list));
            counter=counter.add(BigInteger.valueOf(1));

            if(counter.equals(numberQueueInOneSubtask)){
                String listString="<"+counterSubtask+">"+allQueue.toString()+"\n";

                try(FileOutputStream fileOutputStream = new FileOutputStream("subtasks.txt", true)){
                    fileOutputStream.write(listString.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                allQueue.clear();
                ++counterSubtask;
                counter=BigInteger.valueOf(0);

            }
        }

        for (int i = currentIndex; i < arr.length; i++)
        {
            swap(arr, currentIndex, i);
            permutations(arr, currentIndex + 1);
            swap(arr, currentIndex, i);
        }
    }

    public void findPermutations(int numberOfPassengers) {

        int arr[]=new int[numberOfPassengers];
        for(int i=0;i<numberOfPassengers;i++){
            arr[i]=i;
        }
        permutations(arr, 0);
    }

    private void countOfQueue(Integer countOfPassengers){
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= countOfPassengers; i++){
            result = result.multiply(BigInteger.valueOf(i));
        }
        numberOfQueue=result;
    }
}
