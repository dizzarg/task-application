package org.kadyrov.task.json;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Ignore
public class JsonTest {

    public static class Entity {
        private String key;

        @Override
        public String toString() {
            return "Entity{" +
                    "key='" + key + '\'' +
                    '}';
        }
    }

    public static class InheredEntity extends Entity {
        private String count;
        int number;

        @Override
        public String toString() {
            return "InheredEntity{" +
                    "count=" + count +
                    "} " + super.toString();
        }
    }

    @Test
    public void testListFromString() throws Exception {
        String json = "[\"test1\",\"test2\"]";
        List strings = Json.collectionFromString(json, List.class);
        Assert.assertNotNull(strings);
        System.out.println(strings);
    }

    @Test
    public void testArrayFromString() throws Exception {
        String json = "[\"test1\",\"test2\"]";
        String[] strings = Json.arrayFromString(json, String[].class);
        Assert.assertNotNull(strings);
        System.out.println(Arrays.deepToString(strings));
    }

    @Test
    public void testInheredObjectFromString() throws Exception {
        String json = "{\"key\":\"value\",\"count\":\"1\", \"number\":\"3\"}";
        InheredEntity entity = Json.objectFromString(json, InheredEntity.class);
        Assert.assertNotNull(entity);
        System.out.println(entity.toString());
    }

    static class ArrayClass {
        String name;
        String[] arr;

        @Override
        public String toString() {
            return "ArrayClass{" +
                    "name='" + name + '\'' +
                    ", arr=" + Arrays.toString(arr) +
                    '}';
        }
    }

    @Test
    public void testObjectWithArrayFromString() throws Exception {
        String json = "{\"name\":\"string array\", \"arr\":[\"1\",\"2\"]}";
        ArrayClass arrayClass = Json.objectFromString(json, ArrayClass.class);
        System.out.println(arrayClass.toString());
        Assert.assertEquals(arrayClass.name, "string array");
        Assert.assertArrayEquals(arrayClass.arr, new String[]{"1", "2"});
    }

    @Test
    public void testObjectFromString() throws Exception {
        String json = "{\"key\":\"value\"}";
        Entity entity = Json.objectFromString(json, Entity.class);
        Assert.assertEquals(entity.key, "value");
    }
}
