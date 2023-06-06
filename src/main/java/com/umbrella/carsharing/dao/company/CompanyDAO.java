package com.umbrella.carsharing.dao.company;

import com.umbrella.carsharing.dao.DAO;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO extends DAO<Company> {
    List<Company> getAll() throws SQLException;

    Company get(int companyID) throws SQLException;

    int insert(Company company) throws SQLException;

    int update(Company company) throws SQLException;

    int delete(Company company) throws SQLException;

    String getRentedCarCompany(int carID) throws SQLException;
}
