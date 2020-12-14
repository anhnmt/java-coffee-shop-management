package coffeeshop.DAO;

import coffeeshop.DTO.Product;
import coffeeshop.Utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDao implements GenericDao<Product> {

    @Override
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product obj = new Product(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getNString("name"),
                        rs.getFloat("price"),
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
    public Map<String, Object> create(Product user) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertCategory(?, ?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setNString(1, user.getName());
            cs.setBoolean(2, user.isStatus());
            cs.registerOutParameter(3, Types.BIT);
            cs.registerOutParameter(4, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(3));
            output.put("message", cs.getNString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public Product read(int id) {
        Product obj = null;
        String sql = "SELECT * FROM Categories WHERE id = ?";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    obj = new Product(
                            rs.getInt("id"),
                            rs.getInt("category_id"),
                            rs.getNString("name"),
                            rs.getFloat("price"),
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
    public Map<String, Object> update(Product user) {
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        return null;
    }
}
