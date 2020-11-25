package com.example.controller;

import com.example.dao.DbUtil;
import com.example.view.FrmLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class LoginController {

    public void index() {
        FrmLogin frmDangNhap = new FrmLogin();
        frmDangNhap.setVisible(true);
    }

    public void store(String email, String password) {
        String sql = "SELECT * FROM NguoiDung WHERE Email = ? AND MatKhau = ?";

        try (
                Connection conn = new DbUtil().getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setNString(1, email);
            ps.setNString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Email hoặc Mật khẩu không khớp.", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
