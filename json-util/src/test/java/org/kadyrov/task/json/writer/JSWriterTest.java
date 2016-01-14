package org.kadyrov.task.json.writer;

import org.junit.Test;
import org.kadyrov.task.json.items.JSObject;

import java.util.*;

import static org.junit.Assert.*;
import static org.kadyrov.task.json.util.ReflectionUtil.isLeafClass;

public class JSWriterTest {

    @Test
    public void testIsLeafClass() throws Exception {
        TestEntity testEntity = new TestEntity();
        assertTrue(isLeafClass(testEntity.dateField));
        assertTrue(isLeafClass(testEntity.strFiled));
        assertTrue(isLeafClass(testEntity.intField));
        assertTrue(isLeafClass(testEntity.intField1));
        assertTrue(isLeafClass(testEntity.strings));
    }

    static class TestEntity{
        String strFiled = "test";
        int intField = 3;
        Integer intField1 = 2;
        Date dateField = new Date();
        private String strings = "private str";
        int[][] arr = new int[][]{{23,45}, {1,23}};
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Map<String, Object> map = new HashMap<>();
        {
            map.put("a1", Integer.valueOf(1));
            map.put("a2", Double.valueOf(1));
            map.put("a3", "string");
        }
    }

    static class InsharedTestEntity extends TestEntity {
        String result = "Success";
        long count = 3L;
    }

    @Test
    public void testGetObject() throws Exception {
        JSWriter writer = new JSWriter(new InsharedTestEntity());
        JSObject object = writer.getObject();
        System.out.println(object.toString());
        assertFalse(writer.isArray());
    }

    @Test
    public void testIsArray() throws Exception {
        JSWriter writer = new JSWriter(new int[]{1,2,3});
        System.out.println(writer.getArray().toString());
        assertTrue(writer.isArray());
    }
}
