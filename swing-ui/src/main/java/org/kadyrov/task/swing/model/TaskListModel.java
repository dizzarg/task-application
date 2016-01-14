package org.kadyrov.task.swing.model;

import org.kadyrov.task.dao.api.Dao;
import org.kadyrov.task.dao.api.domain.Task;
import org.kadyrov.task.dao.api.exception.DAOException;
import org.kadyrov.task.dao.jdbc.TaskDaoDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is simple model implementation of "Model View Controller" pattern.
 */
public class TaskListModel extends AbstractListModel<Task> {

    Dao<Task, Integer> taskDao = new TaskDaoDB();
    List<Task> tasks = new ArrayList<>();

    @Override
    public int getSize() {
        return tasks.size();
    }

    @Override
    public Task getElementAt(int index) {
        return tasks.get(index);
    }

    /**
     * Add new Task into database and update view presentation
     *
     * @param task instance
     * @throws DAOException
     */
    public void add(Task task) throws DAOException {
        task = taskDao.save(task);
        tasks.add(task);
        int index = tasks.indexOf(task);
        fireIntervalAdded(this, index, index);
    }

    /**
     * Update fields for selected Task in database and update view presentation
     *
     * @param task selected Task
     * @throws DAOException
     */
    public void edit(Task task) throws DAOException {
        int index = tasks.indexOf(task);
        task = taskDao.save(task);
        tasks.remove(index);
        tasks.add(index, task);
        fireContentsChanged(this, 0, tasks.size());
    }

    /**
     * Load all Tasks
     *
     * @throws DAOException
     */
    public void loadAll() throws DAOException {
        tasks = taskDao.findAll();
        fireContentsChanged(this, 0, tasks.size());
    }

    /**
     * Remove a selected task from database and update view presentation
     *
     * @param task selected Task
     * @throws DAOException
     */
    public void remove(Task task) throws DAOException {
        int index = tasks.indexOf(task);
        taskDao.remove(task.getId());
        tasks.remove(task);
        fireIntervalRemoved(this, index, index);
    }
}
