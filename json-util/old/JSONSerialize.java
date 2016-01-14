package org.kadyrov.task.json;

import org.kadyrov.task.json.exception.JsonBuilderException;

import java.lang.reflect.Field;

public class JSONSerialize {

    public String toJSON(Object bean){
        JSONObject object = new JSONObject(new JSONReader("{}"));
        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName()+ " : "+ field.getType().toString() + " : " + field.get(bean));
                JSONElement element = create(field, bean);
                object.put(field.getName(), element);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    private JSONString create(Field field, Object bean) throws IllegalAccessException {
        boolean isString = field.getType().isAssignableFrom(String.class);
        JSONString string = new JSONString(new JSONReader(""));
        if(isString){
            string.setValue((String) field.get(bean));
        }
        return string;
    }

    public <T> T fromJson(String json, Class<T> bean) throws JsonBuilderException{
        JSONReader reader = new JSONReader(json);
        JSONObject object = new JSONObject(reader);
        return write(object, bean);
    }

    private  <T> T write(JSONObject object, Class<T> entity) throws JsonBuilderException {
        try {
            T t = entity.newInstance();
            for (String key : object.keySet()) {
                JSONElement value = object.getElement(key);
                JSONString jsonString = (JSONString) value;
                ReflectionUtil.fillFiled(entity, t, key, jsonString.getValue());
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new JsonBuilderException(e);
        }
    }

}
