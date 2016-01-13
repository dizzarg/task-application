package org.kadyrov.todo.dao.api.exception;

import java.sql.SQLException;

/**
 * Exception throws when DAO implementation incorrect work.
 */
public class DAOException extends SQLException {

    public DAOException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public DAOException(String reason) {
        super(reason);
    }
}
