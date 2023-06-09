package com.umbrella.carsharing.database;

import com.umbrella.carsharing.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Handles all the database connections.
 * */
public class HikariConnection {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:./src/main/resources/db/" + Main.db);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    private HikariConnection() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
