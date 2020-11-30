package coffeeshop.Utils;

import coffeeshop.DAO.UserDao;
import coffeeshop.DTO.User;
import coffeeshop.GUI.FrmLogin;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) {
        try (Connection conn = new DbUtil().getInstance().getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối thành công");

                FrmLogin frmLogin = new FrmLogin();
                frmLogin.setVisible(true);
//                UserDao ud = new UserDao();
//                Map<String, Object> res = ud.create(new User(
//                        0,
//                        "Nguyễn Tuấn Minh",
//                        "minh@gmail.com",
//                        "1230123",
//                        0,
//                        true
//                ));
//
//                System.out.println("Status: " + res.get("status"));
//                System.out.println("Message: " + res.get("message"));
            } else {
                System.out.println("Kết nối không thành công");
                JOptionPane.showMessageDialog(null, "Kết nối CSDL không thành công.", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
