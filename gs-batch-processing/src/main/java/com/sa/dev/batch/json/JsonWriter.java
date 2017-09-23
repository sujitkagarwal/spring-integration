package com.sa.dev.batch.json;

import com.sa.dev.batch.entity.Person;
import com.sa.dev.batch.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.Iterator;
import java.util.List;

/**
 * Created by qu04jl on 5-9-2017.
 */
@Slf4j
public class JsonWriter implements ItemWriter<Object> {



    @Override
    public void write(List<?> list) throws Exception {
        log.info("json writer "+list.size());
        if(list.size()>0) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof Person) {
                    Person person = (Person) o;
                    final String firstName = person.getFirstName().toUpperCase();
                    final String lastName = person.getLastName().toUpperCase();
                    log.info("JsonWriter (" + person + ") into (" + person + ")");

                } else if (o instanceof User) {
                    User user = (User) o;
                    log.info("JsonWriter (" + user + ") into (" + user + ")");
                }
            }
        }
    }
}
