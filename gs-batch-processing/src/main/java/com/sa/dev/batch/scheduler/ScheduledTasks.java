package com.sa.dev.batch.scheduler;

import com.sa.dev.batch.entity.FileJob;
import com.sa.dev.batch.service.FileService;
import com.sa.dev.batch.util.ListFilesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by qu04jl on 4-9-2017.
 */
@Profile("!test")
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final StopWatch stopwatch=new StopWatch();

    @Autowired
    private FileService fileService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "jsonJob")
    private Job job;

    @Scheduled(fixedRate = 15000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        try {
            File files[] = ListFilesUtil.listFilesAndFolders("/Users/sujitagarwal/workspace_micro/gs-batch-processing/src/test/resources/json/");
            log.info("Number of files" + files.length);
            for (File file : files) {
                log.info("file Name::" + file.getPath());
                log.info("file Name::" + file.getName());
                if (fileService.findByName(file.getName()) != null) {
                    log.info("file process by other server" + file.getName());
                    Map f = Files.readAttributes(Paths.get(file.getAbsolutePath()), "*");
                    log.info("STARTING STOPWATCH");
                    stopwatch.start("Reading number of lines");
                    log.info("number of lines" + Files.lines(Paths.get(file.getAbsolutePath())).count());
                    log.info("STOPPING STOPWATCH");
                    stopwatch.stop();
                    log.info("Elapsed time ==> " + stopwatch.getTotalTimeMillis());
                      } else {
                    try {
                        FileJob fileJob = new FileJob();
                        BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class);


                        log.info("creationTime: " + attr.creationTime());
                        log.info("lastAccessTime: " + attr.lastAccessTime());
                        log.info("lastModifiedTime: " + attr.lastModifiedTime());

                        log.info("isDirectory: " + attr.isDirectory());
                        log.info("isOther: " + attr.isOther());
                        log.info("isRegularFile: " + attr.isRegularFile());
                        log.info("isSymbolicLink: " + attr.isSymbolicLink());
                        log.info("size: " + attr.size());
                        fileJob.setName(file.getName());
                        fileService.createFile(fileJob);
                    } catch (Exception e) {
                        log.info("exception while storing file in db" + file.getName());
                        continue;
                    }
                  /*  JobParameters jobParameters =
                            new JobParametersBuilder()
                                    .addLong("time", System.currentTimeMillis())
                                    .addString("fileName", file.getPath())
                                    .addString("objectType", file.getName())
                                    .toJobParameters();

                    JobExecution execution = jobLauncher.run(job, jobParameters);*/
                   // log.info("Exitus : " + execution.getStatus());
                }
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}