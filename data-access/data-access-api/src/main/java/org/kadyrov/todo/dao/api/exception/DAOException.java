package org.kadyrov.todo.dao.api.exception;

import java.sql.SQLException;

public class DAOException extends SQLException {

    public DAOException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public DAOException(String reason) {
        super(reason);
    }
}
