package coffeeshop.DAO;

import coffeeshop.DTO.Product;
import java.util.List;
import java.util.Map;

public interface IProductDao {

    public List<Product> getAll(Product product, Float fromPrice, Float toPrice);

    public Map<String, Object> create(Product product);

    public Product read(int id);

    public Map<String, Object> update(Product product);

    public Map<String, Object> delete(int id);
}
