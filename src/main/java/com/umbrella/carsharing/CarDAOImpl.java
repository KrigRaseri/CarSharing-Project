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
}
