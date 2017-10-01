package com.sa.dev.batch.partition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by QU04JL on 19-9-2017.
 */
@Slf4j
public class MultiFileResourcePartitioner implements Partitioner {

    private static final String DEFAULT_KEY_NAME = "fileName";
    private final ExecutorService threadpool = Executors.newFixedThreadPool(4);


    private String inboundDir;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> partitionMap = new HashMap<>();
        File dir = new File(inboundDir);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".csv");
                }
            });

            for (File file : files) {
                ExecutionContext context = new ExecutionContext();
                context.put(DEFAULT_KEY_NAME, file.getAbsolutePath());
             //   context.put("futureObject",threadpool.submit(new UserCommand(file.getName())));
                partitionMap.put(file.getName(), context);

            }
        }
        return partitionMap;
    }

    public String getInboundDir() {
        return inboundDir;
    }

    public void setInboundDir(String inboundDir) {
        this.inboundDir = inboundDir;
    }

/*
     class UserCommand implements Callable<User> {
        private final String fileName;

        public UserCommand(String fileName) {
            this.fileName=fileName;
        }

        @Override
        public User call() throws Exception {
            log.info("inside command of user partition "+fileName);
           User user=new User();
            user.setName(fileName);
            return user;
        }
    }*/

   /* @HystrixCommand(fallbackMethod = "findAllFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "720000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "720000"),
            //  @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "72000")

    })*/



}