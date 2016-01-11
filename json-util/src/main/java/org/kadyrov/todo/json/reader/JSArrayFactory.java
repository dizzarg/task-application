package org.kadyrov.todo.json.reader;

import org.kadyrov.todo.json.items.JSArray;
import org.kadyrov.todo.json.items.JSElement;

import java.util.ArrayList;
import java.util.List;

public class JSArrayFactory extends JSElementFactory {

    private final JSReader reader;

    public JSArrayFactory(JSReader reader) {
        this.reader = reader;
    }

    @Override
    public JSArray make() {
        reader.skipSpaces();
        reader.verify('[');
        List<JSElement> list = new ArrayList<>();
        while (hasNext()){
            JSElement element = reader.nextElement();
            list.add(element);
        }
        reader.verify(']');
        return new JSArray(list);
    }

    private boolean hasNext() {
        if( reader.peek() == ','){
            reader.verify(',');
        }
        return reader.peek() != ']';
    }

}
