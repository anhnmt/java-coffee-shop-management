package coffeeshop;

import coffeeshop.GUI.home.Dashboard;
import coffeeshop.Util.BaseMessage;
import coffeeshop.Util.Common;
import coffeeshop.Util.Constant;
import coffeeshop.Util.DbUtil;
import lombok.extern.log4j.Log4j;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

@Log4j
public class MainClass{

    public static void main(String[] args) {
        BaseMessage response;
        try {
            DbUtil dbUtil = new DbUtil();
            Connection conn = dbUtil.getInstance().getConnection();

            if (Common.isNullOrEmpty(conn)) {
                JOptionPane.showMessageDialog(null, "Kết nối CSDL không thành công.", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);

                response = new BaseMessage(Constant.ERROR_RESPONSE, "Kết nối không thành công");
                log.error(Common.createMessageLog(null, response, "main"));
                System.exit(0);
            } else {
                response = new BaseMessage(Constant.SUCCESS_RESPONSE, "Kết nối thành công");
                log.info(Common.createMessageLog(null, response, "main"));

                Dashboard dashboard = new Dashboard(dbUtil);
                dashboard.setVisible(true);
            }
        } catch (HeadlessException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "main"));
        }
    }
}
