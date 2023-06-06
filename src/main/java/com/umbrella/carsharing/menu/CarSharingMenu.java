package com.umbrella.carsharing.menu;

import com.umbrella.carsharing.dao.car.CarDAO;
import com.umbrella.carsharing.dao.company.CompanyDAO;
import com.umbrella.carsharing.dao.customer.CustomerDAO;

import java.sql.SQLException;

public interface CarSharingMenu {
    void setCompanyDAO(CompanyDAO companyDAO);
    void setCarDAO(CarDAO carDAO);
    void setCustomerDAO(CustomerDAO customerDAO);
    void menuInit() throws SQLException;
}
