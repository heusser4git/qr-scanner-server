package shared.service;

import item.model.ModelId;

import java.util.List;

public interface Repository<T> {
    List<T> all();
    T add(T object);
    T get(Long id);
    Boolean update(T object);
    Boolean delete(T object);
}
