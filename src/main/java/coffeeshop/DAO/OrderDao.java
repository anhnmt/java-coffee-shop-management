package coffeeshop.DAO;

import coffeeshop.DTO.Order;
import coffeeshop.Utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao implements GenericDao<Order> {

    @Override
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order obj = new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("table_id"),
                        rs.getFloat("total_price"),
                        rs.getFloat("discount"),
                        rs.getNString("note"),
                        rs.getBoolean("status"),
                        rs.getString("created_at")
                );
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Map<String, Object> create(Order order) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertOrder(?, ?, ?, ?, ?, ?, ?, ?,)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, order.getUser_id());
            cs.setInt(2, order.getTable_id());
            cs.setFloat(3, order.getTotal_price());
            cs.setFloat(4, order.getDiscount());
            cs.setNString(5, order.getNote());
            cs.setBoolean(6, order.isStatus());
            cs.registerOutParameter(7, Types.BIT);
            cs.registerOutParameter(8, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(7));
            output.put("message", cs.getNString(8));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public Order read(int id) {
        Order obj = null;
        String sql = "SELECT * FROM Orders WHERE id = ?";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    obj = new Order(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("table_id"),
                            rs.getFloat("total_price"),
                            rs.getFloat("discount"),
                            rs.getNString("note"),
                            rs.getBoolean("status"),
                            rs.getString("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(Order user) {
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        return null;
    }
}
