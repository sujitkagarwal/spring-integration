package com.sa.dev.batch;

import com.sa.dev.batch.entity.FileJob;
import com.sa.dev.batch.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by qu04jl on 1-9-2017.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BatchTest {

    @Autowired
    @Qualifier(value = "customUserJob")
    private Job job;

    @Autowired
    @Qualifier(value = "customJob")
    private Job customJob;

    @Autowired
    @Qualifier(value = "jsonJob")
    private Job jsonJob;


    @Autowired
    @Qualifier(value = "defaultJob")
    private Job defaultJob;

    @Autowired
    @Qualifier(value = "partitionUserJob")
    private Job partitionUserJob;


    @Autowired
    @Qualifier(value = "partitionRangeJob")
    private Job partitionRangeJob;


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private FileService fileService;

    @Test
    public void testBatch() throws Exception {
        //  File file=new File("C:/tmp//workspace/gs-batch-processing/src/test/resources/file/sample-data_1.csv");
        final JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("fileName", "C:/tmp//workspace/gs-batch-processing/src/test/resources/csv/sample-data_1.csv")
                        .toJobParameters();
        System.out.println("job name :: " + job.getName());
        jobLauncher.run(job, jobParameters);
    }

    @Test
    public void testCustomBatch() throws Exception {
        //  File file=new File("C:/tmp//workspace/gs-batch-processing/src/test/resources/file/sample-data_1.csv");
        final JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("fileName", "C:/tmp//workspace/gs-batch-processing/src/test/resources/TXT/filewithfourrows.txt")
                        .toJobParameters();
        System.out.println("job name :: " + customJob.getName());
        jobLauncher.run(customJob, jobParameters);
    }


    @Test
    public void testDefaultBatch() throws Exception {
        //  File file=new File("C:/tmp//workspace/gs-batch-processing/src/test/resources/file/sample-data_1.csv");
        final JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("fileName", "C:/tmp//workspace/gs-batch-processing/src/test/resources/TXT/filewithfourrows.txt")
                        .toJobParameters();
        System.out.println("job name :: " + defaultJob.getName());
        jobLauncher.run(defaultJob, jobParameters);
    }

    @Test
    public void testJsonBatch() throws Exception {
        //  File file=new File("C:/tmp//workspace/gs-batch-processing/src/test/resources/file/sample-data_1.csv");

        FileJob fileJob = new FileJob();
        fileJob.setName("person2");
        fileService.createFile(fileJob);

        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("fileName", "C:/tmp//workspace/gs-batch-processing/src/test/resources/json/person1.json")
                        .addString("objectType", "person2")
                        .toJobParameters();
        System.out.println("job name :: " + jsonJob.getName());
        jobLauncher.run(jsonJob, jobParameters);

        fileJob = new FileJob();
        fileJob.setName("person1");
        fileService.createFile(fileJob);
        jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("fileName", "C:/tmp//workspace/gs-batch-processing/src/test/resources/json/person2.json")
                        .addString("objectType", "person1")
                        .toJobParameters();
        jobLauncher.run(jsonJob, jobParameters);

        fileJob = new FileJob();
        fileJob.setName("user1");
        fileService.createFile(fileJob);

        jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("fileName", "C:/tmp//workspace/gs-batch-processing/src/test/resources/json/user1.json")
                        .addString("objectType", "user1")
                        .toJobParameters();
        jobLauncher.run(jsonJob, jobParameters);

    }



    @Test
    public void partitionUserJob() throws Exception {
        JobParameters jobParameters =
                new JobParametersBuilder()
                                       .toJobParameters();
        jobLauncher.run(partitionUserJob, jobParameters);
    }


    @Test
    public void partitionRangeJob() throws Exception {
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .toJobParameters();
        jobLauncher.run(partitionRangeJob, jobParameters);
    }

}
