package org.kadyrov.todo.dao.api.exception;

import java.sql.SQLException;

/**
 * Created by kadyrovd on 12/7/2015.
 */
public class ZeroRowsAffectedException extends SQLException {
    public ZeroRowsAffectedException(String reason) {
        super(reason);
    }
}
