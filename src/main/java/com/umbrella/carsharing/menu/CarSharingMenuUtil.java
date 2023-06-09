package com.umbrella.carsharing.menu;

import com.google.inject.Inject;
import com.umbrella.carsharing.dao.car.Car;
import com.umbrella.carsharing.dao.car.CarDAO;
import com.umbrella.carsharing.dao.company.Company;
import com.umbrella.carsharing.dao.company.CompanyDAO;
import com.umbrella.carsharing.dao.customer.CustomerDAO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * The CarSharingMenuUtil class provides utility methods related to the car sharing menu.
 */
@AllArgsConstructor(onConstructor = @__({@Inject}))
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarSharingMenuUtil {
    private CompanyDAO companyDAO;
    private CarDAO carDAO;
    private CustomerDAO customerDAO;

    /**
     * Retrieves and displays the list of available cars for a given company.
     *
     * @param companyID ID of the company.
     * @return true if the car list is empty, false otherwise.
     */
    protected boolean carList(int companyID) {
        try {
            List<Car> carList = carDAO.getAllFromCompanyID(companyID);
            List<Car> availableCars = carList.stream()
                    .filter(car -> !car.isRented())
                    .toList();

            if (availableCars.isEmpty()) {
                System.out.println("The car list is empty!\n");
                return true;
            } else {
                System.out.println("\nCar list:");
                availableCars.forEach((car) -> System.out.printf("%d. %s\n", availableCars.indexOf(car) + 1, car.getName()));
                System.out.println("0. Back\n");
                return false;
            }
        } catch (SQLException e) {
            handleException("An error occurred while accessing the car data.", e);
            return false;
        }
    }

    /**
     * Retrieves and displays the list of companies.
     */
    protected void companyList() {
        try {
            List<Company> companyList = companyDAO.getAll();
            if (companyList.isEmpty()) {
                System.out.println("The company list is empty.\n");
            } else {
                System.out.println("\nCompany list:");
                companyList.forEach((company) -> System.out.printf("%d. %s\n", company.getID(), company.getName()));
                System.out.println("0. Back\n");
            }
        } catch (SQLException e) {
            handleException("An error occurred while accessing the company data.", e);
        }
    }

    /**
     * Handles exceptions by displaying an error message and printing the stack trace.
     *
     * @param message   Error message.
     * @param exception Exception object.
     */
    protected static void handleException(String message, Exception exception) {
        System.out.println(message);
        exception.printStackTrace();
    }

    protected int userInputCheck(BufferedReader reader) {
        int userInput = 0;

        while (true) {
            try {
                userInput = Integer.parseInt(reader.readLine());
                break;
            } catch (IOException e) {
                handleException("An error occurred with the input.", e);
            } catch (NumberFormatException e) {
                System.out.println("Input must be a valid integer.");
            }
        }

        return userInput;
    }
}
