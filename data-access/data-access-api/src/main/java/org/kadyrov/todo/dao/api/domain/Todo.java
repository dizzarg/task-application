package org.kadyrov.todo.dao.api.domain;

import java.util.Date;

public class Todo {

    private Integer id;
    private String name;
    private String description;
    private Date createdDate;
    private Date modifyDate;

    /**
     * need to Json
     */
    private Todo(){}

    public Todo(String name) {
        this.name = name;
    }

    public Todo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return id + "|" + name + "|" +description + "|" + createdDate+"|"+modifyDate;
    }
}
