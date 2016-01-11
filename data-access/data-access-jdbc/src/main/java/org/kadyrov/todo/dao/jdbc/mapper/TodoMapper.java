package org.kadyrov.todo.dao.jdbc.mapper;

import org.kadyrov.todo.dao.api.domain.Todo;
import org.kadyrov.todo.dao.api.exception.MappingException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TodoMapper {

    public Todo mapper(ResultSet resultSet) throws MappingException {
        try {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            String desc = resultSet.getString("DESCRIPTION");
            Timestamp createDt = resultSet.getTimestamp("CREATE_DT");
            Timestamp modifyDt = resultSet.getTimestamp("MODIFY_DT");
            Todo todo = new Todo(id, name);
            todo.setDescription(desc);
            todo.setCreatedDate(createDt);
            todo.setModifyDate(modifyDt);
            return todo;
        } catch (SQLException e) {
            throw new MappingException("Cannot mapping Todo", e);
        }
    }

}
