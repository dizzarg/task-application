package org.kadyrov.task.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JSONObject extends JSONElement {

    Map<String, JSONElement> map;

    public JSONObject(JSONReader reader) {
        super(reader);
    }

    @Override
    void fromJSON( ) {
        reader.skipSpaces();
        begin();
        map = new HashMap<>();
        while (hasNext()){
            JSONString key = new JSONString(reader);
            skipColon();
            map.put(key.getValue(), reader.nextElement());
        }
        end();
    }

    @Override
    String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String key : map.keySet()) {
            sb.append("\n");
            sb.append("\"");
            sb.append(key);
            sb.append("\":");
            sb.append(map.get(key).toJSON());
            sb.append(",");
        }
        if(!map.isEmpty()){
            sb.setCharAt(sb.length()-1, '\n');
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

    private boolean hasNext() {
        if( reader.peek() == ','){
            skipComma();
        }
        return reader.peek() != getEndChar();
    }

    @Override
    protected char getBeginChar( ) {
        return '{';
    }

    @Override
    protected char getEndChar( ) {
        return '}';
    }

    private void skipComma() {
        reader.skipSpaces();
        if(reader.pop()!=','){
            throw new IllegalStateException();
        }
    }

    public void skipColon() {
        reader.skipSpaces();
        if(reader.pop()!=':'){
            throw new IllegalStateException();
        }
    }

    public Set<String> keySet(){
        return map.keySet();
    }

    public JSONElement getElement(String key){
        return map.get(key);
    }

    public void put(String name, JSONElement element) {
        map.put(name, element);
    }

}
