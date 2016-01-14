package org.kadyrov.task.json.reader;

import org.kadyrov.task.json.items.JSElement;
import org.kadyrov.task.json.items.JSObject;

import java.util.HashMap;
import java.util.Map;

public class JSObjectFactory extends JSElementFactory {

    private final JSReader reader;

    public JSObjectFactory(JSReader reader) {
        this.reader = reader;
    }

    @Override
    public JSObject make() {
        reader.skipSpaces();
        reader.verify('{');
        Map<String, JSElement> map = new HashMap<>();
        while (hasNext()){
            reader.verify('"');
            String key = reader.nextString();
            reader.verify('"');
            reader.skipSpaces();
            reader.verify(':');
            JSElement element = reader.nextElement();
            map.put(key, element);
        }
        reader.verify('}');
        return new JSObject(map);
    }

    private boolean hasNext() {
        reader.skipSpaces();
        if( reader.peek() == ','){
            reader.verify(',');
        }
        reader.skipSpaces();
        return reader.peek() != '}';
    }
}
