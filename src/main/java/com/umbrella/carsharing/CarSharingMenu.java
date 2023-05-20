package com.umbrella.carsharing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;


public interface CarSharingMenu {
    CompanyDAOImpl cdi = new CompanyDAOImpl();
    CarDAOImpl carDAO = new CarDAOImpl();
    CustomerDAOImpl cd = new CustomerDAOImpl();

    //------------------------------ Main menu section ------------------------------
    static void menuInit() throws SQLException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean isRun = true;
            while(isRun) {
                System.out.println("1. Log in as a manager");
                System.out.println("2. Log in as a customer");
                System.out.println("3. Create a customer");
                System.out.println("0. Exit");

                String input = reader.readLine();
                switch (input) {
                    case "1" -> loginManager(reader);
                    case "2" -> loginCustomer(reader);
                    case "3" -> createCustomer(reader);
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loginManager(BufferedReader reader) throws SQLException {
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

    private static void loginCustomer(BufferedReader reader) {
        try {
            List<Customer> li = cd.getAll();
            if (li.isEmpty()) {
                System.out.println("The customer list is empty!\n");

            } else {
                System.out.println("\nChoose a customer:");
                li.forEach((c) -> System.out.printf("%d. %s\n", li.indexOf(c)+1, c.getName()));
                System.out.println("0. Back");

                customerMenu(reader, li.get(Integer.parseInt(reader.readLine())-1));
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createCustomer(BufferedReader reader) {
        try {
            System.out.println("Enter the customer name:");
            String input = reader.readLine();
            cd.insert(new Customer(0, input));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("The customer was added!");
        }
    }


    //------------------------------ Manager Menu's -----------------------------
    private static void companySelection(BufferedReader reader) throws SQLException {
        try {
            boolean isRun = true;
            while (isRun) {
                List<Company> li = cdi.getAll();
                if (li.isEmpty()) {
                    System.out.println("The company list is empty\n");
                    break;

                } else {
                    System.out.println("\nChoose a company:");
                    li.forEach((company) -> System.out.printf("%d. %s\n", company.getID(), company.getName()));
                    System.out.println("0. Back");
                    System.out.println();

                    int result = Integer.parseInt(reader.readLine());
                    if (result == 0) {isRun = false; break;}
                    carMenu(reader, li.get(result-1));
                    isRun = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createCompany(BufferedReader reader) throws SQLException {
        try {
            System.out.println("Enter the company name:");
            String input = reader.readLine();
            int result = cdi.insert(new Company(0, input));
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void companyList() throws SQLException {
        List<Company> li = cdi.getAll();
        if (li.isEmpty()) {
            System.out.println("The company list is empty\n");

        } else {
            System.out.println("\nCompany list:");
            li.forEach((company) -> System.out.printf("%d. %s\n", company.getID(), company.getName()));
            System.out.println("0. Back");
            System.out.println();
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

    private static boolean carList(int company_ID) throws SQLException {
        boolean isEmpty = true;
        List<Car> li = carDAO.getAllFromCompany_ID(company_ID);

        if (li.isEmpty()) {
            System.out.println("The car list is empty!\n");

        } else {
            System.out.println("\nCar list:");
            li.forEach((car) -> System.out.printf("%d. %s\n", li.indexOf(car)+1, car.getName()));
            System.out.println("0. Back");
            System.out.println();
            isEmpty = false;
        }
        return isEmpty;
    }

    private static void createCar(BufferedReader reader, Company company) throws SQLException {
        try {
            System.out.println("Enter the car name:");
            String input = reader.readLine();
            int result = carDAO.insert(new Car(0, input, company.getID()));
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------------ Customer section ------------------------------
    private static void customerMenu(BufferedReader reader, Customer customer) {
        try {
            boolean isRun = true;
            while(isRun) {
                System.out.println("1. Rent a car");
                System.out.println("2. Return a rented car");
                System.out.println("3. My rented car");
                System.out.println("0. Exit");

                String input = reader.readLine();
                System.out.println();
                switch (input) {
                    case "1" -> rentCar(reader, customer);
                    case "2" -> returnCar(customer);
                    case "3" -> myCar(customer.getRentedCarID());
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void rentCar(BufferedReader reader, Customer customer) {
        try {
            boolean isRun = true;
            while (isRun) {
                if (customer.getRentedCarID() != null) {
                    System.out.println("You've already rented a car!");
                    isRun = false;
                    break;
                }

                companyList();
                System.out.println("Choose a company:");
                int chooseCompany = Integer.parseInt(reader.readLine());
                if (chooseCompany == 0) {isRun = false; break;}
                if(carList(chooseCompany)) {isRun = false; break;}

                System.out.println("Choose a car:");
                int chooseCar = Integer.parseInt(reader.readLine());
                if (chooseCar == 0) {continue;}
                cd.updateRentedCar(customer.getID(), chooseCar);
                customer.setRentedCarID(chooseCar);
                System.out.printf("You rented '%s'\n", cd.getRentedCar(customer.getRentedCarID()));
                isRun = false;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void returnCar(Customer customer) throws SQLException {
        cd.updateRentedCar(customer.getID(), null);
        customer.setRentedCarID(null);
        System.out.println("You've returned a rented car!");
    }

    private static void myCar(int carID) throws SQLException {
        System.out.println("Your rented car:");
        System.out.println(cd.getRentedCar(carID));
        System.out.println("Company:");
        System.out.println(cdi.getRentedCarCompany(carID));
        System.out.println();
    }
}