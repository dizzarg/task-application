package org.kadyrov.task.json.reader;

import org.kadyrov.task.json.items.JSString;

public class JSStringFactory extends JSElementFactory {

    private final JSReader reader;

    public JSStringFactory(JSReader reader) {
        this.reader = reader;
    }

    @Override
    public JSString make() {
        reader.skipSpaces();
        reader.verify('"');
        String value = reader.nextString();
        reader.verify('"');
        return new JSString(value);
    }
}
