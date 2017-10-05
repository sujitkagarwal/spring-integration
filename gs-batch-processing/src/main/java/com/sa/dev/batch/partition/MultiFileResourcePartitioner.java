package com.sa.dev.batch.partition;


import com.sa.dev.batch.command.AppCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QU04JL on 19-9-2017.
 */
@Slf4j
public class MultiFileResourcePartitioner implements Partitioner {

    private static final String DEFAULT_KEY_NAME = "fileName";

     //private final ExecutorService threadpool = Executors.newFixedThreadPool(4);
    private AppCommand appCommand;


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
            //  context.put("futureObject",threadpool.submit(new UserCommand(file.getName())))
                context.put("futureObject",appCommand.getUserCommand(file.getName()));
                partitionMap.put(file.getName(), context);

            }
        }
        return partitionMap;
    }



    public void setInboundDir(String inboundDir) {
        this.inboundDir = inboundDir;
    }

    public void setAppCommand(AppCommand appCommand) {
        this.appCommand = appCommand;
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


}