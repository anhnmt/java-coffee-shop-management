import dao.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class MainClass {
    public MainClass() {

    }

    public static void main(String[] args) {
        try (Connection conn = new DbUtil().getInstance().getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối thành công");
            } else {
                System.out.println("Kết nối không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
