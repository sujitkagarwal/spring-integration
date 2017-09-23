package com.sa.dev.batch.adapter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sa.dev.batch.entity.Person;

import java.io.IOException;

/**
 * Created by qu04jl on 6-9-2017.
 */
public class PersonResultObjectAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.getRawType() != Person.class)
        {
            return null;
        }

      //  TypeAdapter<Person> defaultAdapter = (TypeAdapter<Person>) gson.getDelegateAdapter(this, type);
        return (TypeAdapter<T>) new MyResultObjectAdapter(gson);
    }

    public class MyResultObjectAdapter extends TypeAdapter<Person> {

        protected TypeAdapter<Person> defaultAdapter;
        private final TypeAdapter<JsonElement> jsonElementTypeAdapter;

        public MyResultObjectAdapter(Gson gson) {
            this.defaultAdapter = gson.getAdapter(Person.class);
            this.jsonElementTypeAdapter = gson.getAdapter(JsonElement.class);
        }

        @Override
        public void write(JsonWriter out, Person value) throws IOException {
            defaultAdapter.write(out, value);
        }

        @Override
        public Person read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.BEGIN_OBJECT) {
                try {
                    JsonObject responseObject = (JsonObject) jsonElementTypeAdapter.read(in);
                    System.out.println(responseObject.getAsJsonObject().toString());
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                   in.nextNull();
                   // JsonObject responseObject = (JsonObject) jsonElementTypeAdapter.read(in);

                }
            }
          /*
            This is the critical part. So if the value is a string,
            Skip it (no exception) and return null.
            */
            System.out.println(in.peek());
            if (in.peek() == JsonToken.STRING) {
                in.skipValue();
                return null;
            }
            Person person=null;
            try {
                 person = defaultAdapter.read(in);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
           return person;
        }
    }
}