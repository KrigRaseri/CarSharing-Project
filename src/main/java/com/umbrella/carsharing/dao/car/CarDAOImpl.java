package com.umbrella.carsharing.dao.car;

import com.umbrella.carsharing.database.HikariConnection;

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

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM car WHERE company_ID = ?")
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Car car = createCarFromResultSet(rs);
                carList.add(car);
            }
        }

        return carList;
    }

    @Override
    public Car get(int carID) throws SQLException {
        Car car = null;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, name, company_ID, isRented FROM car WHERE id = ?")
        ) {
            ps.setInt(1, carID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                car = createCarFromResultSet(rs);
            }
        }
        return car;
    }

    @Override
    public int insert(Car car) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO car (name, company_ID) VALUES(?, ?)")
        ) {
            ps.setString(1, car.getName());
            ps.setInt(2, car.getCompany_ID());
            result = ps.executeUpdate();
        }

        return result;
    }

    @Override
    public int update(Car car) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE car SET name = ?, company_ID = ?, isRented = ? WHERE id = ?")
        ) {
            ps.setString(1, car.getName());
            ps.setInt(2, car.getCompany_ID());
            ps.setBoolean(3, car.isRented());
            ps.setInt(4, car.getID());
            result = ps.executeUpdate();
        }

        return result;
    }

    @Override
    public int delete(Car car) throws SQLException {
        int result;

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM car WHERE id = ?")
        ) {
            ps.setInt(1, car.getID());
            result = ps.executeUpdate();
        }

        return result;
    }

    @Override
    public List<Car> getAllFromCompanyID(int company_ID) throws SQLException {
        List<Car> carList = new ArrayList<>();

        try (Connection con = HikariConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM car WHERE company_ID = ?");
             ResultSet rs = ps.executeQuery()
        ) {
            ps.setInt(1, company_ID);

            while (rs.next()) {
                Car car = createCarFromResultSet(rs);
                carList.add(car);
            }
        }

        return carList;
    }

    /**
     * Creates a Car object from the current row of the ResultSet.
     *
     * @param rs the ResultSet containing car data
     * @return a Car object
     * @throws SQLException if a database error occurs
     */
    private Car createCarFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String name = rs.getString("name");
        int company_ID = rs.getInt("company_ID");
        boolean isRented = rs.getBoolean("isRented");

        return new Car(id, name, company_ID, isRented);
    }
}

