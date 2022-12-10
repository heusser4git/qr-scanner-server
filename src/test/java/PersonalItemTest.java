import item.model.PersonalItem;
import junit.framework.TestCase;

import java.sql.Date;

public class PersonalItemTest extends TestCase {

    public void testCreate() {
        PersonalItem personalItem = PersonalItem.create(2L, "Heusser", "Urs", Date.valueOf("2022-12-10"), false);
        assertEquals(Long.valueOf(2), personalItem.getId());
        assertEquals("Heusser", personalItem.getNachname());
        assertEquals("Urs", personalItem.getVorname());
        assertEquals("2022-12-10", personalItem.getGeburtsdatum().toString());
        assertEquals(false, personalItem.isStatus());
    }

    public void testGetId() {
        PersonalItem personalItem = PersonalItem.create(2L, "Heusser", "Urs", Date.valueOf("2022-12-10"), false);
        assertEquals(Long.valueOf(2), personalItem.getId());
    }

    public void testSetId() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setId(2L);
        assertEquals(Long.valueOf(3), personalItem.getId());
    }

    public void testGetNachname() {
        PersonalItem personalItem = PersonalItem.create(2L, "Heusser", "Urs", Date.valueOf("2022-12-10"), false);
        assertEquals("Heusser", personalItem.getNachname());
    }

    public void testSetNachname() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setNachname("Heusser");
        assertEquals("Heusser", personalItem.getNachname());
    }

    public void testGetVorname() {
        PersonalItem personalItem = PersonalItem.create(2L, "Heusser", "Urs", Date.valueOf("2022-12-10"), false);
        assertEquals("Urs", personalItem.getVorname());
    }

    public void testSetVorname() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setVorname("Urs");
        assertEquals("Urs", personalItem.getVorname());
    }

    public void testGetGeburtsdatum() {
        PersonalItem personalItem = PersonalItem.create(2L, "Heusser", "Urs", Date.valueOf("2022-12-10"), false);
        assertEquals("2022-12-10", personalItem.getGeburtsdatum().toString());
    }

    public void testSetGeburtsdatum() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setGeburtsdatum(Date.valueOf("2022-12-10"));
        assertEquals("2022-12-10", personalItem.getGeburtsdatum().toString());
    }

    public void testIsStatus() {
        PersonalItem personalItem = PersonalItem.create(2L, "Heusser", "Urs", Date.valueOf("2022-12-10"), true);
        assertEquals(true, personalItem.isStatus());
    }

    public void testSetStatus() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setStatus(true);
        assertEquals(true, personalItem.isStatus());
    }

    public void testGetAnzahlEintritte() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setAnzahlEintritte(2);
        assertEquals(2, personalItem.getAnzahlEintritte());
    }

    public void testSetAnzahlEintritte() {
        PersonalItem personalItem = new PersonalItem();
        personalItem.setAnzahlEintritte(2);
        assertEquals(2, personalItem.getAnzahlEintritte());
    }

    public void testTestToString() {
        PersonalItem personalItem = PersonalItem.create(2L, "Heusser", "Urs", Date.valueOf("2022-12-10"), true);
        personalItem.setAnzahlEintritte(5);
        assertEquals("PersonalItem{id=2, nachname='Heusser', vorname='Urs', geburtsdatum=2022-12-10, status=true, anzahlEintritte=5}", personalItem.toString());
    }
}