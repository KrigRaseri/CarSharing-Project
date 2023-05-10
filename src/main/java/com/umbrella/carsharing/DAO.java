package com.umbrella.carsharing;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    List<Company> getAllCompany() throws SQLException;
    Company getCompany(int id) throws SQLException;
    int insertCompany(T t) throws SQLException;
    int updateCompany(T t) throws SQLException;
    int delete(T t) throws SQLException;
}

