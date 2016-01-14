package org.kadyrov.task.web;

import org.kadyrov.task.dao.jdbc.TaskDaoDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TaskContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TaskDaoDB taskDao = new TaskDaoDB( );
        TaskController taskController = new TaskController(taskDao);
        sce.getServletContext().setAttribute("taskController", taskController);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
