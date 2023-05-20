package com.umbrella.carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements DAO<Company> {

    @Override
    public List<Company> getAll() throws SQLException {
        List<Company> companyList = new ArrayList<>();
        Company company = null;

        Connection con = Database.getConnection();
        String sql = "SELECT * FROM company";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            company = new Company();
            company.setID(rs.getInt("ID"));
            company.setName(rs.getString("name"));
            companyList.add(company);
        }

        return companyList;
    }

    @Override
    public Company get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Company company = null;

        String sql = "SELECT id, name FROM company WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int oid = rs.getInt("id");
            String name = rs.getString("name");
            company = new Company(oid, name);
        }

        return company;
    }

    @Override
    public int insert(Company company) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO COMPANY (name) VALUES(?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, company.getName());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    @Override
    public int update(Company company) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE company SET name = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, company.getName());
        ps.setInt(2, company.getID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    @Override
    public int delete(Company company) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM company WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, company.getID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    public String getRentedCarCompany(int carID) throws SQLException {
        Connection con = Database.getConnection();
        String name = "No rented car company";

        String sql = "SELECT id, name FROM company WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, carID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            name = rs.getString("name");
        }

        return name;
    }
}

