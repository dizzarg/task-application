package org.kadyrov.todo.dao.api.exception;

import java.sql.SQLException;

public class MappingException extends SQLException {
    public MappingException(String reason) {
        super(reason);
    }

    public MappingException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
