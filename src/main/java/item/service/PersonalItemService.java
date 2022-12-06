package item.service;

import item.model.ModelId;
import item.model.PersonalItem;

import java.util.List;

public class PersonalItemService {
    private final PersonalItemRepository<PersonalItem> repository;

    public PersonalItemService(PersonalItemRepository<PersonalItem> repository) {
        this.repository = repository;
    }

    public List<PersonalItem> all() {
        return repository.all();
    }

    public PersonalItem create(PersonalItem item) {
        // TODO item data check

        Long nextId = repository.all().size() + 1L;
        item.setId(nextId);

        return repository.add(item);
    }

    public PersonalItem getById(Long id) {
        return repository.get(id);
    }

    public Boolean update(PersonalItem item) {
        // TODO item data check
        repository.update(item);
        return true;
    }
}
