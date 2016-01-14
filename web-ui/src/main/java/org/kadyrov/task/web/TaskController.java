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
public class TaskController extends HttpServlet {

    TaskModel taskModel;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskModel = (TaskModel) getServletContext().getAttribute("taskModel");
        if (taskModel == null) {
            throw new UnavailableException("Cannot initialize Task model.");
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = ServletUtil.getPathInfo(req);
        String response = idParam.isEmpty()? taskModel.loadAll(): taskModel.loadOne(idParam);
        ServletUtil.fillResponse(resp, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = ServletUtil.getPathInfo(req);
        String response = taskModel.delete(idParam);
        ServletUtil.fillResponse(resp, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String request = ServletUtil.readRequestData(req);
        String idParam = ServletUtil.getPathInfo(req);
        String response = taskModel.edit( request, idParam);
        ServletUtil.fillResponse(resp, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String request = ServletUtil.readRequestData(req);
        String response = taskModel.create(request);
        ServletUtil.fillResponse(resp, response);
    }

}
