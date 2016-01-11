package org.kadyrov.todo.json.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JSObject extends JSElement {

    Map<String, JSElement> map;

    public JSObject() {
        this.map = new HashMap<>();
    }

    public JSObject(Map<String, JSElement> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String key : map.keySet()) {
//            sb.append("\n");
            sb.append("\"");
            sb.append(key);
            sb.append("\":");
            sb.append(map.get(key).toString());
            sb.append(",");
        }
        if(!map.isEmpty()){
//            sb.setCharAt(sb.length()-1, '\n');
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("}");
        return sb.toString();
    }

    public boolean isEmpty() {
        return map == null || map.isEmpty();
    }

    public int size() {
        return isEmpty() ? 0 : map.size();
    }


    /* @Override
    public void fromJSON(JSReader reader) {
        reader.skipSpaces();
        begin(reader);
        map = new HashMap<>();
        while (hasNext(reader)){
            JSString key = new JSString();
            key.fromJSON(reader);
            skipColon(reader);
            map.put(key.getValue(), reader.nextElement());
        }
        end(reader);
    }

    @Override
    public String toJSON(JSWriter writer) {
        return writer.toJSON(this);
    }*/
/*
    private boolean hasNext(JSReader reader) {
        if( reader.peek() == ','){
            skipComma(reader);
        }
        return reader.peek() != getEndChar();
    }*/

   /* @Override
    protected char getBeginChar( ) {
        return '{';
    }

    @Override
    protected char getEndChar( ) {
        return '}';
    }*/
/*
    private void skipComma(JSReader reader) {
        reader.skipSpaces();
        if(reader.pop()!=','){
            throw new IllegalStateException();
        }
    }

    public void skipColon(JSReader reader) {
        reader.skipSpaces();
        if(reader.pop()!=':'){
            throw new IllegalStateException();
        }
    }*/

    public Set<String> keySet(){
        return map.keySet();
    }

    public JSString getString(String key){
        return (JSString) map.get(key);
    }

    public boolean containsKey(String key){
        return map.containsKey(key);
    }

    public JSArray getArray(String key){
        return (JSArray) map.get(key);
    }

    public JSObject getObject(String key){
        return (JSObject) map.get(key);
    }

    public void put(String name, JSElement element) {
        map.put(name, element);
    }

}
