package com.umbrella.carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public interface CarSharingMenu {
    static void menuInit() throws SQLException {
        try (Scanner sc = new Scanner(System.in)) {
            boolean isRun = true;
            while(isRun) {
                System.out.println("1. Log in as a manager");
                System.out.println("0. Exit");

                switch (sc.nextLine()) {
                    case "1" -> logIn(sc);
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        }
    }


    private static void logIn(Scanner sc) throws SQLException {
        boolean isRun = true;
        while(isRun) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            switch (sc.nextLine()) {
                case "1" -> companyList();
                case "2" -> createCompany(sc);
                case "0" -> isRun = false;
                default -> System.out.println("Invalid command");
            }
        }
    }


    private static void companyList() throws SQLException {
        CompanyDAOImpl cdi = new CompanyDAOImpl();
        List<Company> li = cdi.getAll();
        if (li.isEmpty()) {
            System.out.println("The company list is empty\n");
        } else {
            System.out.println("\nCompany list:");
            for (Company c : li) {
                System.out.printf("%d. %s\n", c.getID(), c.getName());
            }
            System.out.println();
        }
    }

    private static void createCompany(Scanner sc) throws SQLException {
        System.out.println("Enter the company name:");
        String input = sc.nextLine();
        CompanyDAOImpl cdi = new CompanyDAOImpl();
        int result = cdi.insert(new Company(0, input));
        System.out.println(result);
    }
}
