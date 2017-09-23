package com.sa.dev.batch.json;


import com.sa.dev.batch.entity.FileJob;
import com.sa.dev.batch.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by qu04jl on 6-9-2017.
 */
@Component
@StepScope
@Slf4j
public class JsonStepListner implements StepExecutionListener {



    @Value("#{jobParameters['objectType']}")
    private String objectType;

    @Autowired
    private FileService fileService;


    @Override
    public ExitStatus afterStep(StepExecution setpExe) {
      //  int readCount=setpExe.getReadCount();
        int writeCount=setpExe.getWriteCount();
        int skipCount=setpExe.getSkipCount();
        int commitCount=setpExe.getCommitCount();
        log.info(setpExe.getSummary());
        FileJob fileJob=fileService.findByName(objectType);
        fileJob.setCommitCount(setpExe.getCommitCount());
        fileJob.setReadCount(setpExe.getReadCount());
        fileJob.setWriteCount(setpExe.getWriteCount());
        fileJob.setSkipCount(setpExe.getSkipCount());
        fileJob.setStatus(setpExe.getStatus().name());
        try {
            fileService.createFile(fileJob);
        } catch (Exception e) {
            log.info("update of file status failed");
        }

        return setpExe.getExitStatus();
    }

    @Override
    public void beforeStep(StepExecution arg0) {

    }
}
