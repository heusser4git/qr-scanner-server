package shared.service;

import item.model.ModelId;

import java.util.List;

public interface Repository<T> {
    List<T> all();
    ModelId add(T object);
    T get(Long id);
    Boolean update(T object);
}
