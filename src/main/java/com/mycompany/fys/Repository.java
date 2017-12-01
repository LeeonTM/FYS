package com.mycompany.fys;

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

    //constructor
    public Repository() {
        this(DB_DEFAULT_DATABASE, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
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

    public static void createDatabase() {
        // Execute create script
    }

    /**
     * *
     * Executes a DDL, DML or DCL query that does not yield a result set
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
     * *
     * Executes a DDL, DML or DCL insert query that does not yield a result set
     *
     * @param tableName the full name of the table you wish to insert into.
     * @param values the values you wish to insert into the table.
     * @return the number of rows that have been impacted, -1 on error
     */
    public int executeInsert(String tableName, String[] values) {
        try {
            Statement s = this.connection.createStatement();
            String valuesTotal = "";

            // Add each value to a total string of values to insert
            for (String value : values) {
                if (value == values[values.length - 1]) {
                    valuesTotal += "'" + value + "'";
                } else {
                    valuesTotal += "'" + value + "', ";

                }
            }

            String totalQuery = "INSERT INTO " + tableName + " VALUES(" + valuesTotal + ")";

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

    public boolean executeSelect(String tableName) {
        try {
            Statement s = this.connection.createStatement();
            String totalQuery = "SELECT * FROM " + tableName;
            log(totalQuery);

            // Print out the result
            ResultSet result = s.executeQuery(totalQuery);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print(",  ");
                    }
                    String columnValue = result.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            boolean n = s.execute(totalQuery);
            s.close();
            return (n);
        } catch (SQLException ex) {
            //handle exception
            error(ex);
            return false;
        }
    }

    public boolean executeSelect(String tableName, String[] whereColumns, String[] whereValues) {
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
            
            // Print out the result
            ResultSet result = s.executeQuery(totalQuery);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print(",  ");
                    }
                    String columnValue = result.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            boolean n = s.execute(totalQuery);
            s.close();
            return (n);
        } catch (SQLException ex) {
            //handle exception
            error(ex);
            return false;
        }
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
}
