package com.sa.dev.batch;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.dev.batch.entity.User;

import java.io.File;

/**
 * Created by qu04jl on 5-9-2017.
 */
public class TestMain {

    public static void main(String[] args) {

        String json = "[\n" +
                "  {\n" +
                "    \"firstName\" : \"xyz\",\n" +
                "    \"lastName\" : \"abc\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"firstNameqqqqq\" : \"xyz1234\"\n" +
                "    \"lastName\" : \"abc1234\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"firstName\" : \"xyz5678\",\n" +
                "    \"lastName\" : \"abc5678\"\n" +
                "  }\n" +
                "\n" +
                "]\n";

        try {
            ObjectMapper  mapper=new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonFactory f = new MappingJsonFactory();
            JsonParser jp = f.createParser(new File("/Users/sujitagarwal/workspace_micro/gs-batch-processing/src/test/resources/json/user2.json"));
            JsonToken current;
            current = jp.nextToken();
            if (current == JsonToken.START_ARRAY) {
                // For each of the records in the array
                try {
                while (jp.nextToken() != JsonToken.END_ARRAY) {
                    // read the record into a tree model,
                    // this moves the parsing position to the end of it
                        try {
                            User user=   mapper.readValue(jp,User.class);
                            //User user = jp.readValueAs(User.class);

                            //mapper.readValue(node,User.class);
                            // And now we have random access to everything in the object
                            System.out.println(user.toString());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error: records should be an array: skipping.");
                jp.skipChildren();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
