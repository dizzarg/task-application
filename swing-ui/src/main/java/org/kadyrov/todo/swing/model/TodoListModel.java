package org.kadyrov.todo.swing.model;

import org.kadyrov.todo.dao.api.TodoDao;
import org.kadyrov.todo.dao.api.domain.Todo;
import org.kadyrov.todo.dao.api.exception.DAOException;
import org.kadyrov.todo.dao.jdbc.TodoDaoDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TodoListModel extends AbstractListModel<Todo> {

    TodoDao todoDao = new TodoDaoDB();
    List<Todo> todos = new ArrayList<>();

    @Override
    public int getSize() {
        return todos.size();
    }

    @Override
    public Todo getElementAt(int index) {
        return todos.get(index);
    }

    public void add(Todo todo) throws DAOException {
        todo = todoDao.save(todo);
        todos.add(todo);
        int index = todos.indexOf(todo);
        fireIntervalAdded(this, index, index);
    }

    public void edit(Todo todo) throws DAOException {
        int index = todos.indexOf(todo);
        todo = todoDao.save(todo);
        todos.remove(index);
        todos.add(index, todo);
        fireContentsChanged(this, 0, todos.size());
    }

    public void loadAll() throws DAOException {
        todos = todoDao.findAll();
        fireContentsChanged(this, 0, todos.size());
    }

    public void remove(Todo todo) throws DAOException {
        int index = todos.indexOf(todo);
        todoDao.removeTodo(todo.getId());
        todos.remove(todo);
        fireIntervalRemoved(this, index, index);
    }
}
