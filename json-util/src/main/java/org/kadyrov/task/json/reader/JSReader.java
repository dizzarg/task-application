package org.kadyrov.task.json.reader;

import org.kadyrov.task.json.items.*;

import java.math.BigDecimal;

public class JSReader {

    final private String json;
    private int position;

    public JSReader(String json) {
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

   /* void type(char ch){
        Character.isDigit(ch);

    }*/

    /*
    public JSElement next(){
        skipSpaces();
        char ch = peek();
        JSElementFactory factory = new JSNullFactory();
        if(Character.isDigit(ch)) {
            factory = new JSNumberFactory(this);
        } else {
            switch (ch){
//                case 't':
//                case 'T':
//                case 'f':
//                case 'F': {
//                    factory = new JSBooleanFactory(this);
//                    break;
//                }
                case '"': {
                    factory = new JSStringFactory(this);
                    break;
                }
                case '{': {
                    factory = new JSObjectFactory(this);
                    break;
                }
                case '[': {
                    factory = new JSArrayFactory(this);
                }
//            default: {
//                throw new IllegalStateException();
//            }
            }
        }
        return factory.make();
    }
*/
    public JSElement nextElement( ) {
        skipSpaces();
        char ch = peek();
        JSElementFactory factory = new JSNullFactory();
        switch (ch){
            case '"': {
                factory = new JSStringFactory(this);
                break;
            }
            case '{': {
                factory = new JSObjectFactory(this);
                break;
            }
            case '[': {
                factory = new JSArrayFactory(this);
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        return factory.make();
    }
    /*
    public JSElement _nextElement( ) {
        skipSpaces();
        char ch = peek();
        JSElement element = null;
        switch (ch){
            case '"': {
                element = new JSString();
                element.fromJSON(this);
                return element;
            }
            case '{': {
                element = new JSObject();
                element.fromJSON(this);
                return element;
            }
            case '[': {
                element = new JSArray();
                element.fromJSON(this);
                return element;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }*/

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

    public void verify(char c) {
        if(pop() != c){
            throw new IllegalStateException();
        }
    }

    public BigDecimal nextNumber() {
        StringBuilder builder = new StringBuilder();
        while (Character.isDigit(peek()) || peek()=='.'){
            builder.append(pop());
        }
        return new BigDecimal(builder.toString());
    }
}
