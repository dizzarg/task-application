package org.kadyrov.todo.json.reader;

import org.kadyrov.todo.json.items.JSNumber;

/**
 * Created by damir on 04.01.2016.
 */
public class JSNumberFactory extends JSElementFactory {

    private JSReader reader;

    public JSNumberFactory(JSReader reader) {
        super();
        this.reader = reader;
    }

    @Override
    public JSNumber make() {
        Number number = reader.nextNumber();
        return new JSNumber(number);
    }
}
