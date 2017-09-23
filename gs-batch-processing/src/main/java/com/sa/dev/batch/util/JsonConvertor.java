package com.sa.dev.batch.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qu04jl on 7-9-2017.
 */

public class JsonConvertor {
    private static final Logger log = LoggerFactory.getLogger(JsonConvertor.class);

    private static Gson gson = new GsonBuilder().setLenient().create();

    public static <E> E getObject(JsonReader reader, Class input) {
        return gson.fromJson(reader, input);
    }
}
