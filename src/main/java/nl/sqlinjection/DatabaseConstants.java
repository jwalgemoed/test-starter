package nl.sqlinjection;

import com.sun.rowset.CachedRowSetImpl;

import java.net.URL;
import java.sql.*;

public class DatabaseConstants {

    // Change hostname/port and username/password to your own database instance
    public static final String connectionUrl = "jdbc:mysql://HOSTNAME:PORT/TEST?allowMultiQueries=true";
    public static final String password = "USER";
    public static final String username = "PASS";
    
}
