package com.umbrella.carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {

    @Override
    public List<Car> getAll() throws SQLException {
        List<Car> carList = new ArrayList<>();
        Car car = null;

        Connection con = Database.getConnection();
        String sql = "SELECT * FROM car";
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
        String sql = "INSERT INTO COMPANY (name, company_ID) VALUES(?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, car.getName());
        ps.setInt(2, car.getCompany_ID());
        int result = ps.executeUpdate();

        ps.close();
        con.close();

        return result;
    }
}
