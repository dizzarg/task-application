package org.kadyrov.todo.swing.controller;

import org.kadyrov.todo.dao.api.domain.Todo;
import org.kadyrov.todo.dao.api.exception.DAOException;
import org.kadyrov.todo.swing.model.TodoListModel;
import org.kadyrov.todo.swing.view.TodoView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TodoController {

    Logger logger = Logger.getLogger(TodoController.class.getName());
    {
        logger.setLevel(Level.ALL);
    }

    TodoListModel listModel;
    TodoView todoView;

    public TodoController(TodoListModel listModel, TodoView todoView) {
        this.listModel = listModel;
        this.todoView = todoView;
    }

    public void init(){
        loadAll();
        logger.info("Model created");
    }

    public void loadAll() {
        try {
            listModel.loadAll();
            todoView.updateModel(listModel);
            todoView.showInfo("All todo was loaded");
            logger.info(listModel.getSize()+" was loaded");
        } catch (DAOException e) {
            todoView.showError("Cannot load all todo");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public void addTodo(final Todo newTodo) {
        try {
            listModel.add(newTodo);
            todoView.updateModel(listModel);
            todoView.cleanTodoName();
            todoView.showInfo("Todo was created");
            logger.info(newTodo+" was added");
        } catch (DAOException e) {
            todoView.showError("Cannot create new todo");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void editTodo(final Todo currentTodo) {
        try {
            listModel.edit(currentTodo);
            todoView.updateModel(listModel);
            todoView.cleanTodoName();
            todoView.showInfo("Todo was updated");
            logger.info(currentTodo+" was updated");
        } catch (DAOException e) {
            todoView.showError("Cannot update new todo");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public void removeTodo(final Todo todo) {
        if (todoView.confirm("Do you want remove current todo?")) {
            logger.info("User does not want to remove todo {"+todo+"}");
            return;
        }
        try {
            listModel.remove(todo);
            todoView.disableRemoveBtn();
            todoView.showInfo("Todo was removed");
            logger.info(todo+" was removed");
        } catch (DAOException e) {
            todoView.showError("Cannot remove current todo");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
