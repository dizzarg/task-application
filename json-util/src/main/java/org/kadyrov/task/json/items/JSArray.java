package org.kadyrov.task.json.items;

import java.util.ArrayList;
import java.util.List;

public class JSArray extends JSElement {

    List<JSElement> elements;

    public JSArray() {
        this(new ArrayList<JSElement>());
    }

    public JSArray(List<JSElement> list) {
        elements = list;
    }

    public int size(){
        return elements.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (JSElement element : elements) {
            sb.append(element.toString());
            sb.append(",");
        }
        if(elements.size()>0) {
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("]");
        return sb.toString();
    }

    public JSElement get(int i) {
        return elements.get(i);
    }

    /*
    @Override
    public void fromJSON(JSReader reader) {
        reader.skipSpaces( );
        begin(reader);
        elements = new ArrayList<>();
        while (hasNext(reader)){
            JSElement element = reader.nextElement( );
            elements.add(element);
        }
        end(reader);
    }

    private boolean hasNext(JSReader reader) {
        if( reader.peek() == ','){
            skipComma(reader);
        }
        return reader.peek() != getEndChar();
    }

    public void skipComma(JSReader reader) {
        reader.skipSpaces();
        if(reader.pop()!=','){
            throw new IllegalStateException();
        }
    }*/

//    @Override
//    public String toJSON(JSWriter writer) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for (JSElement element : elements) {
//            sb.append(element.toJSON(writer));
//            sb.append(",");
//        }
//        sb.deleteCharAt(sb.length()-1);
//        sb.append("]");
//        return sb.toString();
//    }
/*

    @Override
    public String toJSON(JSWriter writer) {
        return writer.toJSON(this);
    }


    @Override
    protected char getBeginChar( ) {
        return '[';
    }

    @Override
    protected char getEndChar( ) {
        return ']';
    }
*/

}
