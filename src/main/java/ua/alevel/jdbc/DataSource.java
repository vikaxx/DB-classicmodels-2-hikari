package ua.alevel.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    public static final HikariConfig HIKARI_CONFIG;
    public static final HikariDataSource HIKARI_DATA_SOURCE;

    protected static final String DB_URL = "jdbc:mysql://SG-alevel-3162-master.servers.mongodirector.com:3306/classicmodels";
    protected static final String LOGIN = "alevel";
    protected static final String PASSWORD = "Qwerty123!";

    static {
        HIKARI_CONFIG = new HikariConfig();

        HIKARI_CONFIG.setJdbcUrl(DB_URL);
        HIKARI_CONFIG.setUsername(LOGIN);
        HIKARI_CONFIG.setPassword(PASSWORD);

        Properties properties = new Properties();
        try (InputStream propertiesInputStream = DataSource.class.getClassLoader().getResourceAsStream("hikari.properties")) {
            properties.load(propertiesInputStream);
            HIKARI_CONFIG.setDataSourceProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        HIKARI_DATA_SOURCE = new HikariDataSource(HIKARI_CONFIG);
    }

    public static Connection getConnection() throws SQLException {
        return HIKARI_DATA_SOURCE.getConnection();
    }


}

