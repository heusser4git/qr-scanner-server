package item.model;

import javax.annotation.processing.Generated;

public class PersonalItem {
    private Long id;
    private String nachname;
    private String vorname;

    public static PersonalItem create(Long id, String nachname, String vorname) {
        PersonalItem personalItem = new PersonalItem();
        personalItem.id = id;
        personalItem.nachname = nachname;
        personalItem.vorname = vorname;
        // TODO Implement more attributes like: bild, geburtsdatum
        return personalItem;
    }

    public Long getId() {
        return id;
    }

    public PersonalItem setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNachname() {
        return nachname;
    }

    public PersonalItem setNachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public String getVorname() {
        return vorname;
    }

    public PersonalItem setVorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    @Override
    public String toString() {
        return "PersonalItem{" +
                "id=" + id +
                ", nachname='" + nachname + '\'' +
                ", vorname='" + vorname + '\'' +
                '}';
    }
}
