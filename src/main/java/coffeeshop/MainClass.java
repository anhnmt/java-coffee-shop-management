package coffeeshop;

import coffeeshop.GUI.home.Dashboard;
import coffeeshop.Util.Common;
import coffeeshop.Util.DbUtil;
import java.awt.HeadlessException;

import javax.swing.*;
import java.sql.Connection;
import lombok.extern.log4j.Log4j;

@Log4j
public class MainClass {

    public static void main(String[] args) {
        try {
            DbUtil dbUtil = new DbUtil();
            Connection conn = dbUtil.getInstance().getConnection();

            if (Common.isNullOrEmpty(conn)) {
                System.out.println("Kết nối không thành công");
                JOptionPane.showMessageDialog(null, "Kết nối CSDL không thành công.", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                System.out.println("Kết nối thành công");

                Dashboard dashboard = new Dashboard(dbUtil);
                dashboard.setVisible(true);
            }
        } catch (HeadlessException e) {
            log.error(e.getMessage());
        }
    }
}
