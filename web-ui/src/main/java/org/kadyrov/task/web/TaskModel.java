package org.kadyrov.task.web;

import com.google.gson.Gson;
import org.kadyrov.task.dao.api.domain.Task;
import org.kadyrov.task.dao.api.exception.DAOException;
import org.kadyrov.task.dao.jdbc.TaskDaoDB;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is simple model implementation for web application
 */
public class TaskModel {

    private static final Logger logger = Logger.getLogger(TaskModel.class.getName());

    private static final String SUCCESS = "{\"result\":\"success\"}";
    private static final String FAILURE = "{\"result\":\"failure\"}";

    private final TaskDaoDB taskDao;

    public TaskModel(TaskDaoDB taskDaoDB) {
        this.taskDao = taskDaoDB;
    }

    public String delete(String idParam) {
        StringBuilder response = new StringBuilder();
        try {
            Integer id = Integer.parseInt(idParam);
            taskDao.remove(id);
            response.append(SUCCESS);
        } catch (Exception e) {
            response.append(FAILURE);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return response.toString();
    }

    public String edit(String request, String idParam) {
        Gson gson = new Gson();
        StringBuilder response = new StringBuilder();
        try {
            Integer id = Integer.parseInt(idParam);
            Task task = gson.fromJson(request, Task.class);
            task.setId(id);
            task = taskDao.save(task);
            response.append(SUCCESS);
            logger.info("Task was updated");
        } catch (Exception e) {
            response.append(FAILURE);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return response.toString();
    }

    public String create(String request) {
        StringBuilder response = new StringBuilder();
        Gson gson = new Gson();
        try {
            Task task = gson.fromJson(request, Task.class);
            task = taskDao.save(task);
            response.append(SUCCESS);
            logger.info("Task was created");
        } catch (Exception e) {
            response.append(FAILURE);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return response.toString();
    }

    public String loadAll( ) {
        StringBuilder response = new StringBuilder();
        Gson gson = new Gson();
        try {
            List<Task> taskList = taskDao.findAll();
            response.append(gson.toJson(taskList));
            logger.info("All tasks was loaded");
        } catch (DAOException e) {
            response.append(FAILURE);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return response.toString();
    }

    public String loadOne(String idParam) {
        StringBuilder response = new StringBuilder();
        Gson gson = new Gson();
        try {
            Integer id = Integer.parseInt(idParam);
            Task task = taskDao.findById(id);
            response.append(gson.toJson(task));
            logger.info("Task was loaded");
        } catch (Exception e) {
            response.append(FAILURE);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return response.toString();
    }
}
