package com.umbrella.carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Database() {}

    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        return DriverManager.getConnection("jdbc:h2:./src/carsharing/db/" + Main.db);
    }

    public static void createDBTable() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/" + Main.db);
             Statement statement = con.createStatement()) {

            statement.executeUpdate(" CREATE TABLE IF NOT EXISTS COMPANY " +
                    " (ID INTEGER not NULL AUTO_INCREMENT, " +
                    " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                    " PRIMARY KEY ( ID ))");
            //statement.executeUpdate("DROP TABLE IF EXISTS COMPANY");
            //System.out.println("Created table in given database...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}