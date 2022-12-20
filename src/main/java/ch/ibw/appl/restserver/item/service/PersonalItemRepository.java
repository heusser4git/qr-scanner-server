package ch.ibw.appl.restserver.item.service;

import ch.ibw.appl.restserver.item.model.PersonalItem;
import ch.ibw.appl.restserver.item.shared.service.Repository;

import java.util.List;

public interface PersonalItemRepository<T extends PersonalItem> extends Repository<T> {
    List<T> all();
    T add(T object);
    T get(Long id);
    Boolean update(T object);
    Boolean delete(Long id);
}
