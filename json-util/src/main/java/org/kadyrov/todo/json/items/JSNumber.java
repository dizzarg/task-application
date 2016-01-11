package org.kadyrov.todo.json.items;

/**
 * Created by damir on 04.01.2016.
 */
public class JSNumber extends JSElement {

    private Number number;

    public JSNumber(Number number) {
        this.number = number;
    }
/*
    @Override
    protected char getBeginChar() {
        return 0;
    }

    @Override
    protected char getEndChar() {
        return 0;
    }

    @Override
    public void fromJSON(JSReader reader) {

    }

    @Override
    public String toJSON(JSWriter writer) {
        return null;
    }*/
}
