package org.kadyrov.todo.json.reader;

import org.kadyrov.todo.json.items.JSElement;

/**
 * Created by damir on 04.01.2016.
 */
public class JSBooleanFactory extends JSElementFactory {
    private JSReader reader;

    public JSBooleanFactory(JSReader reader) {
        super();
        this.reader = reader;
    }

    @Override
    public JSElement make() {
//        reader.nextBoolean();
        return null;
    }
}
