package com.mycompany.fys;

import com.mycompany.fys.DbClasses.Role;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Leon
 */
public class Repository {

    private static final String DB_DEFAULT_DATABASE = "findmyluggage";
    private static final String DB_DEFAULT_SERVER_URL = "localhost:3306";
    private static final String DB_DEFAULT_ACCOUNT = "root";
    private static final String DB_DEFAULT_PASSWORD = "root";

    private final static String DB_DRIVER_URL = "com.mysql.jdbc.Driver";
    private final static String DB_DRIVER_PREFIX = "jdbc:mysql://";
    private final static String DB_DRIVER_PARAMETERS = "?useSSL=false";

    private Connection connection = null;

    // set for verbose logging of all queries
    private boolean verbose = true;

    // remembers the first error message on the connection 
    private String errorMessage = null;

    //constructors
    public Repository() {
        this(DB_DEFAULT_DATABASE, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public Repository(String dbName) {
        this(dbName, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public Repository(String dbName, String serverURL, String account, String password) {
        try {
            // verify that a proper JDBC driver has been installed and linked
            if (!selectDriver(DB_DRIVER_URL)) {
                return;
            }

            if (password == null) {
                password = "";
            }

            // establish a connection to a named database on a specified server	
            String connStr = DB_DRIVER_PREFIX + serverURL + "/" + dbName + DB_DRIVER_PARAMETERS;
            log("Connecting " + connStr);
            this.connection = DriverManager.getConnection(connStr, account, password);
        } catch (SQLException eSQL) {
            error(eSQL);
            this.close();
        }
    }

    /**
     * *
     * echoes a message on the system console, if run in verbose mode
     *
     * @param message
     */
    public void log(String message) {
        if (isVerbose()) {
            System.out.println("MyJDBC: " + message);
        }
    }

    public final void close() {

        if (this.connection == null) {
            // db has been closed earlier already
            return;
        }
        try {
            this.connection.close();
            this.connection = null;
            this.log("Data base has been closed");
        } catch (SQLException eSQL) {
            error(eSQL);
        }
    }

    /**
     * *
     * elects proper loading of the named driver for database connections. This
     * is relevant if there are multiple drivers installed that match the JDBC
     * type
     *
     * @param driverName the name of the driver to be activated.
     * @return indicates whether a suitable driver is available
     */
    private Boolean selectDriver(String driverName) {
        try {
            Class.forName(driverName);
            // Put all non-prefered drivers to the end, such that driver selection hits the first
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver d = drivers.nextElement();
                if (!d.getClass().getName().equals(driverName)) {   // move the driver to the end of the list
                    DriverManager.deregisterDriver(d);
                    DriverManager.registerDriver(d);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            error(ex);
            return false;
        }
        return true;
    }

    /**
     * *
     * echoes an exception and its stack trace on the system console. remembers
     * the message of the first error that occurs for later reference. closes
     * the connection such that no further operations are possible.
     *
     * @param e
     */
    public final void error(Exception e) {
        String msg = "MyJDBC-" + e.getClass().getName() + ": " + e.getMessage();

        // capture the message of the first error of the connection
        if (this.errorMessage == null) {
            this.errorMessage = msg;
        }
        System.out.println(msg);
        e.printStackTrace();

        // if an error occurred, close the connection to prevent further operations
        this.close();
    }

    /**
     * **
     * Executes a DDL, DML or DCL query that does not yield a rs set
     *
     * @param tableName the full name of the table you wish to insert into.
     * @param itemId the Id of the item you wish to update in the database
     * @param idColumn the name of the column containing the primary Key of the
     * table
     * @param columnNames the names of the columns you want to update in order
     * with values
     * @param values the values you wish to update in order with columnNames
     * @return the number of rows that have been impacted, -1 on error
     */
    public int executeUpdate(String tableName, String whereValue, String whereColumn, String[] columnNames, String[] values) {
        try {
            Statement s = this.connection.createStatement();
            String setTotal = "SET ";

            // Add each value of both columnNames with matching value to setTotal
            for (int i = 0; i < values.length; i++) {
                if (values[i] == values[values.length - 1]) {
                    setTotal += columnNames[i] + "=" + "'" + values[i] + "'";
                } else {
                    setTotal += columnNames[i] + "=" + "'" + values[i] + "', ";
                }
            }

            String totalQuery = "UPDATE " + tableName + " " + setTotal + " WHERE " + whereColumn + " = " + "'" + whereValue + "'";
            log(totalQuery);

            int n = s.executeUpdate(totalQuery);
            s.close();
            return (n);
        } catch (SQLException ex) {
            // handle exception
            error(ex);
            return -1;
        }
    }

    /**
     * **
     * Executes a DDL, DML or DCL insert query that does not yield a rs set
     *
     * @param tableName the full name of the table you wish to insert into.
     * @param values the values you wish to insert into the table.
     * @return the number of rows that have been impacted, -1 on error
     */
    public int executeInsert(String tableName, String[] columnNames, String[] values) {
        try {
            Statement s = this.connection.createStatement();
            String valuesTotal = "";
            String columnNamesTotal = "";

            // Add each value to a total string of values to insert
            for (int i = 0; i < values.length; i++) {
                if (columnNames[i] == columnNames[columnNames.length - 1]) {
                    valuesTotal += "'" + values[i] + "'";
                    columnNamesTotal += columnNames[i];
                } else {
                    valuesTotal += "'" + values[i] + "', ";
                    columnNamesTotal += "" + columnNames[i] + ", ";
                }
            }

            String totalQuery = "INSERT INTO " + tableName + " (" + columnNamesTotal + ")" + " VALUES (" + valuesTotal + ")";

            log(totalQuery);
            int n = s.executeUpdate(totalQuery);

            s.close();
            return (n);
        } catch (SQLException ex) {
            //handle exception
            error(ex);
            return -1;
        }
    }

    public LinkedList<LinkedList> executeSelect(String tableName) {
        try {
            Statement s = this.connection.createStatement();
            String totalQuery = "SELECT * FROM " + tableName;
            log(totalQuery);

            // Print out the rs
            ResultSet rs = s.executeQuery(totalQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            LinkedList result = new LinkedList();

            while (rs.next()) {
                LinkedList col = new LinkedList();
                for (int i = 1; i <= columnsNumber; i++) {
                    col.add(rs.getString(i));
                }
                result.add(col);
            }

            s.close();
            return (result);
        } catch (SQLException ex) {
            //handle exception
            error(ex);
            return new LinkedList<>();
        }
    }

    public LinkedList<LinkedList> executeCustomSelect(String query) {
        try {
            Statement s = this.connection.createStatement();

            ResultSet rs = s.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            LinkedList result = new LinkedList();

            while (rs.next()) {
                LinkedList col = new LinkedList();
                for (int i = 1; i <= columnsNumber; i++) {
                    col.add(rs.getString(i));
                }
                result.add(col);
            }

            s.close();
            return (result);
        } catch (SQLException ex) {
            //Log error
            error(ex);
            return new LinkedList();
        }
    }

    public LinkedList<LinkedList> executeSelect(String tableName, String[] whereColumns, String[] whereValues) {
        try {
            Statement s = this.connection.createStatement();
            String totalQuery = "SELECT * FROM " + tableName + " WHERE ";

            for (int i = 0; i < whereColumns.length; i++) {
                if (whereColumns[i] == whereColumns[whereColumns.length - 1]) {
                    totalQuery += tableName + "." + whereColumns[i] + " = " + "'" + whereValues[i] + "'";
                } else {
                    totalQuery += tableName + "." + whereColumns[i] + " = " + "'" + whereValues[i] + "' AND ";
                }
            }

            log(totalQuery);

            // Print out the rs
            ResultSet rs = s.executeQuery(totalQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            LinkedList result = new LinkedList();

            while (rs.next()) {
                LinkedList col = new LinkedList();
                for (int i = 1; i <= columnsNumber; i++) {
                    col.add(rs.getString(i));
                }
                result.add(col);
            }

            s.close();
            return (result);
        } catch (SQLException ex) {
            //handle exception
            error(ex);
            return new LinkedList<>();
        }
    }

    public int executeUpdateQuery(String sql) {
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            int n = s.executeUpdate(sql);
            s.close();
            return 1;
        } catch (SQLException ex) {
            // handle exception
            error(ex);
            return 0;
        }
    }

    public static boolean dbExists() {
        Repository repo = new Repository("sys");

        try {
            Statement s = repo.connection.createStatement();
            String sql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = " + "'" + DB_DEFAULT_DATABASE + "'";
            repo.log(sql);
            ResultSet n = s.executeQuery(sql);
            if (n.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            repo.error(ex);
        }

        return false;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static void addDummyData() {
        Repository repo = new Repository();
        
        // Insert dummy roles
        repo.executeInsert("role", new String[]{"Name"}, new String[]{"Medewerker"});
        repo.executeInsert("role", new String[]{"Name"}, new String[]{"Administratie"});

        // Insert dummy airports
        repo.executeInsert("airport", new String[]{"IATACode", "Name", "Country"}, new String[]{"SCHIP", "Schiphol", "Amsterdam"});
        repo.executeInsert("airport", new String[]{"IATACode", "Name", "Country"}, new String[]{"BARCE", "Barcelona Airport", "Barcelona"});

        // Insert Dummy Adrresses
        repo.executeInsert("address", new String[]{"Street", "Number", "Place", "PostalCode", "Country"}, new String[]{"Kerkstraat", "11", "Amsterdam", "1234AH", "Nederland"});
        repo.executeInsert("address", new String[]{"Street", "Number", "Place", "PostalCode", "Country"}, new String[]{"Espana", "165", "Barcelona", "1234YG", "Spanje"});

        // Insert Dummy statuses
        repo.executeInsert("status", new String[]{"Name"}, new String[]{"Vermist"});
        repo.executeInsert("status", new String[]{"Name"}, new String[]{"Gevonden"});
        repo.executeInsert("status", new String[]{"Name"}, new String[]{"In verzending"});
        repo.executeInsert("status", new String[]{"Name"}, new String[]{"Afgeleverd"});

        // Insert Dummy users
        repo.executeInsert("user", new String[]{"Username", "Password", "Email", "RoleId", "AirportId"},
                new String[]{"user1", "Welkom01.", "user1@gmail.com", "1", "1"});
        repo.executeInsert("user", new String[]{"Username", "Password", "Email", "RoleId", "AirportId"},
                new String[]{"user2", "Welkom01.", "user1@gmail.com", "2", "1"});

        // Insert Dummy passengers
        repo.executeInsert("passenger", new String[]{"Firstname", "Lastname", "Email", "Phone", "AddressId"}, new String[]{"Peter", "Pepernoot", "Pepernootje@gmail.com", "0612345678", "1"});
        repo.executeInsert("passenger", new String[]{"Firstname", "Lastname", "Email", "Phone", "AddressId"}, new String[]{"Ricardo", "Spanjaardo", "Spanjaard@gmail.com", "0612345678", "2"});

        // Insert Dummy luggages
        repo.executeInsert("luggage", new String[]{"Destination", "LabelNumber", "FlightNumber", "WFCode","TypeOfLuggage","Brand", "Colour", "Remarks", "PassengerId", "AirportId", "StatusId"},
                new String[]{"Spanje", "AB2645", "4563", "7534DF", "Tas", "merk", "kleur", "Een scheur bij hendel", "2", "2", "1"});
        repo.executeInsert("luggage", new String[]{"Destination", "LabelNumber", "FlightNumber", "WFCode", "TypeOfLuggage","Brand", "Colour", "Remarks","PassengerId", "AirportId", "StatusId"},
                new String[]{"Amsterdam", "AB2645", "4563", "7534DF", "Koffer","merk", "kleur", "Een paar duekjes", "1","1", "2"});
        
        // Insert dummy roles
        repo.executeInsert("role", new String[]{"Name"}, new String[]{"Medewerker"});
        repo.executeInsert("role", new String[]{"Name"}, new String[]{"Administratie"});

        // Insert dummy airports
        repo.executeInsert("airport", new String[]{"IATACode", "Name", "Country"}, new String[]{"SCHIP", "Schiphol", "Amsterdam"});
        repo.executeInsert("airport", new String[]{"IATACode", "Name", "Country"}, new String[]{"BARCE", "Barcelona Airport", "Barcelona"});
    }

    public static void createDummy() {
        // use sys db to create new db
        Repository repo = new Repository("sys");
        int result = repo.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + DB_DEFAULT_DATABASE);
        repo.close();

        Repository repo2 = new Repository(DB_DEFAULT_DATABASE);
        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Airport ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  IATACode VARCHAR(45) NOT NULL,"
                + "  Name VARCHAR(200) NULL,"
                + "  Country VARCHAR(200) NULL,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC))"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Role ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  Name VARCHAR(200) NULL,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC))"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Address ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  Street VARCHAR(200) NULL,"
                + "  Number INT NOT NULL,"
                + "  Place VARCHAR(200) NULL,"
                + "  PostalCode VARCHAR(25) NOT NULL,"
                + "  Country VARCHAR(200) NULL,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC))"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Status ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  Name VARCHAR(200) NULL,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC))"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS User ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  Username VARCHAR(200) NOT NULL,"
                + "  Password VARCHAR(200) NOT NULL,"
                + "  Email VARCHAR(200) NOT NULL,"
                + "  LastLoginDate DATETIME NULL DEFAULT NULL,"
                + "  RoleId INT NOT NULL,"
                + "  AirportId INT NOT NULL,"
                + "  UpdatedAt DATETIME NULL DEFAULT NULL,"
                + "  CreatedAt DATETIME NULL DEFAULT NULL,"
                + "  DeletedAt DATETIME NULL DEFAULT NULL,"
                + "  IsDeleted BIT NULL DEFAULT 0,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC),"
                + "  UNIQUE INDEX Username_UNIQUE (Username ASC),"
                + "  INDEX UserAirportId_idx (AirportId ASC),"
                + "  INDEX UserRoleId_idx (RoleId ASC),"
                + "  CONSTRAINT UserAirportId"
                + "    FOREIGN KEY (AirportId)"
                + "    REFERENCES Airport (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION,"
                + "  CONSTRAINT UserRoleId"
                + "    FOREIGN KEY (RoleId)"
                + "    REFERENCES Role (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION)"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Passenger ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  Firstname VARCHAR(200) NULL,"
                + "  Insertion VARCHAR(100) NULL,"
                + "  Lastname VARCHAR(200) NULL,"
                + "  Email VARCHAR(200) NULL,"
                + "  Phone BIGINT(50) NULL,"
                + "  AddressId INT NOT NULL,"
                + "  UpdatedAt DATETIME NULL DEFAULT NULL,"
                + "  CreatedAt DATETIME NULL DEFAULT NULL,"
                + "  DeletedAt DATETIME NULL DEFAULT NULL,"
                + "  IsDeleted BIT NULL DEFAULT 0,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC),"
                + "  INDEX PassengerAddressId_idx (AddressId ASC),"
                + "  CONSTRAINT PassengerAddressId"
                + "    FOREIGN KEY (AddressId)"
                + "    REFERENCES Address (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION)"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Luggage ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  Destination VARCHAR(200) NULL,"
                + "  LabelNumber VARCHAR(200) NULL,"
                + "  FlightNumber VARCHAR(200) NULL,"
                + "  WFCode VARCHAR(200) NULL,"
                + "  TypeOfLuggage VARCHAR(200) NULL,"
                + "  Brand VARCHAR(200) NULL,"
                + "  Colour VARCHAR(200) NULL,"
                + "  Remarks VARCHAR(255) NULL,"
                + "  PassengerId INT NULL,"
                + "  AirportId INT NOT NULL,"
                + "  StatusId INT NOT NULL,"
                + "  UpdatedAt DATETIME NULL DEFAULT NULL,"
                + "  CreatedAt DATETIME NULL DEFAULT NULL,"
                + "  DeletedAt DATETIME NULL DEFAULT NULL,"
                + "  IsDeleted BIT NULL DEFAULT 0,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC),"
                + "  INDEX LuggageAirportId_idx (AirportId ASC),"
                + "  INDEX LuggagePassengerId_idx (PassengerId ASC),"
                + "  INDEX LuggageStatusId_idx (StatusId ASC),"
                + "  CONSTRAINT LuggageAirportId"
                + "    FOREIGN KEY (AirportId)"
                + "    REFERENCES Airport (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION,"
                + "  CONSTRAINT LuggagePassengerId"
                + "    FOREIGN KEY (PassengerId)"
                + "    REFERENCES Passenger (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION,"
                + "  CONSTRAINT LuggageStatusId"
                + "    FOREIGN KEY (StatusId)"
                + "    REFERENCES Status (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION)"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS DamageClaim ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  Description VARCHAR(255) NULL,"
                + "  EstimatePrice DOUBLE ZEROFILL NULL,"
                + "  InsuranceCompany VARCHAR(255) NULL,"
                + "  LuggageId INT NOT NULL,"
                + "  UpdatedAt DATETIME NULL DEFAULT NULL,"
                + "  CreatedAt DATETIME NULL DEFAULT NULL,"
                + "  DeletedAt DATETIME NULL DEFAULT NULL,"
                + "  IsDeleted BIT NULL DEFAULT 0,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC),"
                + "  INDEX DamageClaimLuggageId_idx (LuggageId ASC),"
                + "  CONSTRAINT DamageClaimLuggageId"
                + "    FOREIGN KEY (LuggageId)"
                + "    REFERENCES Luggage (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION)"
                + "ENGINE = InnoDB;");

        repo2.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Repatriation ("
                + "  Id INT NOT NULL AUTO_INCREMENT,"
                + "  FromAirport VARCHAR(255) NULL,"
                + "  ToAddress VARCHAR(255) NULL,"
                + "  Transporter VARCHAR(200) NULL,"
                + "  TransporterType VARCHAR(200) NULL,"
                + "  Date DATETIME NULL,"
                + "  StatusId INT NOT NULL,"
                + "  PassengerId INT NOT NULL,"
                + "  LuggageId INT NOT NULL,"
                + "  UpdatedAt DATETIME NULL DEFAULT NULL,"
                + "  CreatedAt DATETIME NULL DEFAULT NULL,"
                + "  DeletedAt DATETIME NULL DEFAULT NULL,"
                + "  IsDeleted BIT NULL DEFAULT 0,"
                + "  PRIMARY KEY (Id),"
                + "  UNIQUE INDEX Id_UNIQUE (Id ASC),"
                + "  INDEX RepatriationLuggageId_idx (LuggageId ASC),"
                + "  INDEX RepatriationStatusId_idx (StatusId ASC),"
                + "  CONSTRAINT RepatriationLuggageId"
                + "    FOREIGN KEY (LuggageId)"
                + "    REFERENCES Luggage (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION,"
                + "  CONSTRAINT RepatriationStatusId"
                + "    FOREIGN KEY (StatusId)"
                + "    REFERENCES Status (Id)"
                + "    ON DELETE NO ACTION"
                + "    ON UPDATE NO ACTION)"
                + "ENGINE = InnoDB;");
    }
}
