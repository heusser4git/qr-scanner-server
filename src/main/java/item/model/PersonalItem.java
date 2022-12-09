package item.model;

import java.sql.Date;

public class PersonalItem {
    private Long id;
    private String nachname;
    private String vorname;
    private Date geburtsdatum;
    private boolean status;

    public static PersonalItem create(Long id, String nachname, String vorname, Date geburtsdatum, Boolean status) {
        PersonalItem personalItem = new PersonalItem();
        personalItem.id = id;
        personalItem.nachname = nachname;
        personalItem.vorname = vorname;
        personalItem.geburtsdatum = geburtsdatum;
        personalItem. status = status;
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

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public PersonalItem setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public PersonalItem setStatus(boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "PersonalItem{" +
                "id=" + id +
                ", nachname='" + nachname + '\'' +
                ", vorname='" + vorname + '\'' +
                ", geburtsdatum=" + geburtsdatum +
                ", status=" + status +
                '}';
    }
}
