package com.umbrella.carsharing.dao.customer;

import com.umbrella.carsharing.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = null;

        Connection con = Database.getConnection();
        String sql = "SELECT * FROM customer";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            customer = new Customer();
            customer.setID(rs.getInt("ID"));
            customer.setName(rs.getString("name"));
            customer.setRentedCarID(rs.getInt("rented_car_id"));
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public Customer get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Customer customer = null;

        String sql = "SELECT id, name FROM customer WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int oid = rs.getInt("id");
            String name = rs.getString("name");
            int rentedCarID = rs.getInt("company_ID");
            customer = new Customer(oid, name, rentedCarID);
        }

        return customer;
    }

    @Override
    public int insert(Customer customer) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO customer (name, rented_car_id) VALUES(?, NULL)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, customer.getName());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    @Override
    public int update(Customer customer) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE customer SET name = ?, SET rented_car_ID = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setInt(2, customer.getRentedCarID());
        ps.setInt(3, customer.getID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    @Override
    public int delete(Customer customer) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM customer WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, customer.getID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    public String getRentedCar(int carID) throws SQLException {
        Connection con = Database.getConnection();
        String name = "No rented car";

        String sql = "SELECT id, name FROM car WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, carID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            name = rs.getString("name");
        }
        return name;
    }

    public int updateRentedCar(int customerID, Integer rentID) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE customer SET rented_car_ID = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        if(rentID == null) {
            ps.setNull(1, java.sql.Types.NULL);
        } else {
            ps.setInt(1, rentID);
        }
        ps.setInt(2, customerID);
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }
}


