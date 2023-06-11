Car Sharing System

    @Author Brandon Bilodeau AKA Krig Raseri

The Car Sharing System is a Java-based application that facilitates car sharing between companies and users. 
It provides functionalities for managing cars, companies, and user bookings. This project demonstrates the use of 
object-oriented programming principles, database integration, and SQL operations.

Features

    Car Management: Add, update, and delete cars from the system. Each car has a name, associated company, and rental status.
    Company Management: Manage companies that provide car sharing services. Add, update, and delete company information.
    User Bookings: Allow users to make bookings for available cars. Track the rental status and availability of cars.
    Database Integration: Utilize a database to store and retrieve car, company, and booking information. 
    The project uses JDBC for database connectivity.

Technologies Used

    Java
    JDBC (Java Database Connectivity)
    H2 (Database)
    HikariCP (Connection pooling)
    Gradle (Build tool)
    Lombok 
    JCommander (CLI inputs)
    Guice (Dependency Injection)

Getting Started

    Use the jar included to run the program to get started, or output your own jar, located in build -> libs. Use the CLI command 
    -databaseFileName followed by the name of the db file name you wish to use. This uses H2 database but could be
    easily converted to something like MySQL.

Usage

    Upon running the application, you will be presented with a menu of options.
    Use the menu options to navigate through different functionalities such as car management, company management, 
    and user bookings. Follow the on-screen prompts and provide the required information to perform various operations.
    The application will interact with the database to store and retrieve data as needed.

Contributing

    Contributions to the Car Sharing System project are welcome! If you find any issues or have suggestions for 
    improvements, please submit a pull request or open an issue in the project repository.

License

    This project is licensed under the MIT License.
