package org.kadyrov.task.web;

import org.kadyrov.task.web.util.ServletUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/task/*")
public class TaskServlet extends HttpServlet {

    TaskController taskController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskController = (TaskController) getServletContext().getAttribute("taskController");
        if (taskController == null) {
            throw new UnavailableException("Cannot initialize Task controller.");
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = ServletUtil.getPathInfo(req);
        String response = idParam.isEmpty()? taskController.loadAll(): taskController.loadOne(idParam);
        ServletUtil.fillResponse(resp, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = ServletUtil.getPathInfo(req);
        String response = taskController.delete(idParam);
        ServletUtil.fillResponse(resp, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String request = ServletUtil.getRequestContent(req);
        String idParam = ServletUtil.getPathInfo(req);
        String response = taskController.edit( request, idParam);
        ServletUtil.fillResponse(resp, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String request = ServletUtil.getRequestContent(req);
        String response = taskController.create(request);
        ServletUtil.fillResponse(resp, response);
    }

}
