package org.kadyrov.todo.dao.jdbc;

import org.kadyrov.todo.dao.api.Dao;
import org.kadyrov.todo.dao.api.domain.Todo;
import org.kadyrov.todo.dao.api.exception.DAOException;
import org.kadyrov.todo.dao.api.exception.ZeroRowsAffectedException;
import org.kadyrov.todo.dao.jdbc.connection.DatabaseManager;
import org.kadyrov.todo.dao.jdbc.mapper.TodoMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TodoDaoDB implements Dao<Todo, Integer> {

    Logger logger = Logger.getLogger(TodoDaoDB.class.getName());

    public static final String FIND_ALL = "SELECT ID, NAME, DESCRIPTION, CREATE_DT, MODIFY_DT FROM TODO ORDER BY ID";
    public static final String CREATE = "INSERT INTO TODO(NAME, DESCRIPTION, MODIFY_DT) VALUES(?, ?, CURRENT_TIMESTAMP())";
    public static final String UPDATE_BY_ID = "UPDATE TODO SET NAME=?, DESCRIPTION=?, MODIFY_DT=CURRENT_TIMESTAMP()  WHERE ID=?";
    public static final String DELETE_BY_ID = "DELETE FROM TODO WHERE ID=?";
    public static final String FIND_BY_ID = "SELECT ID, NAME, DESCRIPTION, CREATE_DT, MODIFY_DT FROM TODO WHERE ID=?";

    TodoMapper mapper = new TodoMapper();
    DatabaseManager manager = new DatabaseManager();

    @Override
    public List<Todo> findAll() throws DAOException {
        List<Todo> results = new ArrayList<>();
        try (Connection conn = manager.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(FIND_ALL)) {
            conn.setAutoCommit(true);
            while (resultSet.next()) {
                Todo todo = mapper.mapper(resultSet);
                results.add(todo);
            }
            logger.info("All todo was loaded from DB, those count is "+results.size());
            return results;
        } catch (SQLException e) {
            throw new DAOException("Cannot load todo", e);
        }
    }

    @Override
    public Todo save(Todo todo) throws DAOException {
        try (Connection conn = manager.createConnection()) {
           if(todo.getId()==null){
               return createTodo(conn, todo);
           } else {
               return editTodo(conn, todo);
           }
        } catch (SQLException e) {
            throw new DAOException("Cannot load todo", e);
        }
    }

    public Todo createTodo(Connection conn, Todo newTodo) throws DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(true);
            stmt.setString(1, newTodo.getName());
            stmt.setString(2, newTodo.getDescription());
            if (stmt.executeUpdate() != 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    int id = keys.getInt(1);
                    Todo td = findById(conn, id);
                    logger.info("Todo was created "+td+" in DB");
                    return td;
                } else {
                    throw new DAOException("Cannot generate primary key");
                }
            } else {
                throw new ZeroRowsAffectedException("Cannot insert Todo");
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot load todo", e);
        }
    }

    public Todo editTodo(Connection conn, Todo todo) throws DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID)) {
            conn.setAutoCommit(true);
            stmt.setString(1, todo.getName());
            stmt.setString(2, todo.getDescription());
            stmt.setInt(3, todo.getId());
            if (stmt.executeUpdate() != 0) {
                Todo td = findById(conn, todo.getId());
                logger.info("Todo was updated "+td+" from DB");
                return td;
            } else {
                throw new ZeroRowsAffectedException("Cannot updated Todo");
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot updated todo", e);
        }
    }

    @Override
    public void remove(Integer id) throws DAOException {
        try (Connection conn = manager.createConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(true);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Todo by id {"+id+"} was removed from DB");
        } catch (SQLException e) {
            throw new DAOException("Cannot remove todo", e);
        }
    }

    @Override
    public Todo findById(Integer id) throws DAOException {
        try (Connection conn = manager.createConnection()) {
            Todo td = findById(conn, id);
            logger.info("Todo by id {"+id+"} was loaded from DB");
            return td;
        } catch (SQLException e) {
            throw new DAOException("Cannot load todo", e);
        }
    }

    public Todo findById(Connection conn, Integer id) throws DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapper.mapper(resultSet);
            } else {
                throw new ZeroRowsAffectedException("Entity by id not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot load todo", e);
        }
    }

}
