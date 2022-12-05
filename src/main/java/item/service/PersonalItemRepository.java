package item.service;

import item.model.ModelId;
import item.model.PersonalItem;
import shared.service.Repository;

import java.util.List;

public interface PersonalItemRepository<T extends PersonalItem> extends Repository<T> {
    List<T> all();
    ModelId add(T object);
    T get(Long id);
    Boolean update(T object);
}
