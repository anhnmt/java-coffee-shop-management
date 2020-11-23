package com.example.controller;

import com.example.dao.DbUtil;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MainController {

    public void index() {
        try (Connection conn = new DbUtil().getInstance().getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối thành công");

                LoginController loginController = new LoginController();
                loginController.index();
            } else {
                System.out.println("Kết nối không thành công");
                JOptionPane.showMessageDialog(null, "Kết nối CSDL không thành công.", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
