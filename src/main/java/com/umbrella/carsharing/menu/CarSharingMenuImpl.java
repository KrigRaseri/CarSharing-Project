package com.umbrella.carsharing.menu;

import com.umbrella.carsharing.dao.car.Car;
import com.umbrella.carsharing.dao.car.CarDAO;
import com.umbrella.carsharing.dao.company.Company;
import com.umbrella.carsharing.dao.company.CompanyDAO;
import com.umbrella.carsharing.dao.customer.Customer;
import com.umbrella.carsharing.dao.customer.CustomerDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;


public class CarSharingMenuImpl implements CarSharingMenu {
    private CompanyDAO companyDAO;
    private CarDAO carDAO;
    private CustomerDAO customerDAO;

    @Override
    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public void setCarDAO(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    @Override
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    

    //------------------------------ Main menu section ------------------------------
    public void menuInit() throws SQLException {
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

    private void loginManager(BufferedReader reader) throws SQLException {
        try {
            boolean isRun = true;
            while(isRun) {
                System.out.println();
                System.out.println("1. Company list");
                System.out.println("2. Create a company");
                System.out.println("0. Back");

                String input;
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

    //port to main
    private void loginCustomer(BufferedReader reader) {
        try {
            while (true) {
                List<Customer> li = customerDAO.getAll();
                if (li.isEmpty()) {
                    System.out.println("The customer list is empty!\n");
                    break;

                } else {
                    System.out.println();
                    System.out.println("The customer list:");
                    li.forEach((c) -> System.out.printf("%d. %s\n", li.indexOf(c)+1, c.getName()));
                    System.out.println("0. Back");
                    int input = Integer.parseInt(reader.readLine());
                    if (input == 0) {break;}
                    System.out.println();
                    customerMenu(reader, li.get(input - 1));
                    break;
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCustomer(BufferedReader reader) {
        try {
            System.out.println("\nEnter the customer name:");
            String input = reader.readLine();
            customerDAO.insert(new Customer(0, input));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("The customer was added!\n");
        }
    }


    //------------------------------ Manager Menu's -----------------------------
    private void companySelection(BufferedReader reader) throws SQLException {
        try {
            while (true) {
                List<Company> li = companyDAO.getAll();
                if (li.isEmpty()) {
                    System.out.println("The company list is empty\n");
                    break;

                } else {
                    System.out.println();
                    System.out.println("Choose a company:");
                    li.forEach((company) -> System.out.printf("%d. %s\n", company.getID(), company.getName()));
                    System.out.println("0. Back");

                    int result = Integer.parseInt(reader.readLine());
                    if (result == 0) {break;}
                    carMenu(reader, li.get(result-1));
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCompany(BufferedReader reader) throws SQLException {
        try {
            System.out.println();
            System.out.println("Enter the company name:");
            String input = reader.readLine();
            int result = companyDAO.insert(new Company(0, input));
            //System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void companyList() throws SQLException {
        List<Company> li = companyDAO.getAll();
        if (li.isEmpty()) {
            System.out.println("The company list is empty\n");

        } else {
            System.out.println("\nCompany list:");
            li.forEach((company) -> System.out.printf("%d. %s\n", company.getID(), company.getName()));
            System.out.println("0. Back");
        }
    }

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

    private boolean carList(int company_ID) throws SQLException {
        boolean isEmpty = true;
        List<Car> li = carDAO.getAllFromCompany_ID(company_ID);
        List<Car> availableCars = li.stream().filter(klo -> !klo.isRented()).toList();
        availableCars.forEach(System.out::println);

        if (li.isEmpty()) {
            System.out.println("The car list is empty!\n");

        } else {
            System.out.println("\nCar list:");
            availableCars.forEach((car) -> System.out.printf("%d. %s\n", availableCars.indexOf(car)+1, car.getName()));
            System.out.println("0. Back");
            System.out.println();
            isEmpty = false;
        }
        return isEmpty;
    }

    private void createCar(BufferedReader reader, Company company) throws SQLException {
        try {
            System.out.println("Enter the car name:");
            String input = reader.readLine();
            int result = carDAO.insert(new Car(0, input, company.getID(), false));
            //System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------------ Customer section ------------------------------
    private void customerMenu(BufferedReader reader, Customer customer) {
        try {
            boolean isRun = true;
            while(isRun) {
                System.out.println("1. Rent a car");
                System.out.println("2. Return a rented car");
                System.out.println("3. My rented car");
                System.out.println("0. Back");

                String input = reader.readLine();
                switch (input) {
                    case "1" -> rentCar(reader, customer, customer.getRentedCarID());
                    case "2" -> returnCar(customer, customer.getRentedCarID());
                    case "3" -> myCar(customer.getRentedCarID());
                    case "0" -> isRun = false;
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void rentCar(BufferedReader reader, Customer customer, int carID) {
        try {
            while (true) {
                if (!customerDAO.getRentedCar(carID).equals("No rented car")) {
                    System.out.println("You've already rented a car!");
                    break;
                }

                companyList();
                System.out.println("Choose a company:");
                int chooseCompany = Integer.parseInt(reader.readLine());
                if (chooseCompany == 0) {break;}
                if(carList(chooseCompany)) {break;}

                System.out.println("Choose a car:");
                int chooseCar = Integer.parseInt(reader.readLine());
                if (chooseCar == 0) {continue;}
                customerDAO.updateRentedCar(customer.getID(), chooseCar);
                customer.setRentedCarID(chooseCar);

                Car car = carDAO.get(customer.getRentedCarID());
                car.setRented(true);
                carDAO.update(car);
                System.out.printf("You rented '%s'\n", customerDAO.getRentedCar(customer.getRentedCarID()));
                break;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void returnCar(Customer customer, int carID) throws SQLException {
        if (customerDAO.getRentedCar(carID).equals("No rented car")) {
            System.out.println("You didn't rent a car!");
        } else {
            customerDAO.updateRentedCar(customer.getID(), null);
            customer.setRentedCarID(null);
            Car car = carDAO.get(carID);
            car.setRented(false);
            carDAO.update(car);
            System.out.println("You've returned a rented car!");
        }
    }

    private void myCar(int carID) throws SQLException {
        if (customerDAO.getRentedCar(carID).equals("No rented car")) {
            System.out.println("You didn't rent a car!");
        } else {
            System.out.println("Your rented car:");
            System.out.println(customerDAO.getRentedCar(carID));
            System.out.println("Company:");
            System.out.println(companyDAO.getRentedCarCompany(carID));
            System.out.println();
        }
    }
}
