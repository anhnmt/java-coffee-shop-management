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
        String sql = "{CALL sp_getAllProduct}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Product obj = new Product(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getNString("name"),
                        rs.getFloat("price"),
                        rs.getBoolean("status"),
                        rs.getString("category_name")
                );
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Map<String, Object> create(Product product) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertProduct(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, product.getCategory_id());
            cs.setNString(2, product.getName());
            cs.setFloat(3, product.getPrice());
            cs.setBoolean(4, product.isStatus());
            cs.registerOutParameter(5, Types.BIT);
            cs.registerOutParameter(6, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(5));
            output.put("message", cs.getNString(6));
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
                            null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(Product product) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_updateProduct(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, product.getId());
            cs.setInt(2, product.getCategory_id());
            cs.setNString(3, product.getName());
            cs.setFloat(4, product.getPrice());
            cs.setBoolean(5, product.isStatus());
            cs.registerOutParameter(6, Types.BIT);
            cs.registerOutParameter(7, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(6));
            output.put("message", cs.getNString(7));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public Map<String, Object> delete(int id) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_deleteProduct(?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.BIT);
            cs.registerOutParameter(3, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(2));
            output.put("message", cs.getNString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }
}