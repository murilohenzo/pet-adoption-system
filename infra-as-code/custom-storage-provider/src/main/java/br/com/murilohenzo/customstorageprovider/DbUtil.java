package br.com.murilohenzo.customstorageprovider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.keycloak.component.ComponentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.murilohenzo.customstorageprovider.CustomUserStorageProviderConstants.*;

public class DbUtil {

    private static final Logger log = LoggerFactory.getLogger(DbUtil.class);

    public static Connection getConnection(ComponentModel config) throws SQLException {
        String driverClass = config.get(CONFIG_KEY_JDBC_DRIVER);
        try {
            Class.forName(driverClass);
        }
        catch(ClassNotFoundException nfe) {
            throw new RuntimeException("Invalid JDBC driver: " + driverClass + ". Please check if your driver if properly installed");
        }

        return DriverManager.getConnection(config.get(CONFIG_KEY_JDBC_URL),
                config.get(CONFIG_KEY_DB_USERNAME),
                config.get(CONFIG_KEY_DB_PASSWORD));
    }
}