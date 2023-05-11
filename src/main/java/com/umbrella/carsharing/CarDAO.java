package com.umbrella.carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends DAO<Car> {
    List<Car> getAll() throws SQLException;
    Car get(int id) throws SQLException;
    int insert(Car car) throws SQLException;
    int update(Car car) throws SQLException;
    int delete(Car car) throws SQLException;
}
