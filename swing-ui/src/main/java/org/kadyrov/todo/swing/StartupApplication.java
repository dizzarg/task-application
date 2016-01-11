package org.kadyrov.todo.swing;

import org.kadyrov.todo.swing.controller.TodoController;
import org.kadyrov.todo.swing.model.TodoListModel;
import org.kadyrov.todo.swing.view.TodoView;

public class StartupApplication {

    public static void main(String[] args) {
        TodoListModel listModel = new TodoListModel();
        TodoView view = new TodoView();
        TodoController controller = new TodoController(listModel, view);
        controller.init();
        view.setController(controller);
        view.init();
    }
}
