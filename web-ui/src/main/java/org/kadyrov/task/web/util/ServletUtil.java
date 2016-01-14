package org.kadyrov.task.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtil {

    public static void fillResponse(HttpServletResponse resp, String response) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.write(response);
        pw.flush();
    }

    public static String getRequestContent(HttpServletRequest req) throws IOException {
        StringBuilder request = new StringBuilder();
        BufferedReader bufferedReader = req.getReader();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            request.append(line);
        }
        return request.toString();
    }

    public static String getPathInfo(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if(pathInfo!=null && pathInfo.trim().length()>1){
            return pathInfo.substring(1,pathInfo.length());
        }
        return "";
    }

}
