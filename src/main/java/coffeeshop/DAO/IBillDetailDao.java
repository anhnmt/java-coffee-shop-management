package coffeeshop.DAO;

import coffeeshop.DTO.BillDetail;

import java.util.List;
import java.util.Map;

public interface IBillDetailDao {

    public List<BillDetail> getAll(int bill_id);

    public Map<String, Object> create(BillDetail billDetail);

    public BillDetail read(int id);

    public Map<String, Object> update(BillDetail billDetail);

    public Map<String, Object> delete(BillDetail billDetail);
}
