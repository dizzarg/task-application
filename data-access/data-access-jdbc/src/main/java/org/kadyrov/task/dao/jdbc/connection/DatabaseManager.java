package org.kadyrov.task.dao.jdbc.connection;

import org.h2.jdbcx.JdbcConnectionPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database connection manager.
 * Singleton class.
 */
public enum  DatabaseManager {

    INCANCE;

    Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    static {
        org.h2.Driver.load();
    }

    private final JdbcConnectionPool jdbcConnectionPool;

    DatabaseManager() {
        DatabaseProperties properties = DatabaseProperties.INSCANCE;
        this.jdbcConnectionPool = JdbcConnectionPool.create(properties.getUrl(), properties.getUser(), properties.getPassword());
        InputStream stream = readInitDBSQL(properties.getInitScript());
        initDatabase(stream);
    }

    private InputStream readInitDBSQL(String initScript) {
        return getClass().getClassLoader().getResourceAsStream(initScript);
    }

    private void initDatabase(InputStream stream){
        try(InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(inputStreamReader)) {
            try(Connection conn = jdbcConnectionPool.getConnection()) {
                while (reader.ready()){
                    String sql = reader.readLine();
                    try (Statement stat = conn.createStatement()) {
                        stat.execute(sql);
                        logger.info("Executed SQL : "+sql);
                    } catch (SQLException e) {
                        logger.log(Level.SEVERE, "Could not execute SQL : " + sql, e);
                    }
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Could not init database", e);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not init database", e);
        }
//
//        try(Connection conn = jdbcConnectionPool.getConnection()){
//            conn.setAutoCommit(true);
//            try (Statement stmt = conn.createStatement()){
//                stmt.execute("DROP TABLE task IF EXISTS;");
//                stmt.execute("CREATE TABLE IF NOT EXISTS TASKS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), DESCRIPTION VARCHAR(1024) DEFAULT NULL, CREATE_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP(), MODIFY_DT TIMESTAMP DEFAULT NOT NULL);");
//                stmt.execute("ALTER TABLE TASKS ADD COLUMN IF NOT EXISTS DESCRIPTION VARCHAR(1024) DEFAULT NULL;");
//                stmt.execute("ALTER TABLE TASKS ADD COLUMN IF NOT EXISTS MODIFY_DT TIMESTAMP DEFAULT NULL;");
//                stmt.execute("UPDATE TASKS SET MODIFY_DT=CREATE_DT;");
//                stmt.execute("ALTER TABLE TASKS ALTER COLUMN MODIFY_DT SET NOT NULL;");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public Connection createConnection() throws SQLException {
        return jdbcConnectionPool.getConnection();
    }

}
