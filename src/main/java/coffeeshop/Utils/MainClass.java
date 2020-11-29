package coffeeshop.Utils;

//import com.example.GUI.FrmLogin;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) {
        try (Connection conn = new DbUtil().getInstance().getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối thành công");

//                FrmLogin frmLogin = new FrmLogin();
//                frmLogin.setVisible(true);
            } else {
                System.out.println("Kết nối không thành công");
                JOptionPane.showMessageDialog(null, "Kết nối CSDL không thành công.", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
