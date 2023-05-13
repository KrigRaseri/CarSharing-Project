package com.umbrella.carsharing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface CarSharingMenu {
    static void menuInit() throws SQLException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean isRun = true;
            while(isRun) {
                System.out.println("1. Log in as a manager");
                System.out.println("0. Exit");

                String input = reader.readLine();
                switch (input) {
                    case "1" -> logIn(reader);
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void logIn(BufferedReader reader) throws SQLException {
        try {
            boolean isRun = true;
            while(isRun) {
                System.out.println("1. Company list");
                System.out.println("2. Create a company");
                System.out.println("0. Back");

                String input = null;

                    input = reader.readLine();

                switch (input) {
                    case "1" -> companySelection(reader);
                    case "2" -> createCompany(reader);
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }


    private static void companySelection(BufferedReader reader) throws SQLException {
        try {
            CompanyDAOImpl cdi = new CompanyDAOImpl();
            List<Company> li = cdi.getAll();
            if (li.isEmpty()) {
                System.out.println("The company list is empty\n");

            } else {
                System.out.println("\nChoose a company:");
                li.forEach((company) -> System.out.printf("%d. %s\n", company.getID(), company.getName()));
                System.out.println();

                carMenu(reader, li.get(Integer.parseInt(reader.readLine())-1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createCompany(BufferedReader reader) throws SQLException {
        try {
            System.out.println("Enter the company name:");
            String input = reader.readLine();
            CompanyDAOImpl cdi = new CompanyDAOImpl();
            int result = cdi.insert(new Company(0, input));
            //System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void carMenu(BufferedReader reader, Company company) throws SQLException {
        try {
            boolean isRun = true;
            while (isRun) {
                System.out.println(company.getName() + " company\n" +
                        "1. Car list\n" +
                        "2. Create a car\n" +
                        "0. Back\n");


                String input = reader.readLine();
                switch (input) {
                    case "1" -> carList(company.getID());
                    case "2" -> createCar(reader, company);
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void carList(int company_ID) throws SQLException {
        CarDAOImpl cdi = new CarDAOImpl();
        List<Car> li = cdi.getAllFromCompany_ID(company_ID);

        if (li.isEmpty()) {
            System.out.println("The car list is empty!\n");

        } else {
            System.out.println("\nCar list:");
            li.forEach((car) -> System.out.printf("%d. %s\n", li.indexOf(car)+1, car.getName()));
            System.out.println();
        }
    }

    private static void createCar(BufferedReader reader, Company company) throws SQLException {
        try {
            System.out.println("Enter the car name:");
            String input = reader.readLine();
            CarDAOImpl cdi = new CarDAOImpl();
            int result = cdi.insert(new Car(0, input, company.getID()));
            //System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
