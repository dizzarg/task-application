package org.kadyrov.task.dao.jdbc.exception;

import java.sql.SQLException;

/**
 * Exception throws if client cannot create entity instance from {@link java.sql.ResultSet} row.
 */
public class MappingException extends SQLException {

    public MappingException(String reason, Throwable cause) {
        super(reason, cause);
    }

}
