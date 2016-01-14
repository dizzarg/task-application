package org.kadyrov.task.dao.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database connection manager
 */
public class DatabaseManager {

    private final String url = "jdbc:h2:file:./data/sample";
    private final String user = "sa";
    private final String password = "";

    static {
        try {
            org.h2.Driver.load();
            DatabaseManager manager = new DatabaseManager();
            try(Connection conn = manager.createConnection()){
                conn.setAutoCommit(true);
                try (Statement stmt = conn.createStatement()){
//                    stmt.execute("DROP TABLE task IF EXISTS;");
                    stmt.execute("CREATE TABLE IF NOT EXISTS TASKS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), DESCRIPTION VARCHAR(1024) DEFAULT NULL, CREATE_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP(), MODIFY_DT TIMESTAMP DEFAULT NOT NULL);");
//                    stmt.execute("ALTER TABLE TASKS ADD COLUMN IF NOT EXISTS DESCRIPTION VARCHAR(1024) DEFAULT NULL;");
//                    stmt.execute("ALTER TABLE TASKS ADD COLUMN IF NOT EXISTS MODIFY_DT TIMESTAMP DEFAULT NULL;");
//                    stmt.execute("UPDATE TASKS SET MODIFY_DT=CREATE_DT;");
//                    stmt.execute("ALTER TABLE TASKS ALTER COLUMN MODIFY_DT SET NOT NULL;");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
