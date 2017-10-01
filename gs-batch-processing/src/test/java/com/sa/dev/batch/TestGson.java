package com.sa.dev.batch;
/**
 * Created by qu04jl on 7-9-2017.
 */

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.sa.dev.batch.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestGson {
    private static final Logger log = LoggerFactory.getLogger(TestGson.class);
    Gson gson =new GsonBuilder().setLenient().create();
    public static void main(String[] args) {
        new TestGson().readParserStream();
    }


    public void readStream() {
        try {
            InputStream inputStream = new FileInputStream("/Users/sujitagarwal/workspace_micro/gs-batch-processing/src/test/resources/json/user1.json");
            JsonReader   reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            //
            JsonToken nextToken=null;
            while (reader.hasNext()) {
                 nextToken = reader.peek();
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

            while (reader.hasNext() && !JsonToken.END_ARRAY.equals(nextToken) ) {
                User user = gson.fromJson(reader, User.class);
                log.info(user.toString());
            }
        } catch (Exception ex) {
            log.info("error in json file reader" + ex.getMessage());
        }
    }



    public void readParserStream() {
        try {
            InputStream inputStream = new FileInputStream("/Users/sujitagarwal/workspace_micro/gs-batch-processing/src/test/resources/json/user1.json");
        //    JsonReader   reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(new InputStreamReader(inputStream, "UTF-8"));
            if(jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                JsonElement f1 = jsonObject.get("agrmntAgrmntRlnshps");
                if(f1.isJsonArray())
                {
                    JsonArray jsonArray=f1.getAsJsonArray();
                    for (int i=0;i<jsonArray.size();i++)
                    {
                        System.out.println("true--"+i);
                    }

                }
            }

        } catch (Exception ex) {
            log.info("error in json file reader" + ex.getMessage());
        }
    }
}
