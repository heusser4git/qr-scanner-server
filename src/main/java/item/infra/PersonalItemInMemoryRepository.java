package item.infra;

import item.model.PersonalItem;
import item.service.PersonalItemRepository;

import java.sql.Date;
import java.util.*;

public class PersonalItemInMemoryRepository implements PersonalItemRepository<PersonalItem> {
    private ArrayList<PersonalItem> personalItems = new ArrayList<>();

    public PersonalItemInMemoryRepository(boolean isTest) {
        if(isTest) {
            Date geb = Date.valueOf("1970-01-10");
            this.add(PersonalItem.create(1L, "Heusser", "Urs", Date.valueOf("1970-01-10"), true));
            this.add(PersonalItem.create(2L, "Perko", "Mitja", Date.valueOf("1993-05-16"), true));
            this.add(PersonalItem.create(3L, "Ogi", "Adolf", Date.valueOf("1951-09-18"), false));
        }
    }

    @Override
    public List<PersonalItem> all() {
        return personalItems;
    }

    @Override
    public PersonalItem add(PersonalItem object) {
        personalItems.add(object);
        // TODO remove sout
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
                personalitem.setGeburtsdatum(object.getGeburtsdatum());
                personalitem.setStatus(object.getStatus());
                // TODO remove sout
                System.out.println(all());
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
        PersonalItem personalItem = this.get(id);
        return this.personalItems.remove(personalItem);
    }
}
