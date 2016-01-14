package org.kadyrov.task.json.items;

public class JSString extends JSElement {

    private String value;

    public JSString() {}

    public JSString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", value);
    }

    /*@Override
    public void fromJSON(JSReader reader) {
        reader.skipSpaces();
        begin(reader);
        value = reader.nextString();
        end(reader);
    }*/

//    @Override
//    public String toJSON(JSWriter writer) {
//        return String.format("\\\"%s\\\"", value);
//    }
/*

    @Override
    public String toJSON(JSWriter writer) {
        return writer.toJSON(this);
    }

    @Override
    protected char getBeginChar( ) {
        return '"';
    }

    @Override
    protected char getEndChar( ) {
        return '"';
    }
*/

}
