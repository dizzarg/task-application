package org.kadyrov.task.web;

import org.kadyrov.task.dao.jdbc.TaskDaoDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * Listener for initialize model. Model is defined in pattern "Model View Controller".
 */
@WebListener
public class RuntimeContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(RuntimeContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Start initialize servlet context");
        TaskDaoDB taskDao = new TaskDaoDB( );
        TaskModel taskModel = new TaskModel(taskDao);
        sce.getServletContext().setAttribute("taskModel", taskModel);
        logger.info("Servlet context is initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Servlet context is destroyed");
    }
}
