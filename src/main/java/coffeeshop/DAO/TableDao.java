package coffeeshop.DAO;

import coffeeshop.DTO.Table;
import coffeeshop.Utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDao implements GenericDao<Table> {

    @Override
    public List<Table> getAll() {
        List<Table> list = new ArrayList<>();
        String sql = "{CALL sp_getAllTable}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Table obj = new Table(
                        rs.getInt("id"),
                        rs.getInt("area_id"),
                        rs.getNString("name"),
                        rs.getNString("note"),
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
    public Map<String, Object> create(Table table) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertTable(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, table.getArea_id());
            cs.setNString(2, table.getName());
            cs.setNString(3, table.getNote());
            cs.setBoolean(4, table.isStatus());
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
    public Table read(int id) {
        Table obj = null;
        String sql = "SELECT * FROM Tables WHERE id = ?";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    obj = new Table(
                            rs.getInt("id"),
                            rs.getInt("area_id"),
                            rs.getNString("name"),
                            rs.getNString("note"),
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public Map<String, Object> update(Table table) {
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        return null;
    }
}
