package com.sa.dev.batch.listener;

import com.sa.dev.batch.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

/**
 * Created by qu04jl on 5-9-2017.
 */
@Component
@Slf4j
public class ReadStepListener implements ItemReadListener<Person> {



    @Override
    public void beforeRead() {
        log.info("beforeRead");
    }

    @Override
    public void afterRead(Person person) {
        log.info("afterRead:::"+person.getFirstName());

    }

    @Override
    public void onReadError(Exception e) {
        log.info("onReadError");
    }
}
