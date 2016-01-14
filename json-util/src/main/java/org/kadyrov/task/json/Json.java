package org.kadyrov.task.json;

import org.kadyrov.task.json.items.JSArray;
import org.kadyrov.task.json.items.JSElement;
import org.kadyrov.task.json.items.JSObject;
import org.kadyrov.task.json.items.JSString;
import org.kadyrov.task.json.reader.JSArrayFactory;
import org.kadyrov.task.json.reader.JSObjectFactory;
import org.kadyrov.task.json.reader.JSReader;
import org.kadyrov.task.json.util.ReflectionUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Json {


    @SuppressWarnings("unchecked")
    public static <T> T collectionFromString(String json, Class<T> bean) throws Exception {
        JSReader reader = new JSReader(json);
        JSArrayFactory factory = new JSArrayFactory(reader);
        JSArray jsArray = factory.make();
        T t = null;
        if(bean.equals(List.class)){
            List list = new ArrayList();
            for (int i = 0; i < jsArray.size(); i++) {
                JSElement value = jsArray.get(i);
                if(value instanceof JSString){
                    list.add(((JSString) value).getValue());
                }
                if (value instanceof JSObject){
                    Object o1 = fillFields(Object.class, (JSObject) value);
                    list.add(o1);
                }
            }
            t = (T) list;
        }
        return t;
    }

    public static <T> T[] arrayFromString(String json, Class<T[]> bean) throws Exception {
        JSReader reader = new JSReader(json);
        JSArrayFactory factory = new JSArrayFactory(reader);
        JSArray jsArray = factory.make();
        return fillArray(jsArray, bean);
    }

    @SuppressWarnings("unchecked")
    public static <T> T objectFromString(String json, Class<T> bean) throws Exception {
        JSReader reader = new JSReader(json);
        JSObjectFactory factory = new JSObjectFactory(reader);
        JSObject jsObject = factory.make();
        if (ReflectionUtil.isMap(bean)){
            Map<String, String> result = new HashMap<>();
            for (String s : jsObject.keySet()) {
                result.put(s, jsObject.getString(s).getValue());
            }
            return (T) result;
        } else {
            return fillFields(bean, jsObject);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] fillArray(JSArray jsArray, Class<T[]> bean) throws IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {
        Object[] objects = new Object[jsArray.size()];
        for (int i = 0; i < jsArray.size(); i++) {
            JSElement value = jsArray.get(i);
            if(value instanceof JSString){
                objects[i] = ((JSString) value).getValue();
            }
            if (value instanceof JSObject){
                Object o1 = fillFields(Object.class, (JSObject) value);
                Array.set(objects, i, o1);
            }
        }
        return Arrays.copyOf(objects, objects.length, bean);
    }

    private static <T> T fillFields(Class<T> bean, JSObject jsObject) throws IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {
        T t = callConstructor(bean);
        fillFields(jsObject, bean, t);
        return t;
    }

    @SuppressWarnings("unchecked")
    private static <T> T callConstructor(Class<T> bean) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?>[] declaredConstructors = bean.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            declaredConstructor.setAccessible(true);
            if(declaredConstructor.getParameterTypes().length == 0){
                return  (T) declaredConstructor.newInstance();
            }
        }
        return bean.newInstance();
    }

    private static <T> void fillFields(JSObject jsObject, Class<? super T> bean, T t) throws NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Field[] fields = bean.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            Class<?> type = field.getType();
            if(!jsObject.containsKey(key)) continue;
            if (ReflectionUtil.isIterable(type)){
                if (type.isArray()){
                    JSArray array = jsObject.getArray(key);
                    Object[] objects = fillArray(array, Object[].class);
                    ReflectionUtil.fillField(objects, t, field);
                }
                type.getInterfaces();
            }else if(ReflectionUtil.isLeafClass(type)){
                JSString jsString = jsObject.getString(key);
                ReflectionUtil.fillField(jsString, t, field);
            } else if (ReflectionUtil.isMap(type)){
                //
            } else {
                JSObject object = jsObject.getObject(key);
                fillFields(bean, object);
            }

        }
        Class<? super T> superclass = bean.getSuperclass();
        if(!superclass.getName().startsWith("java.lang")){
            fillFields(jsObject, superclass, t);
        }
    }

}
