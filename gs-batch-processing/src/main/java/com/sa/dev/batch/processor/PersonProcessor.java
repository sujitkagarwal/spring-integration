package com.sa.dev.batch.processor;

import com.sa.dev.batch.entity.Person;
import com.sa.dev.batch.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.Future;

/**
 * Created by sujitagarwal on 02/10/17.
 */
@Slf4j
public class PersonProcessor implements ItemProcessor<Person, Person> {

    @Value("#{stepExecutionContext[futureObject]}")
    private Future<User> futureObj;

    @Override
    public Person process(final Person person) throws Exception {
        log.info(futureObj.get().getName());
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        final Person transformedPerson = new Person(firstName, lastName);
        log.info("Converting (" + person + ") into (" + transformedPerson + ")");
        return transformedPerson;
    }

}