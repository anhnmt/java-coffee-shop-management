package coffeeshop.Util;

import coffeeshop.GUI.Dashboard;

import javax.swing.*;
import java.sql.Connection;

public class MainClass {

    public static void main(String[] args) {
        try {
            DbUtil dbUtil = new DbUtil();
            Connection conn = dbUtil.getInstance().getConnection();

            if (Common.isNullOrEmpty(conn)) {
                System.out.println("Kết nối không thành công");
                JOptionPane.showMessageDialog(null, "Kết nối CSDL không thành công.", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Kết nối thành công");

                Dashboard dashboard = new Dashboard(dbUtil);
                dashboard.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
