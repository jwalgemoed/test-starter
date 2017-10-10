package nl.sqlinjection;

import com.sun.rowset.CachedRowSetImpl;

import java.net.URL;
import java.sql.*;

public class DatabaseConstants {

    // Change hostname/port and username/password to your own database instance
    public static final String connectionUrl = "jdbc:mysql://localhost:3306/user?allowMultiQueries=true";
    public static final String password = "secret";
    public static final String username = "root";
    
}
