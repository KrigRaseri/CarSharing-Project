package com.umbrella.carsharing.database;

import com.umbrella.carsharing.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public interface CreateTables {

    static void createDBTable() {
        try (Connection con = HikariConnection.getConnection();
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

    static void createCarTable() {
        try (Connection con = HikariConnection.getConnection();
             Statement statement = con.createStatement()) {

            statement.executeUpdate(" CREATE TABLE IF NOT EXISTS CAR " +
                    " (ID INTEGER not NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                    " company_ID INTEGER NOT NULL, " +
                    " isRented BOOLEAN DEFAULT FALSE, " +
                    " CONSTRAINT fk_company FOREIGN KEY (company_ID) " +
                    " REFERENCES COMPANY(ID) )");
            //statement.executeUpdate("DROP TABLE IF EXISTS COMPANY");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void createCustomerTable() {
        try (Connection con = HikariConnection.getConnection();
             Statement statement = con.createStatement()) {

            statement.executeUpdate(" CREATE TABLE IF NOT EXISTS CUSTOMER " +
                    " (ID INTEGER not NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " name VARCHAR(255) UNIQUE NOT NULL, " +
                    " rented_car_id INTEGER, " +
                    " CONSTRAINT fk_customer FOREIGN KEY (rented_car_id) " +
                    " REFERENCES CAR(ID) )");
            //statement.executeUpdate("DROP TABLE IF EXISTS COMPANY");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}