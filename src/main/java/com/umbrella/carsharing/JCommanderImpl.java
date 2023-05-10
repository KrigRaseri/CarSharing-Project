package com.umbrella.carsharing;

import com.beust.jcommander.Parameter;

public class JCommanderImpl {
    @Parameter(
            names = "-databaseFileName",
            description = "Database file name"
    )
    String dbArgs;
}