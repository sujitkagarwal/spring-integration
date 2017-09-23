package com.sa.dev.batch.listener;


import com.sa.dev.batch.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

/**
 * Created by qu04jl on 5-9-2017.
 */
@Slf4j
public class ProcessStepListener implements ItemProcessListener<Person,Person> {



    @Override
    public void beforeProcess(Person person) {

    }

    @Override
    public void afterProcess(Person person, Person person2) {

    }

    @Override
    public void onProcessError(Person person, Exception e) {
        log.info("on error for person "+person.getFirstName());
        log.info("on error for error  "+e.getMessage());
    }
}
