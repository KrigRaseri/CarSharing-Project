package com.umbrella.carsharing;

import com.beust.jcommander.JCommander;

import java.sql.SQLException;

public class Main {
    public static String db;

    public static void main(String[] args) {
        try {
            JCommanderImpl jcc = new JCommanderImpl();
            JCommander jc = JCommander.newBuilder().addObject(jcc).build();
            jc.parse(args);
            db = jcc.dbArgs == null ? "carsharing.mv.db" : jcc.dbArgs;

            CarSharingMenu.menuInit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}