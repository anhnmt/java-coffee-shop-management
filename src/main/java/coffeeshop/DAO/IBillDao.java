package coffeeshop.DAO;

import coffeeshop.DTO.Bill;

import java.util.List;
import java.util.Map;

public interface IBillDao {

    public int count();

    public List<Bill> getAll(Bill bill);

    public Map<String, Object> create(Bill bill);

    public Bill read(int id);

    public Map<String, Object> update(Bill bill);

    public Map<String, Object> delete(int id);

    public Bill getByTableId(Bill bill);
}
