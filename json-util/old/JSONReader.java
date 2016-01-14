package org.kadyrov.task.json;

public class JSONReader {

    final private String json;
    private int position;

    public JSONReader(String json) {
        this.json = json;
    }

    public char peek( ) {
        return json.charAt(position);
    }

    public char previous( ) {
        return json.charAt(position-1);
    }

    public char pop( ) {
        char peek = peek( );
        position++;
        return peek;
    }

    public void skipSpaces( ) {
        while (peek()==' ') position++;
    }

    public JSONElement nextElement( ) {
        skipSpaces();
        char ch = peek();
        switch (ch){
            case '"': {
                return new JSONString(this);
            }
            case '{': {
                return new JSONObject(this);
            }
            case '[': {
                return new JSONArray(this);
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }

    public String nextString() {
        StringBuilder builder = new StringBuilder();
        while (hasNext()){
            builder.append(pop());
        }
        return builder.toString();
    }

    private boolean hasNext() {
        if(peek() == '"') {
            if(previous() == '\\') return true;
            return false;
        }
        return true;

    }

}
