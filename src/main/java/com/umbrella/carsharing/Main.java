package com.umbrella.carsharing;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;

import com.umbrella.carsharing.database.CreateTables;
import com.umbrella.carsharing.menu.CarSharingMenuMain;

/**
 * This program is to rent cars from a company, and save relevant data to a database. A manager can add companies and
 * the cars that are offered by that company. The customer can choose the car to rent, check the car, or return the car.
 * */
public class Main {
    public static String db;

    public static void main(String[] args) {
        CarSharingGuice carSharingGuice = new CarSharingGuice();
        Injector injector = Guice.createInjector(carSharingGuice);
        CarSharingMenuMain csm = injector.getInstance(CarSharingMenuMain.class);

        JCommanderImpl jcc = new JCommanderImpl();
        JCommander jc = JCommander.newBuilder().addObject(jcc).build();
        jc.parse(args);
        db = jcc.dbArgs == null ? "carsharing" : jcc.dbArgs;

        // Create the required tables in the database
        CreateTables.createCompanyTable();
        CreateTables.createCarTable();
        CreateTables.createCustomerTable();

        // Initialize the car sharing menu
        csm.menuInit();
    }
}