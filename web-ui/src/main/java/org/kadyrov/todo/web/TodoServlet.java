package org.kadyrov.todo.web;

import org.kadyrov.todo.dao.api.Dao;
import org.kadyrov.todo.dao.api.domain.Todo;
import org.kadyrov.todo.dao.api.exception.DAOException;
import org.kadyrov.todo.dao.jdbc.TodoDaoDB;
import org.kadyrov.todo.json.Json;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/todo/*")
public class TodoServlet extends HttpServlet {

    static final String jsonTodos = "[{\"id\": 0,\"name\": \"Excepteur et ea sint ex duis fugiat consequat sunt magna.\",\"desc\": \"Ut duis reprehenderit non non est dolore minim incididunt quis veniam eiusmod fugiat id. Commodo elit et ullamco ullamco qui commodo magna labore sint mollit reprehenderit irure do. Enim veniam eiusmod culpa esse sit mollit labore aute irure Lorem ullamco.\\n\\nVeniam nulla in qui et consectetur. Commodo occaecat labore occaecat commodo culpa fugiat excepteur est anim id. Do deserunt consequat veniam amet excepteur eiusmod occaecat fugiat id cupidatat do cupidatat id. Culpa eiusmod officia exercitation tempor commodo.\",\"createdt\": \"Wed Sep 05 2012 07:12:32 GMT+0000 (UTC)\",\"modifydt\": \"Tue Jun 02 2015 07:53:09 GMT+0000 (UTC)\"},{\"id\": 1,\"name\": \"Quis sunt Lorem ad excepteur sint.\",\"desc\": \"Cillum minim aute consequat et qui sit amet. In ipsum excepteur pariatur minim sunt. Cillum laborum mollit excepteur esse Lorem tempor laborum nulla duis aliquip ut. Id amet non anim est excepteur cillum. Quis dolore qui adipisicing esse pariatur velit ullamco deserunt qui magna nisi laborum minim. Et mollit exercitation eiusmod qui occaecat nulla culpa in cillum reprehenderit tempor.\\n\\nDolore quis ad elit officia id. Aliquip duis aute non eiusmod et fugiat veniam id eu quis voluptate. Laborum consectetur nostrud exercitation irure eu veniam ullamco incididunt aliquip dolor Lorem. Commodo minim minim velit qui nisi consectetur irure nulla sint id amet irure aute. Qui ullamco ut esse nostrud commodo reprehenderit adipisicing Lorem.\",\"createdt\": \"Sat Oct 29 2011 18:53:27 GMT+0000 (UTC)\",\"modifydt\": \"Wed Apr 01 2015 17:28:22 GMT+0000 (UTC)\"},{\"id\": 2,\"name\": \"Ullamco irure excepteur ullamco nulla amet consequat anim.\",\"desc\": \"Laboris eu in pariatur ad. Elit nisi ut ut laborum ullamco. Nostrud aliqua nisi in est irure cupidatat.\\n\\nFugiat eiusmod amet nulla elit esse dolor tempor dolor exercitation. Do officia reprehenderit fugiat ea dolore tempor. Culpa minim ullamco tempor esse eu commodo aliqua nostrud qui est Lorem fugiat voluptate. Adipisicing irure dolore ipsum qui pariatur tempor enim. Fugiat ea reprehenderit veniam qui labore anim sit aliquip et enim.\",\"createdt\": \"Tue Sep 18 2012 10:51:09 GMT+0000 (UTC)\",\"modifydt\": \"Thu Apr 16 2015 14:39:25 GMT+0000 (UTC)\"},{\"id\": 3,\"name\": \"Incididunt magna officia duis velit fugiat enim commodo commodo fugiat qui.\",\"desc\": \"Magna deserunt fugiat ipsum incididunt sunt laborum ad consectetur eu elit. Adipisicing culpa magna Lorem duis quis in in laboris Lorem. Aute mollit cupidatat dolor elit ipsum sit. Eu ex adipisicing voluptate eiusmod proident magna incididunt ad ex dolor nulla mollit. Dolore culpa nisi enim eiusmod velit sunt ipsum. Elit nostrud elit aute tempor voluptate esse duis in ea. Cillum deserunt anim culpa et tempor dolor.\\n\\nAd fugiat officia occaecat ipsum. Nulla irure ea in pariatur. Magna ea duis irure voluptate minim in minim ea. Proident irure non qui amet Lorem officia et esse eiusmod eu est.\",\"createdt\": \"Fri Dec 16 2011 05:02:33 GMT+0000 (UTC)\",\"modifydt\": \"Sat Mar 28 2015 13:52:23 GMT+0000 (UTC)\"},{\"id\": 4,\"name\": \"Cupidatat eu voluptate nulla qui consequat est exercitation ut non nisi mollit.\",\"desc\": \"Exercitation commodo id pariatur ipsum adipisicing. Nulla magna nulla et veniam esse in id nulla sint. Minim reprehenderit deserunt occaecat qui. Dolor irure qui pariatur non ex laborum sit ipsum culpa voluptate laboris aliquip elit. Esse excepteur adipisicing minim cillum laborum. Laboris consequat ex aliqua tempor reprehenderit sunt.\\n\\nCulpa consectetur ipsum esse commodo est anim consectetur excepteur incididunt elit. Nostrud id ipsum nisi aliqua anim officia ex. Sit adipisicing consectetur esse fugiat ullamco veniam mollit consectetur consectetur fugiat. Laboris mollit ipsum incididunt ea. Est nostrud adipisicing ad nulla non. Sit nostrud aliquip reprehenderit excepteur occaecat ea sit do esse id ex ex mollit.\",\"createdt\": \"Wed Sep 12 2012 06:53:30 GMT+0000 (UTC)\",\"modifydt\": \"Sat May 09 2015 11:15:49 GMT+0000 (UTC)\"}]";

    private final Dao todoDao = new TodoDaoDB();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");

        PrintWriter pw = resp.getWriter();

        String idParam = req.getParameter("id");
        String pathInfo = req.getPathInfo();
        if(pathInfo!=null && pathInfo.length()>1){
            idParam = pathInfo.substring(1,pathInfo.length());
        }
        StringBuilder response = new StringBuilder();
        if(idParam == null || idParam.isEmpty()){
            response.append("[");
            try {
                List<Todo> todoList = todoDao.findAll();
                for (Todo todo : todoList) {
                    response.append("{");
                    response.append("\"id\":\"").append(todo.getId()).append("\",");
                    response.append("\"name\":\"").append(escape(todo.getName())).append("\",");
                    response.append("\"desc\":\"").append(escape(todo.getDescription())).append("\",");
                    response.append("\"createdt\":\"").append(todo.getCreatedDate()).append("\",");
                    response.append("\"modifydt\":\"").append(todo.getModifyDate()).append("\"");
                    response.append("},");
                }
                if(todoList.size()>0){
                    response.delete(response.length()-1, response.length());
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }
            response.append("]");
        } else {
            try {
                Integer id = Integer.parseInt(idParam);
                Todo todo = todoDao.findById(id);

//                response.append("{");
//                response.append("\"id\":\"").append(todo.getId()).append("\",");
//                response.append("\"name\":\"").append(escape(todo.getName())).append("\",");
//                response.append("\"desc\":\"").append(escape(todo.getDescription())).append("\",");
//                response.append("\"createdt\":\"").append(todo.getCreatedDate()).append("\",");
//                response.append("\"modifydt\":\"").append(todo.getModifyDate()).append("\"");
//                response.append("}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Response: " + response.toString());

        pw.write(response.toString());
        pw.flush();

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");

        PrintWriter pw = resp.getWriter();

        String idParam = req.getParameter("id");
        String pathInfo = req.getPathInfo();
        if(pathInfo!=null && pathInfo.length()>1){
            idParam = pathInfo.substring(1,pathInfo.length());
        }
        StringBuilder response = new StringBuilder();
        if(idParam != null && !idParam.isEmpty()){
            try {
                Integer id = Integer.parseInt(idParam);
                todoDao.remove(id);
                response.append("{");
                response.append("\"result\":\"success\"");
                response.append("}");
            } catch (Exception e) {
                response.append("{");
                response.append("\"result\":\"failure\"");
                response.append("}");
                e.printStackTrace();
            }
        }
        pw.write(response.toString());
        pw.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        StringBuilder response = new StringBuilder();
        StringBuilder request = new StringBuilder();
        BufferedReader bufferedReader = req.getReader();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            request.append(line);
        }
        System.out.println("Content-type: " + req.getContentType());
        System.out.println("Content-length: " + req.getContentLength());
        System.out.println("Data: "+request.toString());
        String idParam = req.getParameter("id");
        String pathInfo = req.getPathInfo();
        if(pathInfo!=null && pathInfo.length()>1){
            idParam = pathInfo.substring(1,pathInfo.length());
        }
        try {
            Integer id = Integer.parseInt(idParam);
            Todo todo = Json.objectFromString(request.toString(), Todo.class);
            todo.setId(id);
            todo = todoDao.save(todo);
            response.append("{");
            response.append("\"result\":\"success\",");
            response.append("\"savedTodo\":\"").append(todo.toString()).append("\"");
            response.append("}");
        } catch (Exception e) {
            response.append("{");
            response.append("\"result\":\"failure\"");
            response.append("}");
            e.printStackTrace();
        }
        pw.write(response.toString());
        pw.flush();
    }

    private String escape(String value) {
        if(value==null) return "";
        StringBuilder sb = new StringBuilder();
        char[] chars = value.toCharArray();
        for (char aChar : chars) {
            if(aChar == '"' || aChar == '\''){
                sb.append("\\");
            }
            sb.append(aChar);
        }
        return sb.toString();
    }
}
