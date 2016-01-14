package org.kadyrov.task.json;

public abstract class JSONElement {

    protected JSONReader reader;

//    public JSONElement(JSONReader reader) {
//        this.reader = reader;
//        fromJSON();
//    }

    abstract void fromJSON( );

    abstract String toJSON();

    protected abstract char getBeginChar( );

    protected abstract char getEndChar( );

    public void begin() {
        if(reader.pop()!= getBeginChar( )){
            throw new IllegalStateException();
        }
    }

    public void end() {
        if(reader.pop() != getEndChar( )){
            throw new IllegalStateException();
        }
    }

}
