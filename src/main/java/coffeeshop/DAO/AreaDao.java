package coffeeshop.DAO;

import coffeeshop.DTO.Area;
import coffeeshop.Utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaDao implements GenericDao<Area> {
    @Override
    public List<Area> getAll() {
        List<Area> list = new ArrayList<>();
        String sql = "{CALL sp_getAllArea}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Area obj = new Area(
                        rs.getInt("id"),
                        rs.getNString("name"),
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
    public Map<String, Object> create(Area area) {
        Map<String, Object> output = new HashMap<>();
        String sql = "{CALL sp_insertArea(?, ?, ?, ?)}";

        try (Connection conn = new DbUtil().getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setNString(1, area.getName());
            cs.setBoolean(2, area.isStatus());
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
    public Area read(int id) {
        Area obj = null;
        String sql = "SELECT * FROM Areas WHERE id = ?";

        try (Connection conn = new DbUtil().getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    obj = new Area(
                            rs.getInt("id"),
                            rs.getNString("name"),
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
    public Map<String, Object> update(Area area) {
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        return null;
    }
}
