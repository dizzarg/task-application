package org.kadyrov.task.dao.jdbc.mapper;

import org.kadyrov.task.dao.api.domain.Task;
import org.kadyrov.task.dao.jdbc.exception.MappingException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Entity mapper from query result
 */
public class TaskMapper {

    /**
     * Create entity instance from {@link ResultSet}
     *
     * @param resultSet query value, it must not be null
     * @return entity instance
     * @throws MappingException when it throws {@link SQLException}
     */
    public Task mapper(ResultSet resultSet) throws MappingException {
        try {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            String desc = resultSet.getString("DESCRIPTION");
            Timestamp createDt = resultSet.getTimestamp("CREATE_DT");
            Timestamp modifyDt = resultSet.getTimestamp("MODIFY_DT");
            Task task = new Task(id, name);
            task.setDescription(desc);
            task.setCreatedDate(createDt);
            task.setModifyDate(modifyDt);
            return task;
        } catch (SQLException e) {
            throw new MappingException("Cannot mapping task", e);
        }
    }

}
