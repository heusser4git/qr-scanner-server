package item.infra;

import item.model.ModelId;
import item.model.PersonalItem;
import item.service.PersonalItemRepository;

import java.util.*;

public class PersonalItemInMemoryRepository implements PersonalItemRepository<PersonalItem> {
    private ArrayList<PersonalItem> personalItems = new ArrayList<>();

    public PersonalItemInMemoryRepository(boolean isTest) {
        if(isTest) {
            this.add(PersonalItem.create(1L, "Heusser", "Urs"));
            this.add(PersonalItem.create(2L, "Perko", "Mitja"));
            this.add(PersonalItem.create(3L, "Ogi", "Adolf"));
        }
    }

    @Override
    public List<PersonalItem> all() {
        return personalItems;
    }

    @Override
    public PersonalItem add(PersonalItem object) {
        personalItems.add(object);
        System.out.println(all());
        return object;
    }

    @Override
    public PersonalItem get(Long id) {
        for(PersonalItem personalItem: personalItems) {
            if(personalItem.getId()==id) {
                return personalItem;
            }
        }
        return null;
    }

    @Override
    public Boolean update(PersonalItem object) {
        for (PersonalItem personalitem: personalItems) {
            if(personalitem.getId()==object.getId()) {
                personalitem.setNachname(object.getNachname());
                personalitem.setVorname(object.getVorname());
                // Todo ergaenze parameter
                System.out.println(all());
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean delete(PersonalItem object) {
        return null;
    }
}
