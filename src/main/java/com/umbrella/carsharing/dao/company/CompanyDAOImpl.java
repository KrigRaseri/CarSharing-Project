package com.umbrella.carsharing.dao.company;

import com.umbrella.carsharing.database.HikariConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    @Override
    public List<Company> getAll() throws SQLException {
        List<Company> companyList = new ArrayList<>();

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM company");
             ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Company company = createCompanyFromResultSet(rs);
                companyList.add(company);
            }
        }

        return companyList;
    }

    @Override
    public Company get(int id) throws SQLException {
        Company company = null;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, name FROM company WHERE id = ?");
             ResultSet rs = ps.executeQuery()
        ) {
            ps.setInt(1, id);

            if (rs.next()) {
                company = createCompanyFromResultSet(rs);
            }
        }

        return company;
    }

    @Override
    public int insert(Company company) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO COMPANY (name) VALUES(?)")
        ) {
            ps.setString(1, company.getName());
            result = ps.executeUpdate();
        }

        return result;
    }

    @Override
    public int update(Company company) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE company SET name = ? WHERE id = ?")
        ) {
            ps.setString(1, company.getName());
            ps.setInt(2, company.getID());
            result = ps.executeUpdate();
        }

        return result;
    }

    @Override
    public int delete(Company company) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM company WHERE id = ?")
        ) {
            ps.setInt(1, company.getID());
            result = ps.executeUpdate();
        }

        return result;
    }

    public String getRentedCarCompany(int carID) throws SQLException {
        String name = "No rented car company";

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, name FROM company WHERE id = ?");
             ResultSet rs = ps.executeQuery()
        ) {
            ps.setInt(1, carID);
            if (rs.next()) {
                name = rs.getString("name");
            }
        }

        return name;
    }

    /**
     * Creates a Company object from the current row of the ResultSet.
     *
     * @param rs the ResultSet containing company data
     * @return a Company object
     * @throws SQLException if a database error occurs
     */
    private Company createCompanyFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String name = rs.getString("name");

        return new Company(id, name);
    }
}

