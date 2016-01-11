package org.kadyrov.todo.json;

public class JSONString extends JSONElement {

    private String value;

    public JSONString(JSONReader reader) {
        super(reader);
    }

    @Override
    void fromJSON() {
        reader.skipSpaces();
        begin();
        value = reader.nextString();
        end();
    }

    @Override
    String toJSON() {
        return String.format("\\\"%s\\\"", value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected char getBeginChar( ) {
        return '"';
    }

    @Override
    protected char getEndChar( ) {
        return '"';
    }


}
