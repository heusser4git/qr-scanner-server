package ch.ibw.appl.restserver.item.service;

import ch.ibw.appl.restserver.item.model.PersonalItem;

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
        checkPersonalItemData(item);
        return repository.add(item);
    }

    private boolean checkPersonalItemData(PersonalItem item) {
        if(item.getNachname() == null || item.getNachname().isEmpty()) {
            throw new ValidationError("Nachname darf nicht leer sein.");
        }
        if(item.getVorname() == null || item.getVorname().isEmpty()) {
            throw new ValidationError("Vorname darf nicht leer sein.");
        }
        return true;
    }

    public PersonalItem getById(Long id) {
        return repository.get(id);
    }

    public Boolean update(PersonalItem item) {
        checkPersonalItemData(item);
        return repository.update(item);
    }

    public boolean delete(long id) {
        if(id>0) {
            return repository.delete(id);
        } else {
            throw new ValidationError("ID muss groesser als 0 sein.");
        }
    }
}
