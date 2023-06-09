package com.umbrella.carsharing.menu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.umbrella.carsharing.CarSharingGuice;
import com.umbrella.carsharing.dao.customer.Customer;
import com.umbrella.carsharing.dao.customer.CustomerDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * The main class for the Car Sharing Menu.
 */
public class CarSharingMenuMain {
    private final Injector injector;

    /**
     * Constructs a CarSharingMenuMain object.
     */
    public CarSharingMenuMain() {
        this.injector = Guice.createInjector(new CarSharingGuice());
    }

    /**
     * Initializes the Car Sharing Menu.
     */
    public void menuInit() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("1. Log in as a manager");
                System.out.println("2. Log in as a customer");
                System.out.println("3. Create a customer");
                System.out.println("0. Exit");

                String logInChoice = reader.readLine();

                switch (logInChoice) {
                    case "1" -> injector.getInstance(ManagerMenu.class).loginManager(reader);
                    case "2" -> injector.getInstance(CustomerMenu.class).loginCustomer(reader);
                    case "3" -> createCustomer(reader);
                    case "0" -> isRunning = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            CarSharingMenuUtil.handleException("An error occurred while reading input.", e);
        } catch (SQLException e) {
            CarSharingMenuUtil.handleException("An error occurred while accessing the database.", e);
        }
    }

    /**
     * Creates a new customer.
     *
     * @param reader the BufferedReader object to read user input
     */
    private void createCustomer(BufferedReader reader) {
        try {
            System.out.println("\nEnter the customer name:");
            String input = reader.readLine();
            CustomerDAO customerDAO = injector.getInstance(CustomerDAO.class);
            customerDAO.insert(new Customer(0, input));
        } catch (SQLException | IOException e) {
            CarSharingMenuUtil.handleException("An error occurred while creating a customer.", e);
        } finally {
            System.out.println("The customer was added!\n");
        }
    }
}