package coffeeshop.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import lombok.extern.log4j.Log4j;

@Log4j
public class DbUtil {

    private static DbUtil instance;
    private static Connection conn;
    private BaseMessage response;

    public DbUtil() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            Class.forName(bundle.getString("DRIVER_NAME"));

            conn = DriverManager.getConnection(bundle.getString("URL"), bundle.getString("USER"), bundle.getString("PASSWORD"));
        } catch (ClassNotFoundException | SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "DbUtil"));
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
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "getInstance"));
        }

        return instance;
    }
}
