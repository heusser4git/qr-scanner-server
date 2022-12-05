package item.model;

import javax.annotation.processing.Generated;

public class PersonalItem {
    public Long id;
    private String nachname;
    private String vorname;

    public static PersonalItem create(String nachname, String vorname) {
        PersonalItem personalItem = new PersonalItem();
        personalItem.nachname = nachname;
        personalItem.vorname = vorname;
        // TODO Implement more attributes like: bild, geburtsdatum
        return personalItem;
    }

    @Override
    public String toString() {
        return "PersonalItem{" +
                "nachname='" + nachname + '\'' +
                ", vorname='" + vorname + '\'' +
                '}';
    }
}
