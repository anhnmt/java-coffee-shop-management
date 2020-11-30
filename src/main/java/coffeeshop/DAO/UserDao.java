package coffeeshop.DAO;

import coffeeshop.DTO.User;
import coffeeshop.Utils.DbUtil;

import java.sql.*;
import java.util.*;

public class UserDao implements GenericDao<User> {
    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<User>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = new DbUtil().getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                User obj = new User(
                        rs.getInt("id"),
                        rs.getNString("name"),
                        rs.getNString("email"),
                        rs.getNString("password"),
                        rs.getInt("role"),
                        rs.getBoolean("status")
                );
                list.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    @Override
    public Map<String, Object> create(User user) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertUser(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection();
             CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setNString(1, user.getName());
            cs.setString(2, user.getEmail());
            cs.setString(3, user.getPassword());
            cs.setInt(4, user.getRole());
            cs.setBoolean(5, user.isStatus());
            cs.registerOutParameter(6, Types.BIT);
            cs.registerOutParameter(7, Types.NVARCHAR);
            cs.execute();

            output.put("status", cs.getBoolean(6));
            output.put("message", cs.getNString(7));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return output;
    }

    @Override
    public User read(int id) {
        User obj = null;
        String sql = "SELECT * FROM Users WHERE id = ?";

        try (Connection conn = new DbUtil().getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    obj = new User(
                            rs.getInt("id"),
                            rs.getNString("name"),
                            rs.getNString("email"),
                            rs.getNString("password"),
                            rs.getInt("role"),
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(User user) {
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        return null;
    }
}
