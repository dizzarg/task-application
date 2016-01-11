package org.kadyrov.todo.dao.api;

import org.kadyrov.todo.dao.api.domain.Todo;
import org.kadyrov.todo.dao.api.exception.DAOException;

import java.util.List;

public interface TodoDao {
    List<Todo> findAll() throws DAOException;

    Todo save(Todo todo) throws DAOException;

    void removeTodo(Integer id) throws DAOException;

    Todo findById(Integer id) throws DAOException;
}
