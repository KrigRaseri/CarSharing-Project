package com.umbrella.carsharing.dao.customer;

import com.umbrella.carsharing.dao.DAO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends DAO<Customer> {
    List<Customer> getAll() throws SQLException;

    Customer get(int customerID) throws SQLException;

    int insert(Customer customer) throws SQLException;

    int update(Customer customer) throws SQLException;

    int delete(Customer customer) throws SQLException;

    String getRentedCar(int carID) throws SQLException;

    int updateRentedCar(int customerID, Integer rentID) throws SQLException;
}
