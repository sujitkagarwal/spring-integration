package com.sa.dev.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by sujitagarwal on 01/09/17.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BatchTest {

    @Autowired
    @Qualifier(value = "importUserJob1")
    Job job1;
    @Autowired
    @Qualifier(value = "importUserJob")
    Job job;

    @Autowired
    JobLauncher jobLauncher;

    @Test
    public void batchTest() {
        JobParametersBuilder jobBuilder = new JobParametersBuilder();
        jobBuilder.addDate("Date", new Date());
        JobParameters jobParameter = jobBuilder.toJobParameters();
        try {
            System.out.println(job.getName());
            System.out.println(job1.getName());
            jobLauncher.run(job, jobParameter);
            jobLauncher.run(job1, jobParameter);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
