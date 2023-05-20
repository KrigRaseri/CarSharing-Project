package com.umbrella.carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements DAO<Car> {

    @Override
    public List<Car> getAll() throws SQLException {
        List<Car> carList = new ArrayList<>();
        Car car = null;

        Connection con = Database.getConnection();
        String sql = "SELECT * FROM car WHERE company_ID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            car = new Car();
            car.setID(rs.getInt("ID"));
            car.setName(rs.getString("name"));
            car.setCompany_ID(rs.getInt("company_ID"));
            carList.add(car);
        }
        return carList;
    }

    @Override
    public Car get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Car car = null;

        String sql = "SELECT id, name FROM car WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int oid = rs.getInt("id");
            String name = rs.getString("name");
            int company_ID = rs.getInt("company_ID");
            car = new Car(oid, name, company_ID);
        }

        return car;
    }

    @Override
    public int insert(Car car) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "INSERT INTO car (name, company_ID) VALUES(?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, car.getName());
        ps.setInt(2, car.getCompany_ID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    @Override
    public int update(Car car) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "UPDATE car SET name = ?, SET company_ID WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, car.getName());
        ps.setInt(2, car.getCompany_ID());
        ps.setInt(3, car.getID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    @Override
    public int delete(Car car) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "DELETE FROM car WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, car.getID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }

    public List<Car> getAllFromCompany_ID(int company_ID) throws SQLException {
        List<Car> carList = new ArrayList<>();
        Car car = null;

        Connection con = Database.getConnection();
        String sql = "SELECT * FROM car WHERE company_ID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, company_ID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            car = new Car();
            car.setID(rs.getInt("ID"));
            car.setName(rs.getString("name"));
            car.setCompany_ID(rs.getInt("company_ID"));
            carList.add(car);
        }
        return carList;
    }
}
