package org.kadyrov.todo.json;

import java.util.ArrayList;
import java.util.List;

public class JSONArray extends JSONElement {

    List<JSONElement> elements;

    public JSONArray(JSONReader reader) {
        super(reader);
    }

    @Override
    void fromJSON( ) {
        reader.skipSpaces( );
        begin();
        elements = new ArrayList<>();
        while (hasNext()){
            JSONElement element = reader.nextElement( );
            elements.add(element);
        }
        end();
    }

    @Override
    String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (JSONElement element : elements) {
            sb.append(element.toString());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }

    @Override
    protected char getBeginChar( ) {
        return '[';
    }

    @Override
    protected char getEndChar( ) {
        return ']';
    }

    private boolean hasNext() {
        if( reader.peek() == ','){
            skipComma();
        }
        return reader.peek() != getEndChar();
    }

    public void skipComma() {
        reader.skipSpaces();
        if(reader.pop()!=','){
            throw new IllegalStateException();
        }
    }
}
