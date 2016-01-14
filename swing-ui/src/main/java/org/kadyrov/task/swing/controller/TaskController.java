package org.kadyrov.task.swing.controller;

import org.kadyrov.task.dao.api.domain.Task;
import org.kadyrov.task.dao.api.exception.DAOException;
import org.kadyrov.task.swing.model.TaskListModel;
import org.kadyrov.task.swing.view.TaskView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  This is simple controller implementation of "Model View Controller" pattern.
 */
public class TaskController {

    Logger logger = Logger.getLogger(TaskController.class.getName());
    {
        logger.setLevel(Level.ALL);
    }

    TaskListModel listModel;
    TaskView taskView;

    public TaskController(TaskListModel listModel, TaskView taskView) {
        this.listModel = listModel;
        this.taskView = taskView;
    }

    public void init(){
        loadAll();
        logger.info("Model created");
    }

    public void loadAll() {
        try {
            listModel.loadAll();
            taskView.updateModel(listModel);
            taskView.showInfo("All task was loaded");
            logger.info(listModel.getSize()+" was loaded");
        } catch (DAOException e) {
            taskView.showError("Cannot load all task");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public void addTask(final Task newtask) {
        try {
            listModel.add(newtask);
            taskView.updateModel(listModel);
            taskView.cleanTaskName();
            taskView.showInfo("Task was created");
            logger.info(newtask+" was added");
        } catch (DAOException e) {
            taskView.showError("Cannot create new task");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void editTask(final Task currentTask) {
        try {
            listModel.edit(currentTask);
            taskView.updateModel(listModel);
            taskView.cleanTaskName();
            taskView.showInfo("Task was updated");
            logger.info(currentTask+" was updated");
        } catch (DAOException e) {
            taskView.showError("Cannot update new task");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public void removeTask(final Task task) {
        if (taskView.confirm("Do you want remove current task?")) {
            logger.info("User does not want to remove task {"+task+"}");
            return;
        }
        try {
            listModel.remove(task);
            taskView.disableRemoveBtn();
            taskView.showInfo("Task was removed");
            logger.info(task+" was removed");
        } catch (DAOException e) {
            taskView.showError("Cannot remove current task");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
