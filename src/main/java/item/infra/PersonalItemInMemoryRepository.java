package item.infra;

import item.model.ModelId;
import item.model.PersonalItem;
import item.service.PersonalItemRepository;

import java.util.*;

public class PersonalItemInMemoryRepository implements PersonalItemRepository<PersonalItem> {
    private Map<ModelId, PersonalItem> map = Collections.synchronizedMap(new LinkedHashMap<>());
    private long nextId = 0;

    public PersonalItemInMemoryRepository(boolean isTest) {
        if(isTest) {
            nextId = 23;
            this.add(PersonalItem.create("Heusser", "Urs"));
        }
    }

    @Override
    public List<PersonalItem> all() {
        return new ArrayList<>(map.values());
    }

    @Override
    public ModelId add(PersonalItem object) {
        ModelId id = ModelId.create(nextId++);
        map.put(id, object);
        return id;
    }

    @Override
    public PersonalItem get(Long id) {
        return map.entrySet().stream()
                .filter(e -> e.getKey().id.equals(id))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    @Override
    public Boolean update(PersonalItem object) {
        return null;
    }
}
