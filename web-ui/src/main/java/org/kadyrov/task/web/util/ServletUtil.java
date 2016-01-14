package org.kadyrov.task.web.util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Util class for {@link HttpServlet}
 */
public class ServletUtil {

    /**
     * Fill json content response into response
     *
     * @param resp response instance
     * @param response string of object in JSON format
     * @throws IOException if cannot write string into response
     */
    public static void fillResponse(HttpServletResponse resp, String response) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.write(response);
        pw.flush();
    }

    /**
     * Read data from request
     *
     * @param req HTTP request
     * @return string value of request data
     * @throws IOException if cannot copy data from HTTP request
     */
    public static String readRequestData(HttpServletRequest req) throws IOException {
        StringBuilder request = new StringBuilder();
        BufferedReader bufferedReader = req.getReader();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            request.append(line);
        }
        return request.toString();
    }

    /**
     * Query path information without first slash.
     * Method return path value if client will request
     * http://<host>:<port>/<servlet-context>/<path>.
     * if path is empty it will return empty string.
     *
     * @param req HTTP request instance
     * @return path value
     */
    public static String getPathInfo(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if(pathInfo!=null && pathInfo.trim().length()>1){
            return pathInfo.substring(1,pathInfo.length());
        }
        return "";
    }

}
