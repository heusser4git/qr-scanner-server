package item.infra;

import com.fasterxml.jackson.core.type.TypeReference;
import item.model.PersonalItem;
import item.service.PersonalItemService;
import org.eclipse.jetty.http.HttpStatus;
import shared.service.JsonSerializer;
import spark.Service;

import java.sql.SQLException;

public class PersonalItemController {
    private PersonalItemService personalItemService;

    public PersonalItemController(Boolean isTest) {
        if(isTest) {
            personalItemService = new PersonalItemService(new PersonalItemInMemoryRepository(isTest));
        } else {
            // TODO noTestVersion???
            try {
                personalItemService = new PersonalItemService(new PersonalItemSQLRepository(isTest));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createRoutes(Service server) {
        JsonSerializer jsonSerializer = new JsonSerializer();

        server.get("/personal/items", "*",
                (request, response) -> {
                    response.status(HttpStatus.OK_200);
                    return personalItemService.all();
                },
                jsonSerializer::serialize);

        server.get("/personal/items/:id", (request, response) -> {
            long id = Long.parseLong(request.params("id"));
            PersonalItem personalItem = personalItemService.getById(id);
            if(personalItem != null && personalItem.getId()==id) {
                response.status(HttpStatus.OK_200);
                return personalItem;
            } else {
                response.status(HttpStatus.NOT_FOUND_404);
                return "";
            }
        }, jsonSerializer::serialize);

        server.post("/personal/items", (request, response) -> {
                PersonalItem item = jsonSerializer.deserialize(request.body(), new TypeReference<PersonalItem>() {});
                System.out.println("POST: " + item);
                if(item.getId()!=null) {
                    // update
                    System.out.println("UPDATE: " + item);
                    if(personalItemService.update(item)) {
                        response.status(HttpStatus.ACCEPTED_202);
                    } else {
                        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                    }
                    return item;
                } else {
                    System.out.println("CREATE: " + item);
                    PersonalItem personalItem = personalItemService.create(item);
                    if(personalItem.getId()>0) {
                        response.status(HttpStatus.CREATED_201);
                    } else {
                        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                    }
                    return personalItem;
                }
        }, jsonSerializer::serialize);

        server.delete("/personal/items/:id", (request, response) -> {
            System.out.println("DO NE");
            long id = Long.parseLong(request.params("id"));
            boolean result = personalItemService.delete(id);
            if(result) {
                response.status(HttpStatus.OK_200);
            } else {
                response.status(HttpStatus.INSUFFICIENT_STORAGE_507);
            }
            return result;
        }, jsonSerializer::serialize);
    }
}
