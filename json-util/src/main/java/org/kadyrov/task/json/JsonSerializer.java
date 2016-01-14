package org.kadyrov.task.json;

import org.kadyrov.task.json.util.ReflectionUtil;

import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class JsonSerializer {

    StringSerializerFactory stringSerializerFactory = new StringSerializerFactory();

    public String serializeObject(Object object){
        Objects.requireNonNull(object);
        Serializer<Object> serializer = new ObjectSerializer();
        return serialize(object, serializer);
    }

    public String serializeArray(Object object){
        Objects.requireNonNull(object);
        Serializer<Object> serializer = new ArraySerializer();
        return serialize(object, serializer);
    }

    private String serialize(Object object, Serializer<Object> serializer) {
        serializer.target(object);
        return serializer.toJson();
    }

    private interface Serializer<T> {
        void target(T t);
        String toJson();
    }

    public class FormatSerializer implements Serializer<Object> {
        private Object target;
        private Format format;

        @Override
        public void target(Object o) {
            target = o;
            if(Date.class.isInstance(o)){
                format = new SimpleDateFormat("\"yy-MM-dd HH:mm:ss\"");
            } else if (String.class.isInstance(o)){
                format = new MessageFormat("\"{0}\"");
            }
        }

        @Override
        public String toJson() {
            return format.format(target);
        }
    }


    private interface SerializerFactory{
        Serializer serializer();
    }

    public class StringSerializerFactory implements SerializerFactory {

        private String bracket;

        public void setBracket(String bracket) {
            this.bracket = bracket;
        }

        @Override
        public Serializer serializer() {
            return new StringSerializer(bracket);
        }
    }




    private class StringSerializer implements Serializer<String> {
        private String string;
        final protected String bracket;

        public StringSerializer(String bracket) {
            this.bracket = bracket;
        }

        @Override
        public void target(String s) {
            string = s;
        }

        @Override
        public String toJson() {
            return bracket + string + bracket;
        }

    }

    private class DateSerializer implements Serializer<Date> {
        private final SimpleDateFormat sdf;
        private Date date;
        final protected String bracket;

        private DateSerializer(SimpleDateFormat sdf, String bracket) {
            this.sdf = sdf;
            this.bracket = bracket;
        }

        @Override
        public void target(Date date) {
            this.date = date;
        }

        @Override
        public String toJson() {
            return bracket + sdf.format(date) + bracket;
        }
    }


    public class MapSerializerFactory implements SerializerFactory {

        @Override
        public Serializer serializer() {
            return new MapSerializer();
        }
    }

    public class MapSerializer implements Serializer<Map> {
        private Map map;

        @Override
        public void target(Map map) {
            this.map = map;
        }

        @Override
        public String toJson() {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for (Object o : map.keySet()) {
                builder.append('\"');
                builder.append(String.valueOf(o));
                builder.append('\"');
                builder.append(':');
                builder.append('\"');
                Object value = map.get(o);

                if (ReflectionUtil.isLeafClass(value)){
                    builder.append(String.valueOf(value));
                }
                builder.append('\"');
            }
            builder.append("}");
            return builder.toString();
        }

    }
    private class ObjectSerializer implements Serializer<Object> {
        private Object target;
        @Override
        public void target(Object o) {
            target = o;
        }
        @Override
        public String toJson() {
            if(ReflectionUtil.isMap(target)){
                MapSerializer serializer = new MapSerializer();
                serializer.target((Map) target);
                return serializer.toJson();
            }
            return null;
        }

    }

    private class ArraySerializer implements Serializer<Object> {
        private Object target;

        @Override
        public void target(Object o) {
            target = o;
        }

        @Override
        public String toJson() {
            return null;
        }
    }
}
