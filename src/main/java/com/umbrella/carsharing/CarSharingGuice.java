package com.umbrella.carsharing;

import com.google.inject.AbstractModule;
import com.umbrella.carsharing.dao.car.CarDAO;
import com.umbrella.carsharing.dao.car.CarDAOImpl;
import com.umbrella.carsharing.dao.company.CompanyDAO;
import com.umbrella.carsharing.dao.company.CompanyDAOImpl;
import com.umbrella.carsharing.dao.customer.CustomerDAO;
import com.umbrella.carsharing.dao.customer.CustomerDAOImpl;
import com.umbrella.carsharing.menu.CarSharingMenuMain;
import com.umbrella.carsharing.menu.CarSharingMenuUtil;
import com.umbrella.carsharing.menu.CustomerMenu;
import com.umbrella.carsharing.menu.ManagerMenu;

/**
 * Configures the dependency injection bindings using Guice.
 */
public class CarSharingGuice extends AbstractModule {
        @Override
        protected void configure() {
            bind(CompanyDAO.class).to(CompanyDAOImpl.class);
            bind(CarDAO.class).to(CarDAOImpl.class);
            bind(CustomerDAO.class).to(CustomerDAOImpl.class);
            bind(CarSharingMenuUtil.class);
            bind(CustomerMenu.class);
            bind(ManagerMenu.class);
            bind(CarSharingMenuMain.class);
        }
}