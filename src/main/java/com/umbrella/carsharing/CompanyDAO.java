package com.umbrella.carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO extends DAO<Company> {
    List<Company> getAllCompany() throws SQLException;
    Company getCompany(int id) throws SQLException;
    int insertCompany(Company company) throws SQLException;
    int updateCompany(Company company) throws SQLException;
    int deleteCompany(Company company) throws SQLException;
}
