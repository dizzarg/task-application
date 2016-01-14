package org.kadyrov.task.dao.jdbc;

import org.kadyrov.task.dao.api.Dao;
import org.kadyrov.task.dao.api.domain.Task;
import org.kadyrov.task.dao.api.exception.DAOException;
import org.kadyrov.task.dao.api.exception.ZeroRowsAffectedException;
import org.kadyrov.task.dao.jdbc.connection.DatabaseManager;
import org.kadyrov.task.dao.jdbc.mapper.TaskMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TaskDaoDB implements Dao<Task, Integer> {

    Logger logger = Logger.getLogger(TaskDaoDB.class.getName());

    public static final String FIND_ALL = "SELECT ID, NAME, DESCRIPTION, CREATE_DT, MODIFY_DT FROM TASKS ORDER BY ID";
    public static final String CREATE = "INSERT INTO TASKS(NAME, DESCRIPTION, MODIFY_DT) VALUES(?, ?, CURRENT_TIMESTAMP())";
    public static final String UPDATE_BY_ID = "UPDATE TASKS SET NAME=?, DESCRIPTION=?, MODIFY_DT=CURRENT_TIMESTAMP() WHERE ID=?";
    public static final String DELETE_BY_ID = "DELETE FROM TASKS WHERE ID=?";
    public static final String FIND_BY_ID = "SELECT ID, NAME, DESCRIPTION, CREATE_DT, MODIFY_DT FROM TASKS WHERE ID=?";

    TaskMapper mapper = new TaskMapper();
    DatabaseManager manager = DatabaseManager.INCANCE;

    @Override
    public List<Task> findAll(int maxResult, int count) throws DAOException {
        List<Task> results = new ArrayList<>();
        try (Connection conn = manager.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(FIND_ALL + " LIMIT "+maxResult+","+count)) {
            conn.setAutoCommit(false);
            while (resultSet.next()) {
                Task task = mapper.mapper(resultSet);
                results.add(task);
            }
            logger.info("All task was loaded from DB, those count is "+results.size());
            return results;
        } catch (SQLException e) {
            throw new DAOException("Cannot load task", e);
        }
    }

    @Override
    public List<Task> findAll() throws DAOException {
        List<Task> results = new ArrayList<>();
        try (Connection conn = manager.createConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(FIND_ALL)) {
            conn.setAutoCommit(false);
            while (resultSet.next()) {
                Task task = mapper.mapper(resultSet);
                results.add(task);
            }
            logger.info("All task was loaded from DB, those count is "+results.size());
            return results;
        } catch (SQLException e) {
            throw new DAOException("Cannot load task", e);
        }
    }

    @Override
    public Task save(Task task) throws DAOException {
        try (Connection conn = manager.createConnection()) {
           if(task.getId()==null){
               return createTask(conn, task);
           } else {
               return editTask(conn, task);
           }
        } catch (SQLException e) {
            throw new DAOException("Cannot load task", e);
        }
    }

    public Task createTask(Connection conn, Task newTask) throws DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(true);
            stmt.setString(1, newTask.getName());
            stmt.setString(2, newTask.getDescription());
            if (stmt.executeUpdate() != 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    int id = keys.getInt(1);
                    Task td = findById(conn, id);
                    logger.info("task was created "+td+" in DB");
                    return td;
                } else {
                    throw new DAOException("Cannot generate primary key");
                }
            } else {
                throw new ZeroRowsAffectedException("Cannot insert task");
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot load task", e);
        }
    }

    public Task editTask(Connection conn, Task task) throws DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID)) {
            conn.setAutoCommit(true);
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getId());
            if (stmt.executeUpdate() != 0) {
                Task td = findById(conn, task.getId());
                logger.info("task was updated "+td+" from DB");
                return td;
            } else {
                throw new ZeroRowsAffectedException("Cannot updated task");
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot updated task", e);
        }
    }

    @Override
    public void remove(Integer id) throws DAOException {
        try (Connection conn = manager.createConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(true);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("task by id {"+id+"} was removed from DB");
        } catch (SQLException e) {
            throw new DAOException("Cannot remove task", e);
        }
    }

    @Override
    public Task findById(Integer id) throws DAOException {
        try (Connection conn = manager.createConnection()) {
            Task td = findById(conn, id);
            logger.info("task by id {"+id+"} was loaded from DB");
            return td;
        } catch (SQLException e) {
            throw new DAOException("Cannot load task", e);
        }
    }

    public Task findById(Connection conn, Integer id) throws DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapper.mapper(resultSet);
            } else {
                throw new ZeroRowsAffectedException("Entity by id not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot load task", e);
        }
    }

}
