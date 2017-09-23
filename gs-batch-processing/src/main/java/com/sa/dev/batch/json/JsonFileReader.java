package com.sa.dev.batch.json;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.sa.dev.batch.entity.Person;
import com.sa.dev.batch.entity.User;
import com.sa.dev.batch.util.JsonConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by qu04jl on 5-9-2017.
 */
@Component
@StepScope
@Slf4j
public class JsonFileReader implements ItemReader<Object> {


    @Value("#{jobParameters['fileName']}")
    private String fileName;

    @Value("#{jobParameters['objectType']}")
    private String objectType;

    private JsonReader reader = null;
    private InputStream inputStream=null;

    @Override
    public Object read() throws Exception {

        if (reader.hasNext()) {
            if (objectType != null) {
                if (objectType.startsWith("person")) {
                    Person person = JsonConvertor.getObject(reader, Person.class);
                    if (person.getFirstName() != null) {
                        log.info("person mode: " + person);
                        return person;
                    } else {
                        throw new JsonParsePersonException("Json reader exception person");
                    }
                } else if (objectType.startsWith("user")) {
                    User user = JsonConvertor.getObject(reader, User.class);
                    if (user.getId() != null) {
                        log.info("user mode: " + user);
                        return user;
                    } else {
                        throw new JsonParsePersonException("Json reader exception for user");
                    }
                } else {
                    return JsonConvertor.getObject(reader, Object.class);
                }
            } else {

                return JsonConvertor.getObject(reader, Object.class);
            }

        }
        return null;
    }


    @PostConstruct
    public void readStream() {
        try {
            inputStream = new FileInputStream(fileName);
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            //
            while (reader.hasNext()) {
                JsonToken nextToken = reader.peek();
                log.info(nextToken.name());
                if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                    reader.beginObject();
                    reader.skipValue();

                }
                if (JsonToken.BEGIN_ARRAY.equals(nextToken)) {
                    reader.beginArray();

                    break;
                }
            }
        } catch (Exception ex) {
            log.info("error in json file reader" + ex.getMessage());
        }
    }

    @PreDestroy
    public void closeStream() {
        if(inputStream!=null)
        {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("error in closing the stream "+e.getMessage());
            }
        }

    }

}
