package org.kadyrov.task.dao.api.exception;

import java.sql.SQLException;

/**
 * Exception throws if database query is not returned entities.
 */
public class ZeroRowsAffectedException extends SQLException {
    public ZeroRowsAffectedException(String reason) {
        super(reason);
    }
}
