package org.kadyrov.todo.json.reader;

import org.kadyrov.todo.json.items.JSNull;

public class JSNullFactory extends JSElementFactory {

    @Override
    public JSNull make() {
        return new JSNull();
    }
}
