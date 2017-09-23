package com.sa.dev.batch.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QU04JL on 19-9-2017.
 */

public class MultiFileResourcePartitioner implements Partitioner {

    private static final String DEFAULT_KEY_NAME = "fileName";

    private String inboundDir;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> partitionMap = new HashMap<>();
        File dir = new File(inboundDir);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                ExecutionContext context = new ExecutionContext();
                context.put(DEFAULT_KEY_NAME,file.getAbsolutePath() );
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
}
