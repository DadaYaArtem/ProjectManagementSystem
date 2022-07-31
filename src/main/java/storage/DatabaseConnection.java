package storage;

import prefs.Prefs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  
    private static Connection con = null;
  
    static
    {
        String url = new Prefs().getString(Prefs.URL);
        String user = new Prefs().getString(Prefs.USERNAME);
        String pass = new Prefs().getString(Prefs.PASSWORD);
        try {
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return con;
    }
}