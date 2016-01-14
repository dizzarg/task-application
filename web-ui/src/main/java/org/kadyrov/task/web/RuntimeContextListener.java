package org.kadyrov.task.web;

import org.kadyrov.task.dao.jdbc.TaskDaoDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RuntimeContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TaskDaoDB taskDao = new TaskDaoDB( );
        TaskModel taskModel = new TaskModel(taskDao);
        sce.getServletContext().setAttribute("taskModel", taskModel);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
