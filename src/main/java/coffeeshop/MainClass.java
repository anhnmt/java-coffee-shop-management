package coffeeshop;

import coffeeshop.GUI.home.Dashboard;
import coffeeshop.Util.Common;
import coffeeshop.Util.Constant;
import coffeeshop.Util.BaseMessage;
import coffeeshop.Util.DbUtil;
import java.awt.HeadlessException;

import javax.swing.*;
import java.sql.Connection;
import lombok.extern.log4j.Log4j;

@Log4j
public class MainClass {

    private BaseMessage response;

    public MainClass() {
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
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "main"));
        }
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
    }
}
