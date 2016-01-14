package org.kadyrov.task.json.writer;

import org.kadyrov.task.json.exception.JsonBuilderException;
import org.kadyrov.task.json.items.JSArray;
import org.kadyrov.task.json.items.JSElement;
import org.kadyrov.task.json.items.JSObject;
import org.kadyrov.task.json.items.JSString;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

import static org.kadyrov.task.json.util.ReflectionUtil.*;

public class JSWriter {

    private final Object object;

    public JSWriter(Object object) {
        this.object = object;
    }

    public boolean isArray(){
        return isIterable(object);
    }

    public JSObject getObject() throws JsonBuilderException {
        return fromObject(object);
    }

    public JSArray getArray() throws JsonBuilderException {
        return fromArray(object);
    }

    private static JSArray fromArray(Object obj) throws JsonBuilderException {
        List<JSElement> elements = new ArrayList<>();
        if(obj instanceof Iterable){
            Iterable iterable = (Iterable) obj;
            for (Object o : iterable) {
                elements.add(getJsElement(o));
            }
        } else {
            for (int i = 0, len = Array.getLength(obj); i < len; i++ ){
                elements.add(getJsElement(Array.get(obj, i)));
            }
        }
        return new JSArray(elements);
    }

    private static JSElement getJsElement(Object o) throws JsonBuilderException {
        JSElement element = null;
        if(isLeafClass(o)){
            element = new JSString(String.valueOf(o));
        } else {
            if(isIterable(o)){
                element = fromArray(o);
            } else {
                if(isMap(o)){
                    element = fromMap((Map) o);
                } else {
                    element = fromObject(o);
                }
            }
        }
        return element;
    }

    private static JSObject fromMap(Map map) throws JsonBuilderException {
        Map<String, JSElement> m = new HashMap<>();
        for (Object o : map.keySet()) {
            m.put(o.toString(), getJsElement(o));
        }
        return new JSObject(m);
    }

    private static JSObject fromObject(Object object) throws JsonBuilderException {
        try {
            Map<String, JSElement> properties = new HashMap<>();
            Class<?> objectClass = object.getClass();
            List<Field> fields = getFieldsFromParent(objectClass);
            for (Field declaredField : fields) {
                declaredField.setAccessible(true);
                Object obj = declaredField.get(object);
                properties.put(declaredField.getName(), getJsElement(obj));
            }
            return new JSObject(properties);
        } catch (IllegalAccessException e) {
            throw new JsonBuilderException(e);
        }
    }


}
