package id.co.jriztana.app.pos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@Service
@Slf4j
public class PosServiceImpl implements PosService {

    @Override
    public List<List<String>> processCsv(List<List<String>> data) {
//        data.parallelStream().map(inner -> log.info("I am thread " + threadId + " of " + numThreads));

        final int numThreads = 5;
        ForkJoinPool customThreadPool = new ForkJoinPool(numThreads);


        ExecutorService exec = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < data.size(); i++) {
            log.info("Loop No " + i);
            exec.execute(() -> {
                long threadId = Thread.currentThread().getId() % numThreads + 1;
                log.info("I am " + Thread.currentThread().getName());
                log.info("I am thread " + threadId + " of " + numThreads);
            });
        }
        return data;
    }



}
