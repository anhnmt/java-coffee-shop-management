package coffeeshop.DAO;

import coffeeshop.DTO.Area;

import java.util.List;
import java.util.Map;

public interface IAreaDao {

    public int count();

    public List<Area> getAll();

    public Map<String, Object> create(Area area);

    public Area findByName(String name);

    public Map<String, Object> update(Area area);

    public Map<String, Object> delete(int id);

    public Area read(int id);
}
