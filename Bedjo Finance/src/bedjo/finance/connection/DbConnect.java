/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bedjo.finance.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class DbConnect {

    static Connection c = null;

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data/data.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return c;
    }
}
