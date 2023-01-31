package ch.ibw.appl.restserver.functional.shared.functional;

import com.fasterxml.jackson.core.type.TypeReference;
import ch.ibw.appl.restserver.item.model.PersonalItem;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import ch.ibw.appl.restserver.item.shared.service.JsonSerializer;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PersonalControllerTest extends FunctionalTest {
    @Test
    public void notAcceptable() {
        HttpRequest.Builder method = httpClient.get("/personal/items");
        HttpResponse<String> response = httpClient.execute(method);
        assertEquals(HttpStatus.NOT_ACCEPTABLE_406, response.statusCode());
    }

    @Test
    public void get_personalItems() {
        HttpResponse<String> response = executeGet("/personal/items");

        assertEquals(HttpStatus.OK_200, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());

        String body = response.body();

        List<PersonalItem> items = new JsonSerializer().deserialize(body, new TypeReference<List<PersonalItem>>() {
        });
        assertEquals("Heusser", items.get(0).getNachname());
    }

    @Test
    public void get_byId() {
        HttpResponse<String> response = executeGet("/personal/items/2");

        assertEquals(HttpStatus.OK_200, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());

        String body = response.body();

        PersonalItem item = new JsonSerializer().deserialize(body, new TypeReference<PersonalItem>() {});
        assertEquals("Perko", item.getNachname());
    }

    @Test
    public void get_byId_notFound() {
        HttpResponse<String> response = executeGet("/personal/items/42");

        assertEquals(HttpStatus.NOT_FOUND_404, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());

        String body = response.body();
        assertEquals("\"\"", body);
    }

    @Test
    public void create_PersonalItem() {
        PersonalItem item = new PersonalItem();
        item.setNachname("Barandun");
        item.setVorname("Manuel");
        item.setGeburtsdatum(Date.valueOf("1970-12-20"));

        HttpResponse<String> response = executePost("/personal/items", item);

        assertEquals(HttpStatus.CREATED_201, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());

        String body = response.body();
        PersonalItem createdItem = new JsonSerializer().deserialize(body, new TypeReference<PersonalItem>() {
        });
        assertNotNull(createdItem.getId());
        assertEquals("Barandun", createdItem.getNachname());
        assertEquals("Manuel", createdItem.getVorname());
        assertEquals("1970-12-20", createdItem.getGeburtsdatum().toString());
        assertEquals(false, createdItem.isStatus());
        assertEquals(0, createdItem.getAnzahlEintritte());
    }

    @Test
    public void create_PersonalItem_validationFailed() {
        PersonalItem item = new PersonalItem();
        item.setNachname("");
        item.setVorname("");

        HttpResponse<String> response = executePost("/personal/items", item);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());

        String body = response.body();
        assertTrue(body.contains("message"));
    }

    @Test
    public void update_PersonalItem() {
        PersonalItem item = new PersonalItem();
        item.setId(1L);
        item.setNachname("Barandun");
        item.setVorname("Manuel");
        item.setGeburtsdatum(Date.valueOf("1970-12-20"));
        item.setStatus(false);
        item.setAnzahlEintritte(77);

        HttpResponse<String> response = executeGet("/personal/items/1");
        PersonalItem oltItem = new JsonSerializer().deserialize(response.body(), new TypeReference<PersonalItem>() {});
        assertEquals("Heusser", oltItem.getNachname());


        response = executePost("/personal/items", item);

        assertEquals(HttpStatus.ACCEPTED_202, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());

        String body = response.body();
        PersonalItem updatedItem = new JsonSerializer().deserialize(body, new TypeReference<PersonalItem>() {
        });
        assertNotNull(updatedItem.getId());

        assertEquals("Barandun", updatedItem.getNachname());
        assertEquals("Manuel", updatedItem.getVorname());
        assertEquals("1970-12-20", updatedItem.getGeburtsdatum().toString());
        assertEquals(false, updatedItem.isStatus());
        assertEquals(77, updatedItem.getAnzahlEintritte());
    }

    @Test
    public void delete_PersonalItem() {
        HttpResponse<String> response = executeDelete("/personal/item/2");
        assertEquals(HttpStatus.NOT_FOUND_404, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());
    }
}
