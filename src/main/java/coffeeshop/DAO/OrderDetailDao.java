package coffeeshop.DAO;

import coffeeshop.DTO.OrderDetail;
import coffeeshop.Utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailDao implements GenericDao<OrderDetail> {

    @Override
    public List<OrderDetail> getAll() {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderDetail obj = new OrderDetail(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("amount")
                );
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Map<String, Object> create(OrderDetail orderDetail) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertOrderDetail(?, ?, ?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, orderDetail.getOrder_id());
            cs.setInt(2, orderDetail.getProduct_id());
            cs.setInt(3, orderDetail.getAmount());
            cs.registerOutParameter(4, Types.BIT);
            cs.registerOutParameter(5, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(4));
            output.put("message", cs.getNString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public OrderDetail read(int id) {
        OrderDetail obj = null;
        String sql = "SELECT * FROM OrderDetails WHERE order_id = ?";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    obj = new OrderDetail(
                            rs.getInt("order_id"),
                            rs.getInt("product_id"),
                            rs.getInt("amount")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(OrderDetail user) {
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        return null;
    }
}
