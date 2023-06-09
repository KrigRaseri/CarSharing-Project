package com.umbrella.carsharing;

import com.beust.jcommander.Parameter;

/**
 * Handles CLI inputs for the database file names.
 * */
public class JCommanderImpl {
    @Parameter(
            names = "-databaseFileName",
            description = "Database file name"
    )
    String dbArgs;
}