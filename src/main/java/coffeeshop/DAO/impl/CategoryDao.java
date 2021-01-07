package coffeeshop.DAO.impl;

import coffeeshop.DAO.ICategoryDao;
import coffeeshop.DTO.Category;
import coffeeshop.Util.*;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class CategoryDao implements ICategoryDao {

    Connection conn = null;
    CallableStatement cs = null;
    ResultSet rs = null;
    private BaseMessage response;

    public CategoryDao(DbUtil dbUtil) {
        conn = dbUtil.getInstance().getConnection();
    }

    @Override
    public int count() {
        int count = 0;
        String sql = "{CALL sp_countCategories}";

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
    public List<Category> getAll(Category category) {
        List<Category> list = new ArrayList<>();

        String sql = "{CALL sp_getAllCategory(?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setNull(1, Types.NVARCHAR);
            cs.setNull(2, Types.BOOLEAN);

            if (!Common.isNullOrEmpty(category)) {
                if (!Common.isNullOrEmpty(category.getName())) {
                    cs.setNString(1, category.getName());
                }
                if (!Common.isNullOrEmpty(category.getStatus())) {
                    cs.setBoolean(2, category.getStatus());
                }
            }
            rs = cs.executeQuery();

            while (rs.next()) {
                Category obj = new Category(
                        rs.getInt("id"),
                        rs.getNString("name"),
                        rs.getBoolean("status")
                );
                list.add(obj);
            }

            response = new MessageResponse<>(Constant.SUCCESS_RESPONSE, "Thành công", list);
            log.info(Common.createMessageLog(category, response, "getAll"));
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(category, response, "getAll"));
        } finally {
            rs = null;
            cs = null;
        }

        return list;
    }

    @Override
    public Map<String, Object> create(Category category) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertCategory(?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setNString(1, category.getName());
            cs.setBoolean(2, category.getStatus());
            cs.registerOutParameter(3, Types.BIT);
            cs.registerOutParameter(4, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(3));
            output.put("message", cs.getNString(4));

            response = new MessageResponse<>(cs.getBoolean(3), cs.getNString(4), output);
            if (cs.getBoolean(3)) {
                log.info(Common.createMessageLog(category, response, "create"));
            } else {
                log.error(Common.createMessageLog(category, response, "create"));
            }
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(category, response, "create"));
        } finally {
            cs = null;
        }

        return output;
    }

    @Override
    public Category read(int id) {
        Category obj = null;
        String sql = "SELECT * FROM Categories WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                obj = new Category(
                        rs.getInt("id"),
                        rs.getNString("name"),
                        rs.getBoolean("status")
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
    public Map<String, Object> update(Category category) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_updateCategory(?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, category.getId());
            cs.setNString(2, category.getName());
            cs.setBoolean(3, category.getStatus());
            cs.registerOutParameter(4, Types.BIT);
            cs.registerOutParameter(5, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(4));
            output.put("message", cs.getNString(5));

            response = new MessageResponse<>(cs.getBoolean(4), cs.getNString(5), output);
            if (cs.getBoolean(4)) {
                log.info(Common.createMessageLog(category, response, "update"));
            } else {
                log.error(Common.createMessageLog(category, response, "update"));
            }
        } catch (SQLException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(category, response, "update"));
        } finally {
            cs = null;
        }

        return output;
    }

    @Override
    public Map<String, Object> delete(int id) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_deleteCategory(?, ?, ?)}";

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
