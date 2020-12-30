package coffeeshop.DAO;

import java.util.Map;

public interface GenericDao<T> {

//    List<T> getAll();
    Map<String, Object> create(T t);

    T read(int id);

    Map<String, Object> update(T t);

    Map<String, Object> delete(int id);
}
