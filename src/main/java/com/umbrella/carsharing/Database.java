package com.umbrella.carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Database() {}

    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        return DriverManager.getConnection("jdbc:h2:./src/main/resources/db/" + Main.db);
    }

    public static void createDBTable() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/" + Main.db);
             Statement statement = con.createStatement()) {

            statement.executeUpdate(" CREATE TABLE IF NOT EXISTS COMPANY " +
                    " (ID INTEGER not NULL AUTO_INCREMENT, " +
                    " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                    " PRIMARY KEY ( ID ))");
            //statement.executeUpdate("DROP TABLE IF EXISTS COMPANY");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createCarTable() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/" + Main.db);
             Statement statement = con.createStatement()) {

            statement.executeUpdate(" CREATE TABLE IF NOT EXISTS CAR " +
                    " (ID INTEGER not NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                    " company_ID INTEGER NOT NULL, " +
                    " CONSTRAINT fk_company FOREIGN KEY (company_ID) " +
                    " REFERENCES COMPANY(ID) )");
            //statement.executeUpdate("DROP TABLE IF EXISTS COMPANY");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createCustomerTable() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/" + Main.db);
             Statement statement = con.createStatement()) {

            statement.executeUpdate(" CREATE TABLE IF NOT EXISTS CUSTOMER " +
                    " (ID INTEGER not NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                    " rented_car_id INTEGER, " +
                    " CONSTRAINT fk_customer FOREIGN KEY (rented_car_id) " +
                    " REFERENCES CAR(ID) )");
            //statement.executeUpdate("DROP TABLE IF EXISTS COMPANY");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}