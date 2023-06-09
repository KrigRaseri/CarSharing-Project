package com.umbrella.carsharing.menu;

import com.google.inject.Inject;
import com.umbrella.carsharing.dao.car.Car;
import com.umbrella.carsharing.dao.car.CarDAO;
import com.umbrella.carsharing.dao.company.CompanyDAO;
import com.umbrella.carsharing.dao.customer.Customer;
import com.umbrella.carsharing.dao.customer.CustomerDAO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the menu for customer-related actions in the Car Sharing system.
 */
@AllArgsConstructor(onConstructor = @__({@Inject}))
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMenu {
    private CompanyDAO companyDAO;
    private CarDAO carDAO;
    private CustomerDAO customerDAO;
    private CarSharingMenuUtil csUtil;

    /**
     * Displays the login menu for customers and handles customer login actions.
     *
     * @param reader the BufferedReader object to read user input
     */
    public void loginCustomer(BufferedReader reader) {
        try {
            while (true) {
                List<Customer> customerList = customerDAO.getAll();
                if (customerList.isEmpty()) {
                    System.out.println("The customer list is empty!\n");
                } else {
                    System.out.println();
                    System.out.println("The customer list:");
                    customerList.forEach((c) -> System.out.printf("%d. %s\n", customerList.indexOf(c) + 1, c.getName()));
                    System.out.println("0. Back");
                    int chooseCustomer = csUtil.userInputCheck(reader);
                    if (chooseCustomer == 0) {
                        break;
                    }
                    System.out.println();
                    customerMenu(reader, customerList.get(chooseCustomer - 1));
                }
                break;
            }
        } catch (SQLException e) {
            CarSharingMenuUtil.handleException("An error occurred while accessing the customer data.", e);
        }
    }

    /**
     * Displays the customer menu and handles customer actions.
     *
     * @param reader   the BufferedReader object to read user input
     * @param customer the Customer object representing the logged-in customer
     */
    private void customerMenu(BufferedReader reader, Customer customer) {
        try {
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("1. Rent a car");
                System.out.println("2. Return a rented car");
                System.out.println("3. My rented car");
                System.out.println("0. Back");

                String input = reader.readLine();
                switch (input) {
                    case "1" -> rentCar(reader, customer, customer.getRentedCarID() == null ? 0
                            : customer.getRentedCarID());
                    case "2" -> returnCar(customer, customer.getRentedCarID() == null ? 0 : customer.getRentedCarID());
                    case "3" -> myCar(customer.getRentedCarID() == null ? 0 : customer.getRentedCarID());
                    case "0" -> isRunning = false;
                    default -> System.out.println("\nInvalid command\n");
                }
            }
        } catch (IOException e) {
            CarSharingMenuUtil.handleException("An error occurred while reading input from the user.", e);
        }
    }

    /**
     * Handles the process of renting a car for the customer.
     *
     * @param reader the BufferedReader object to read user input
     * @param customer the Customer object representing the logged-in customer
     * @param carID the ID of the currently rented car (0 if no car is rented)
     */
    private void rentCar(BufferedReader reader, Customer customer, int carID) {
        try {
            while (true) {
                if (carID != 0) {
                    System.out.println("\nYou've already rented a car!");
                    break;
                }

                csUtil.companyList();
                System.out.println("Choose a company:");
                int chooseCompany = csUtil.userInputCheck(reader);
                if (chooseCompany == 0) {
                    break;
                }

                if (csUtil.carList(chooseCompany)) {
                    break;
                }

                System.out.println("Choose a car:");
                int chooseCar = csUtil.userInputCheck(reader);
                if (chooseCar == 0) {
                    continue;
                }

                customer.setRentedCarID(chooseCar);
                int resultCustomer = customerDAO.update(customer);
                System.out.printf("%d. Records updated in the customer table.", resultCustomer);

                Car car = carDAO.get(customer.getRentedCarID());
                car.setRented(true);
                int resultCar = carDAO.update(car);
                System.out.printf("%d. Records updated in the car table.", resultCar);
                System.out.printf("\nYou rented '%s'\n", carDAO.get(chooseCar).getName());
                break;
            }
        } catch (SQLException e) {
            CarSharingMenuUtil.handleException("Failed to update customer or car data. Please try again later.", e);
        } catch (NumberFormatException e) {
            CarSharingMenuUtil.handleException("Invalid input. Please enter a valid command number.", e);
        }
    }

    /**
     * Handles the process of returning a rented car for the customer.
     *
     * @param customer the Customer object representing the logged-in customer
     * @param carID the ID of the currently rented car (0 if no car is rented)
     */
    private void returnCar(Customer customer, int carID) {
        try {
            if (carID == 0) {
                System.out.println("\nYou didn't rent a car!\n");
            } else {
                customer.setRentedCarID(null);
                int resultCustomer = customerDAO.update(customer);
                System.out.printf("%d. Records updated in the customer table.", resultCustomer);

                Car car = carDAO.get(carID);
                car.setRented(false);
                int resultCar = carDAO.update(car);
                System.out.printf("%d. Records updated in the car table.", resultCar);
                System.out.println("\nYou've returned a rented car!\n");
            }
        } catch (SQLException e) {
            CarSharingMenuUtil.handleException("An error occurred while accessing the car or company data.", e);
        }
    }

    /**
     * Displays the details of the currently rented car for the customer.
     *
     * @param carID the ID of the currently rented car (0 if no car is rented)
     */
    private void myCar(int carID) {
        try {
            if (carID == 0) {
                System.out.println("\nYou didn't rent a car!\n");
            } else {
                System.out.println("\nYour rented car:");
                System.out.println(carDAO.get(carID).getName());
                System.out.println("Company:");
                System.out.println(companyDAO.get(carID).getName() + "\n");
            }
        } catch (SQLException e) {
            CarSharingMenuUtil.handleException("An error occurred while accessing the car or company data.", e);
        }
    }
}
