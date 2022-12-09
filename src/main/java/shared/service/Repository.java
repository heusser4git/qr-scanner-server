package shared.service;

import java.util.List;

public interface Repository<T> {
    List<T> all();
    T add(T object);
    T get(Long id);
    Boolean update(T object);
    Boolean delete(Long id);
}
