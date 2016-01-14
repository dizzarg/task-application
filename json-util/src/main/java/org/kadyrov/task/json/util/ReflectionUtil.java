package org.kadyrov.task.json.util;

import org.kadyrov.task.json.items.JSString;

import java.lang.reflect.Field;
import java.util.*;

public final class ReflectionUtil {

    public static <T> void fillField(Object o, T t, Field field) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        field.set(t, o);
    }

    public static <T> void fillField(JSString string, T t, Field field) throws NoSuchFieldException, IllegalAccessException {
        Class<?> type = field.getType();
        field.setAccessible(true);
        if(int.class.equals(type) || Integer.class.equals(type)){
            field.set(t, Integer.valueOf(string.getValue()));
        } else if(double.class.equals(type) || Double.class.equals(type)){
            field.set(t, Double.valueOf(string.getValue()));
        } else if(float.class.equals(type) || Float.class.equals(type)){
            field.set(t, Float.valueOf(string.getValue()));
        } else if(String.class.equals(type)) {
            field.set(t, string.getValue());
        }
    }

    public static List<Field> getFieldsFromParent(Class<?> clazz) {
        ArrayList<Field> fields = getFields(clazz);
        Class<?> superclass = clazz.getSuperclass();
        if(superclass.getName().startsWith("java.lang")){
            return fields;
        }
        fields.addAll(getFieldsFromParent(superclass));
        return fields;
    }

    public static ArrayList<Field> getFields(Class<?> clazz) {
        return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
    }

    public static boolean isLeafClass(Object obj) {
        return obj.getClass().getName().startsWith("java.lang") || obj instanceof Date;
    }

    public static boolean isIterable(Object obj) {
        return obj.getClass().isArray() || obj instanceof Iterable || obj instanceof Enumeration;
    }

    public static boolean isIterable(Class clazz){
        return clazz.isArray();
    }


    public static boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    private ReflectionUtil() {}
}
