package com.sa.dev.batch.partition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QU04JL on 22-9-2017.
 */
@Slf4j
public class RangePartitioner  implements Partitioner {

    private static final String DEFAULT_KEY_NAME = "fileName";


    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> partitionMap = new HashMap<>();


        long fromId = 0L;
        long toId=0L;
        long numberOfLine=0L;
        long batchSize=0L;

        try{
            numberOfLine=Files.lines( Paths.get("/Users/sujitagarwal/workspace_micro/gs-batch-processing/src/test/resources/csv/sample-data-3.csv")).count();
            log.info("number of lines" + numberOfLine);
            batchSize=numberOfLine/gridSize;
           toId=batchSize;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(numberOfLine>0) {
            for (int i = 1; i <= gridSize; i++) {
                ExecutionContext value = new ExecutionContext();
                log.info("Starting : Thread" + i);
                log.info("fromId : " + fromId);
                log.info("toId : " + toId);

                value.putLong("fromId", fromId);
                value.putLong("toId", toId);
              //  value.putLong("batchSize", batchSize);
                value.put(DEFAULT_KEY_NAME,"/Users/sujitagarwal/workspace_micro/gs-batch-processing/src/test/resources/csv/sample-data-3.csv");
                // give each thread a name, thread 1,2,3
               // value.putString("name", "Thread" + i);

                partitionMap.put("DEFAULT_KEY_NAME" + i, value);

                fromId = toId;
                toId += batchSize;

            }
        }

        return partitionMap;
    }

  /*  public String getInboundDir() {
        return inboundDir;
    }

    public void setInboundDir(String inboundDir) {
        this.inboundDir = inboundDir;
    }*/
}
