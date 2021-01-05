package coffeeshop.DAO.impl;

import coffeeshop.DAO.*;
import coffeeshop.DTO.Bill;
import coffeeshop.Util.Common;
import coffeeshop.Util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j;

@Log4j
public class BillDao implements IBillDao {

    Connection conn = null;
    CallableStatement cs = null;
    ResultSet rs = null;

    public BillDao(DbUtil dbUtil) {
        conn = dbUtil.getInstance().getConnection();
    }

    @Override
    public List<Bill> getAll(Bill bill) {
        List<Bill> list = new ArrayList<>();
        String sql = "{CALL sp_getAllBill(?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setNull(1, Types.INTEGER);
            cs.setNull(2, Types.INTEGER);
            cs.setNull(3, Types.INTEGER);
            cs.setNull(4, Types.BOOLEAN);

            if (!Common.isNullOrEmpty(bill)) {
                if (!Common.isNullOrEmpty(bill.getId())) {
                    cs.setInt(1, bill.getId());
                }
                if (!Common.isNullOrEmpty(bill.getUser_id())) {
                    cs.setInt(2, bill.getUser_id());
                }
                if (!Common.isNullOrEmpty(bill.getTable_id())) {
                    cs.setInt(3, bill.getTable_id());
                }
                if (!Common.isNullOrEmpty(bill.getStatus())) {
                    cs.setBoolean(4, bill.getStatus());
                }
            }
            rs = cs.executeQuery();

            while (rs.next()) {
                Bill obj = new Bill(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        rs.getFloat("total_price"),
                        rs.getFloat("discount"),
                        rs.getBoolean("status"),
                        rs.getString("created_at"),
                        rs.getNString("user_name"),
                        rs.getNString("table_name")
                );
                list.add(obj);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            rs = null;
            cs = null;
        }

        return list;
    }

    @Override
    public Map<String, Object> create(Bill bill) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertBill(?, ?, ?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setNull(1, Types.INTEGER);
            cs.setNull(2, Types.INTEGER);
            cs.setNull(3, Types.FLOAT);
            cs.setNull(4, Types.FLOAT);
            cs.setNull(5, Types.BOOLEAN);

            if (!Common.isNullOrEmpty(bill)) {
                if (!Common.isNullOrEmpty(bill.getUser_id())) {
                    cs.setInt(1, bill.getUser_id());
                }
                if (!Common.isNullOrEmpty(bill.getTable_id())) {
                    cs.setInt(2, bill.getTable_id());
                }
                if (!Common.isNullOrEmpty(bill.getTotal_price())) {
                    cs.setFloat(3, bill.getTotal_price());
                }
                if (!Common.isNullOrEmpty(bill.getDiscount())) {
                    cs.setFloat(4, bill.getDiscount());
                }
                if (!Common.isNullOrEmpty(bill.getStatus())) {
                    cs.setBoolean(5, bill.getStatus());
                }
            }
            cs.registerOutParameter(6, Types.BIT);
            cs.registerOutParameter(7, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(6));
            output.put("message", cs.getNString(7));
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            cs = null;
        }

        return output;
    }

    @Override
    public Bill read(int id) {
        Bill obj = null;
        String sql = "{CALL sp_getBillById(?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            rs = cs.executeQuery();

            while (rs.next()) {
                obj = new Bill(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        rs.getFloat("total_price"),
                        rs.getFloat("discount"),
                        rs.getBoolean("status"),
                        rs.getString("created_at"),
                        rs.getNString("user_name"),
                        rs.getNString("table_name")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            rs = null;
            cs = null;
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(Bill bill) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_updateBill(?, ?, ?, ?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, bill.getId());
            cs.setInt(2, bill.getUser_id());
            cs.setInt(3, bill.getTable_id());
            cs.setFloat(4, bill.getTotal_price());
            cs.setFloat(5, bill.getDiscount());
            cs.setBoolean(6, bill.getStatus());
            cs.registerOutParameter(7, Types.BIT);
            cs.registerOutParameter(8, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(7));
            output.put("message", cs.getNString(8));
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            cs = null;
        }

        return output;
    }

    @Override
    public Map<String, Object> delete(int id) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_deleteBill(?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.BIT);
            cs.registerOutParameter(3, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(2));
            output.put("message", cs.getNString(3));
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            cs = null;
        }

        return output;
    }

    @Override
    public Bill getByTableId(Bill bill) {
        Bill obj = null;
        String sql = "{CALL sp_getBillByTableId(?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, bill.getTable_id());

            if (Common.isNullOrEmpty(bill.getStatus())) {
                cs.setNull(2, Types.BOOLEAN);
            } else {
                cs.setBoolean(2, bill.getStatus());
            }

            rs = cs.executeQuery();
            while (rs.next()) {
                obj = new Bill(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        rs.getFloat("total_price"),
                        rs.getFloat("discount"),
                        rs.getBoolean("status"),
                        rs.getString("created_at"),
                        rs.getNString("user_name"),
                        rs.getNString("table_name")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            rs = null;
            cs = null;
        }

        return obj;
    }
}
