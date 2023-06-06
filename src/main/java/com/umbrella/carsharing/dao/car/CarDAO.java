package com.umbrella.carsharing.dao.car;

import com.umbrella.carsharing.dao.DAO;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends DAO<Car> {
    List<Car> getAll() throws SQLException;

    Car get(int carID) throws SQLException;

    int insert(Car car) throws SQLException;

    int update(Car car) throws SQLException;

    int delete(Car car) throws SQLException;
}
