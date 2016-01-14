package org.kadyrov.task.json;

import java.util.Set;

public class JSONObjects {

    public static String toJSON(JSONObject object){
        StringBuilder sb = new StringBuilder();
        Set<String> keys = object.keySet();
        for (String key : keys) {
            sb.append("\\\"");
            sb.append(key);
            sb.append("\\\"");
            sb.append(toJSON(object.getElement(key)));
        }
        return sb.toString();
    }

    private static String toJSON(JSONElement element) {
        return element.toJSON();
    }

    public static String toJSON(JSONString string){
        StringBuilder sb = new StringBuilder();
        sb.append("\\\"");
        sb.append(string.getValue());
        sb.append("\\\"");
        return sb.toString();
    }

    public static String toJSON(JSONArray string){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public static <T> T fromString(String json, Class<T> entity) throws Exception {
        JSONReader reader = new JSONReader(json);
        JSONObject object = new JSONObject(reader);
        return write(object, entity);
    }

    public static <T> T write(JSONObject object, Class<T> entity) throws Exception {
        try {
            T t = entity.newInstance();
            for (String key : object.keySet()) {
                JSONElement value = object.getElement(key);
                JSONString jsonString = (JSONString) value;
                ReflectionUtil.fillFiled(entity, t, key, jsonString.getValue());
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    static JSONObject fromString(String json){
        if(!json.startsWith("{")) throw new IllegalStateException();
        JSONReader reader = new JSONReader(json);
        return new JSONObject(reader);
    }

}
