package com.umbrella.carsharing.dao.customer;

import com.umbrella.carsharing.database.HikariConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    /**
     * Retrieves all customers from the database.
     *
     * @return a list of all customers
     * @throws SQLException if a database error occurs
     */
    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customerList = new ArrayList<>();

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM customer");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer customer = createCustomerFromResultSet(rs);
                customerList.add(customer);
            }
        }
        return customerList;
    }

    /**
     * Retrieves a customer by their ID from the database.
     *
     * @param id the ID of the customer
     * @return the customer with the specified ID, or null if not found
     * @throws SQLException if a database error occurs
     */
    @Override
    public Customer get(int id) throws SQLException {
        Customer customer = null;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, name, rented_car_id FROM customer WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = createCustomerFromResultSet(rs);
            }
        }
        return customer;
    }

    /**
     * Inserts a new customer into the database.
     *
     * @param customer the customer to insert
     * @return the number of rows affected (1 if successful, 0 otherwise)
     * @throws SQLException if a database error occurs
     */
    @Override
    public int insert(Customer customer) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO customer (name, rented_car_id) VALUES(?, NULL)")) {

            ps.setString(1, customer.getName());
            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param customer the customer to update
     * @return the number of rows affected (1 if successful, 0 otherwise)
     * @throws SQLException if a database error occurs
     */
    @Override
    public int update(Customer customer) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE customer SET name = ?, rented_car_ID = ? WHERE id = ?")) {

            ps.setString(1, customer.getName());
            if (customer.getRentedCarID() == null) {
                ps.setNull(2, java.sql.Types.NULL);
            } else {
                ps.setInt(2, customer.getRentedCarID());
            }
            ps.setInt(3, customer.getID());
            result = ps.executeUpdate();
        }

        return result;
    }

    /**
     * Deletes a customer from the database.
     *
     * @param customer the customer to delete
     * @return the number of rows affected (1 if successful, 0 otherwise)
     * @throws SQLException if a database error occurs
     */
    @Override
    public int delete(Customer customer) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM customer WHERE id = ?")) {

            ps.setInt(1, customer.getID());
            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     * Creates a Customer object from the current row of the ResultSet.
     *
     * @param rs the ResultSet containing customer data
     * @return a Customer object
     * @throws SQLException if a database error occurs
     */
    private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String name = rs.getString("name");
        int rentedCarID = rs.getInt("rented_car_id");

        return new Customer(id, name, rentedCarID);
    }
}


