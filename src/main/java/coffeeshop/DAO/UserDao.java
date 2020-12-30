package coffeeshop.DAO;

import coffeeshop.DTO.User;
import coffeeshop.Util.Common;
import coffeeshop.Util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao implements GenericDao<User> {

    Connection conn = null;
    CallableStatement cs = null;
    ResultSet rs = null;

    public UserDao(DbUtil dbUtil) {
        conn = dbUtil.getInstance().getConnection();
    }

    public List<User> getAll(User user) {
        List<User> list = new ArrayList<>();
        String sql = "{CALL sp_getAllUser(?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);

            cs.setNull(1, Types.NVARCHAR);
            cs.setNull(2, Types.VARCHAR);
            cs.setNull(3, Types.INTEGER);
            cs.setNull(4, Types.BOOLEAN);

            if (!Common.isNullOrEmpty(user)) {
                if (!Common.isNullOrEmpty(user.getName())) {
                    cs.setNString(1, user.getName());
                }
                if (!Common.isNullOrEmpty(user.getEmail())) {
                    cs.setString(2, user.getEmail());
                }
                if (!Common.isNullOrEmpty(user.getRole())) {
                    cs.setInt(3, user.getRole());
                }
                if (!Common.isNullOrEmpty(user.getStatus())) {
                    cs.setBoolean(4, user.getStatus());
                }
            }
            rs = cs.executeQuery();

            while (rs.next()) {
                User obj = new User(
                        rs.getInt("id"),
                        rs.getNString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getBoolean("status")
                );
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Map<String, Object> create(User user) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertUser(?, ?, ?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setNString(1, user.getName());
            cs.setString(2, user.getEmail());
            cs.setString(3, user.getPassword());
            cs.setInt(4, user.getRole());
            cs.setBoolean(5, user.getStatus());
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
    public User read(int id) {
        User obj = null;
        String sql = "{CALL sp_getUserById(?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, id);
            rs = cs.executeQuery();

            while (rs.next()) {
                obj = new User(
                        rs.getInt("id"),
                        rs.getNString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getBoolean("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(User user) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_updateUser(?, ?, ?, ?, ?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setInt(1, user.getId());
            cs.setNString(2, user.getName());
            cs.setString(3, user.getEmail());
            cs.setString(4, user.getPassword());
            cs.setInt(5, user.getRole());
            cs.setBoolean(6, user.getStatus());
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
    public Map<String, Object> delete(int id) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_deleteUser(?, ?, ?)}";

        try {
            cs = conn.prepareCall(sql);
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

    public User auth(String email, String password) {
        User obj = null;
        String sql = "{CALL sp_checkUser(?, ?)}";

        try {
            cs = conn.prepareCall(sql);
            cs.setString(1, email);
            cs.setString(2, password);
            rs = cs.executeQuery();

            while (rs.next()) {
                obj = new User(
                        rs.getInt("id"),
                        rs.getNString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getBoolean("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
