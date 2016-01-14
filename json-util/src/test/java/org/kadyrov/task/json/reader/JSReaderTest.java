package org.kadyrov.task.json.reader;

import org.junit.Assert;
import org.junit.Test;
import org.kadyrov.task.json.items.JSObject;

public class JSReaderTest {

    @Test
    public void testArray() throws Exception {
        String json = "{\"key1\":\"value1\",\"key2\":[\"test1\",\"test2\"]}";
        JSReader reader = new JSReader(json);
        JSObjectFactory factory = new JSObjectFactory(reader);
        JSObject object = factory.make();
        Assert.assertNotNull(object);
        Assert.assertEquals(object.size(), 2);
    }

    @Test
    public void testFromString_3() throws Exception {
        String json = "{\"key1\":\"val\\\"ue1\"}";
        JSReader reader = new JSReader(json);
        JSObjectFactory factory = new JSObjectFactory(reader);
        JSObject object = factory.make();
        Assert.assertNotNull(object);
        Assert.assertEquals(object.size(), 1);
    }

    @Test
    public void testFromString_2() throws Exception {
        String json = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
        JSReader reader = new JSReader(json);
        JSObjectFactory factory = new JSObjectFactory(reader);
        JSObject object = factory.make();
        Assert.assertNotNull(object);
        Assert.assertEquals(object.size(), 2);
    }

    @Test
    public void testFromString_1() throws Exception {
        String json = "{}";
        JSReader reader = new JSReader(json);
        JSObjectFactory factory = new JSObjectFactory(reader);
        JSObject object = factory.make();
        Assert.assertNotNull(object);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void testFromString_ise_1() throws Exception {
        String json = "";
        JSReader reader = new JSReader(json);
        JSObjectFactory factory = new JSObjectFactory(reader);
        factory.make();
        Assert.fail();
    }
    
}
