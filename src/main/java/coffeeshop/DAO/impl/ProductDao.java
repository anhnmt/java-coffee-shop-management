package coffeeshop.DAO.impl;

import coffeeshop.DAO.IProductDao;
import coffeeshop.DTO.Product;
import coffeeshop.Util.*;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class ProductDao implements IProductDao {

    Connection conn = null;
    CallableStatement cs = null;
    ResultSet rs = null;
    private BaseMessage response;

    public ProductDao(DbUtil dbUtil) {
        conn = dbUtil.getInstance().getConnection();
    }

    @Override
    public int count() {
        int count = 0;
        String sql = "{CALL sp_countProducts}";

        try {
            cs = conn.prepareCall(sql);
            rs = cs.executeQuery();

            while (rs.next()) {
                count = rs.getInt("count");
            }

            response = new MessageResponse<>(Constant.SUCCESS_RESPONSE, "Thành công", count);
            log.info(Common.createMessageLog(null, response, "count"));
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "count"));
        } finally {
            rs = null;
            cs = null;
        }

        return count;
    }

    @Override
    public List<Product> getAll(Product product, Float fromPrice, Float toPrice) {
        List<Product> list = new ArrayList<>();
        String sql = "{CALL sp_getAllProduct(?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setNull(1, Types.NVARCHAR);
            cs.setNull(2, Types.INTEGER);
            cs.setNull(3, Types.FLOAT);
            cs.setNull(4, Types.FLOAT);
            cs.setNull(5, Types.BOOLEAN);

            if (!Common.isNullOrEmpty(product)) {
                if (!Common.isNullOrEmpty(product.getName())) {
                    cs.setNString(1, product.getName());
                }
                if (!Common.isNullOrEmpty(product.getCategory_id())) {
                    cs.setInt(2, product.getCategory_id());
                }
                if (!Common.isNullOrEmpty(fromPrice)) {
                    cs.setFloat(3, fromPrice);
                }
                if (!Common.isNullOrEmpty(toPrice)) {
                    cs.setFloat(4, toPrice);
                }
                if (!Common.isNullOrEmpty(product.getStatus())) {
                    cs.setBoolean(5, product.getStatus());
                }
            }
            rs = cs.executeQuery();

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

            response = new MessageResponse<>(Constant.SUCCESS_RESPONSE, "Thành công", list);
            log.info(Common.createMessageLog(new Object[]{product, fromPrice, toPrice}, response, "getAll"));
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(new Object[]{product, fromPrice, toPrice}, response, "getAll"));
        } finally {
            rs = null;
            cs = null;
        }

        return list;
    }

    @Override
    public Map<String, Object> create(Product product) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertProduct(?, ?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, product.getCategory_id());
            cs.setNString(2, product.getName());
            cs.setFloat(3, product.getPrice());
            cs.setBoolean(4, product.getStatus());
            cs.registerOutParameter(5, Types.BIT);
            cs.registerOutParameter(6, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(5));
            output.put("message", cs.getNString(6));

            response = new MessageResponse<>(cs.getBoolean(5), cs.getNString(6), output);
            if (cs.getBoolean(5)) {
                log.info(Common.createMessageLog(product, response, "create"));
            } else {
                log.error(Common.createMessageLog(product, response, "create"));
            }
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(product, response, "create"));
        } finally {
            cs = null;
        }

        return output;
    }

    @Override
    public Product read(int id) {
        Product obj = null;
        String sql = "{CALL sp_getProductById(?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            rs = cs.executeQuery();

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

            response = new MessageResponse<>(Constant.SUCCESS_RESPONSE, "Thành công", obj);
            log.info(Common.createMessageLog(id, response, "read"));
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(id, response, "read"));
        } finally {
            rs = null;
            cs = null;
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(Product product) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_updateProduct(?, ?, ?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, product.getId());
            cs.setInt(2, product.getCategory_id());
            cs.setNString(3, product.getName());
            cs.setFloat(4, product.getPrice());
            cs.setBoolean(5, product.getStatus());
            cs.registerOutParameter(6, Types.BIT);
            cs.registerOutParameter(7, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(6));
            output.put("message", cs.getNString(7));

            response = new MessageResponse<>(cs.getBoolean(6), cs.getNString(7), output);
            if (cs.getBoolean(6)) {
                log.info(Common.createMessageLog(product, response, "update"));
            } else {
                log.error(Common.createMessageLog(product, response, "update"));
            }
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(product, response, "update"));
        } finally {
            cs = null;
        }

        return output;
    }

    @Override
    public Map<String, Object> delete(int id) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_deleteProduct(?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.BIT);
            cs.registerOutParameter(3, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(2));
            output.put("message", cs.getNString(3));

            response = new MessageResponse<>(cs.getBoolean(2), cs.getNString(3), output);
            if (cs.getBoolean(2)) {
                log.info(Common.createMessageLog(id, response, "delete"));
            } else {
                log.error(Common.createMessageLog(id, response, "delete"));
            }
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(id, response, "delete"));
        } finally {
            cs = null;
        }

        return output;
    }
}
