package com.sa.dev.batch.json;


import com.sa.dev.batch.entity.Person;
import com.sa.dev.batch.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by qu04jl on 5-9-2017.
 */
@Component
@Slf4j
public class JsonProcessor implements ItemProcessor<Object,Object> {


    @Override
    public Object process(Object o) throws Exception {
        if(o instanceof Person){
            Person person=(Person)o;
            final String firstName = person.getFirstName().toUpperCase();
            final String lastName = person.getLastName().toUpperCase();
            log.info("Converting (" + person + ") into (" + person + ")");
            return person;
        }
        else if(o instanceof User)
        {
            User user=(User)o;
            log.info("Converting (" + user + ") into (" + user + ")");
            return user;
        }
        return null;
    }
}
