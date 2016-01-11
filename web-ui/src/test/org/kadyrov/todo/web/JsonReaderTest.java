package org.kadyrov.todo.web;

import org.junit.Ignore;
import org.junit.Test;
import org.kadyrov.todo.web.json.JsonReader;

import java.io.StringReader;

import static org.junit.Assert.*;

public class JsonReaderTest {


    @Test
    @Ignore
    public void testBeginEndObject() throws Exception {
        String source = "{}";
        JsonReader reader = new JsonReader(new StringReader(source));
        reader.beginObject();
        reader.endObject();
        assertTrue(true);
    }

    @Test
    @Ignore
    public void testObject() throws Exception {
        String source = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
        JsonReader reader = new JsonReader(new StringReader(source));
        reader.beginObject();
        while (reader.hasNext()){
            System.out.println("Name: '"+reader.nextString()+"'");
            reader.skip();
            reader.skip();
            System.out.println("Value: '"+reader.nextString()+"'");
            reader.skip();
        }
        reader.endObject();
    }

    @Test
    public void testArray() throws Exception {
        String source = "[{\"key1\":\"value1\",\"key2\":\"value2\"}]";
        JsonReader reader = new JsonReader(new StringReader(source));
        reader.beginArray();
        reader.beginObject();
        while (reader.hasNext()){
            System.out.println("Name: '"+reader.nextString()+"'");
//            reader.skip();
//            reader.skip();
            System.out.println("Value: '"+reader.nextString()+"'");
//            reader.skip();
        }
        reader.endObject();
        reader.endArray();
    }

}