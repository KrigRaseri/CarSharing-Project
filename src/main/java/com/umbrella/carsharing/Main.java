package com.umbrella.carsharing;

import com.beust.jcommander.JCommander;
import com.umbrella.carsharing.dao.car.CarDAO;
import com.umbrella.carsharing.dao.car.CarDAOImpl;
import com.umbrella.carsharing.dao.company.CompanyDAO;
import com.umbrella.carsharing.dao.company.CompanyDAOImpl;
import com.umbrella.carsharing.dao.customer.CustomerDAO;
import com.umbrella.carsharing.dao.customer.CustomerDAOImpl;
import com.umbrella.carsharing.menu.CarSharingMenu;
import com.umbrella.carsharing.menu.CarSharingMenuImpl;

import java.sql.SQLException;

public class Main {
    public static String db;

    public static void main(String[] args) {
        try {
            // Create instances of the DAO implementations
            CompanyDAO companyDAO = new CompanyDAOImpl();
            CarDAO carDAO = new CarDAOImpl();
            CustomerDAO customerDAO = new CustomerDAOImpl();

            // Create an instance of the CarSharingMenu implementation
            CarSharingMenu carSharingMenu = new CarSharingMenuImpl();

            // Inject the DAO dependencies into the menu
            carSharingMenu.setCompanyDAO(companyDAO);
            carSharingMenu.setCarDAO(carDAO);
            carSharingMenu.setCustomerDAO(customerDAO);

            JCommanderImpl jcc = new JCommanderImpl();
            JCommander jc = JCommander.newBuilder().addObject(jcc).build();
            jc.parse(args);
            db = jcc.dbArgs == null ? "carsharing" : jcc.dbArgs;

            Database.createDBTable();
            Database.createCarTable();
            Database.createCustomerTable();
            carSharingMenu.menuInit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}