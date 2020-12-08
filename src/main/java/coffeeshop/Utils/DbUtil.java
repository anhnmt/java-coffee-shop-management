package coffeeshop.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbUtil {
    private static DbUtil instance;
    private static Connection conn;

    public DbUtil() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("coffeeshop.config.config");
            Class.forName(bundle.getString("DRIVER_NAME"));

            conn = DriverManager.getConnection(bundle.getString("URL"), bundle.getString("USER"), bundle.getString("PASSWORD"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public DbUtil getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DbUtil();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
