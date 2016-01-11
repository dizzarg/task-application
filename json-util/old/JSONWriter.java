package org.kadyrov.todo.json;

public class JSONWriter {

    final private JSONElement element;

    public JSONWriter(JSONElement element) {
        this.element = element;
    }

    public String toJSON(){
        return element.toJSON();
    }
}
