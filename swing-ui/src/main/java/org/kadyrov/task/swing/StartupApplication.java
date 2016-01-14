package org.kadyrov.task.swing;

import org.kadyrov.task.swing.controller.TaskController;
import org.kadyrov.task.swing.model.TaskListModel;
import org.kadyrov.task.swing.view.TaskView;

public class StartupApplication {

    public static void main(String[] args) {
        TaskListModel listModel = new TaskListModel();
        TaskView view = new TaskView();
        TaskController controller = new TaskController(listModel, view);
        controller.init();
        view.setController(controller);
        view.init();
    }
}
