package org.kadyrov.task.dao.jdbc.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum  DatabaseProperties {

    INSCANCE;

    private final String url;
    private final String user;
    private final String password;
    private final String initScript;

    DatabaseProperties() {
        try {
            Thread thread = Thread.currentThread();
            ClassLoader loader = thread.getContextClassLoader();
            InputStream inputStream = loader.getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            this.url = (String) properties.get("db.url");
            this.user = (String) properties.get("db.user");
            this.password = (String) properties.get("db.password");
            this.initScript = (String) properties.get("db.initScript");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getInitScript() {
        return initScript;
    }
}
