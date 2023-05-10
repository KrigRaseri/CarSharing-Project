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


    // TODO: 5/10/2023
    private static void companyList() throws SQLException {

    }

    // TODO: 5/10/2023
    private static void createCompany(Scanner sc) throws SQLException {

    }
}
