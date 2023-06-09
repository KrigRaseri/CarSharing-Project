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

@AllArgsConstructor(onConstructor = @__({@Inject}))
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ManagerMenu {
    private CompanyDAO companyDAO;
    private CarDAO carDAO;
    private CustomerDAO customerDAO;
    private CarSharingMenuUtil csUtil;

    /**
     * Displays the login screen for managers and handles manager menu options.
     *
     * @param reader BufferedReader object for reading user input.
     */
    public void loginManager(BufferedReader reader) throws SQLException {
        boolean isRun = true;
        while (isRun) {
            System.out.println();
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            int input = csUtil.userInputCheck(reader);

            switch (input) {
                case 1 -> companySelection(reader);
                case 2 -> createCompany(reader);
                case 0 -> isRun = false;
                default -> System.out.println("Invalid command");
            }
        }
    }

    /**
     * Displays the list of companies and handles company selection for further operations.
     *
     * @param reader BufferedReader object for reading user input.
     * @throws SQLException if an error occurs while accessing the database.
     */
    private void companySelection(BufferedReader reader) throws SQLException {
       while (true) {
           List<Company> companyList = companyDAO.getAll();
           if (companyList.isEmpty()) {
               System.out.println("The company list is empty");
           } else {
               System.out.println("\nChoose a company:");
               companyList.forEach((company) -> System.out.printf("%d. %s\n", company.getID(), company.getName()));
               System.out.println("0. Back");

               int selection = csUtil.userInputCheck(reader);
               if (selection == 0) {
                   break;
               }
               carMenu(reader, companyList.get(selection - 1));
           }
           break;
       }
    }

    /**
     * Creates a new company based on user input.
     *
     * @param reader BufferedReader object for reading user input.
     * @throws SQLException if an error occurs while accessing the database.
     */
    private void createCompany(BufferedReader reader) throws SQLException {
        try {
            System.out.println();
            System.out.println("Enter the company name:");
            String companyName = reader.readLine();
            int result = companyDAO.insert(new Company(0, companyName));
            System.out.printf("%d. Records updated.", result);
        } catch (IOException e) {
            System.out.println("An error occurred while reading input from the user.");
            e.printStackTrace();
        }
    }

    /**
     * Displays the car menu for a specific company and handles car-related operations.
     *
     * @param reader  BufferedReader object for reading user input.
     * @param company Company object representing the selected company.
     * @throws SQLException if an error occurs while accessing the database.
     */
    private void carMenu(BufferedReader reader, Company company) throws SQLException {
        try {
            boolean isRun = true;
            while (isRun) {
                System.out.println();
                System.out.println(company.getName() + " company\n" +
                        "1. Car list\n" +
                        "2. Create a car\n" +
                        "0. Back\n");

                String input = reader.readLine();
                switch (input) {
                    case "1" -> csUtil.carList(company.getID());
                    case "2" -> createCar(reader, company);
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading input from the user.");
            e.printStackTrace();
        }
    }

    /**
     * Creates a new car for the specified company based on user input.
     *
     * @param reader  BufferedReader object for reading user input.
     * @param company Company object representing the selected company.
     * @throws SQLException if an error occurs while accessing the database.
     */
    private void createCar(BufferedReader reader, Company company) throws SQLException {
        try {
            System.out.println("Enter the car name:");
            String carName = reader.readLine();
            int result = carDAO.insert(new Car(0, carName, company.getID(), false));
            System.out.printf("%d. Records updated.", result);
        } catch (IOException e) {
            System.out.println("An error occurred while reading input from the user.");
            e.printStackTrace();
        }
    }
}
