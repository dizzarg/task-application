package org.kadyrov.task.json.reader;

import org.kadyrov.task.json.items.JSNull;

public class JSNullFactory extends JSElementFactory {

    @Override
    public JSNull make() {
        return new JSNull();
    }
}
