package ch.ibw.appl.restserver.functional.shared.functional;

import com.fasterxml.jackson.core.type.TypeReference;
import ch.ibw.appl.restserver.item.model.PersonalItem;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import ch.ibw.appl.restserver.item.shared.service.JsonSerializer;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;


public class ScannerControllerTest extends FunctionalTest {
    @Test
    public void notAcceptable() {
        HttpRequest.Builder method = httpClient.get("/scanner/items");
        HttpResponse<String> response = httpClient.execute(method);
        assertEquals(HttpStatus.NOT_ACCEPTABLE_406, response.statusCode());
    }

    @Test
    public void get_byId() {
        HttpResponse<String> response = executeGet("/personal/items/2");
        PersonalItem oldItem = new JsonSerializer().deserialize(response.body(), new TypeReference<PersonalItem>() {});

        response = executeGet("/scanner/items/2");

        assertEquals(HttpStatus.OK_200, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").get());

        PersonalItem item = new JsonSerializer().deserialize(response.body(), new TypeReference<PersonalItem>() {});
        assertEquals(oldItem.getAnzahlEintritte()+1, item.getAnzahlEintritte());
    }
}
