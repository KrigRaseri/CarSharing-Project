package com.umbrella.carsharing.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    List<T> getAll() throws SQLException;
    T get(int id) throws SQLException;
    int insert(T t) throws SQLException;
    int update(T t) throws SQLException;
    int delete(T t) throws SQLException;
}

