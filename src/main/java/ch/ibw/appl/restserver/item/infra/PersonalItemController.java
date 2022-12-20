package ch.ibw.appl.restserver.item.infra;

import ch.ibw.appl.restserver.item.service.PersonalItemService;
import com.fasterxml.jackson.core.type.TypeReference;
import ch.ibw.appl.restserver.item.model.PersonalItem;
import org.eclipse.jetty.http.HttpStatus;
import ch.ibw.appl.restserver.item.shared.service.JsonSerializer;
import spark.Service;


public class PersonalItemController {
    private PersonalItemService personalItemService;

    public PersonalItemController(Boolean isTest) {
        if(Boolean.TRUE.equals(isTest)) {
            personalItemService = new PersonalItemService(new PersonalItemInMemoryRepository(isTest));
        } else {
            personalItemService = new PersonalItemService(new PersonalItemSQLRepository(isTest));
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
            boolean isTheSameItem = personalItem != null && personalItem.getId().equals(id);
            if(isTheSameItem) {
                response.status(HttpStatus.OK_200);
                return personalItem;
            } else {
                response.status(HttpStatus.NOT_FOUND_404);
                return "";
            }
        }, jsonSerializer::serialize);

        server.post("/personal/items", (request, response) -> {
            PersonalItem item = jsonSerializer.deserialize(request.body(), new TypeReference<PersonalItem>() {});
            boolean isAnItem = item.getId() != null;
            if(isAnItem) {
                // update
                Boolean updateTheItem = personalItemService.update(item);
                if(Boolean.TRUE.equals(updateTheItem)) {
                    response.status(HttpStatus.ACCEPTED_202);
                } else {
                    response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                }
                return item;
            } else {
                PersonalItem personalItem = personalItemService.create(item);
                boolean hasNowAnId = personalItem.getId() > 0;
                if(hasNowAnId) {
                    response.status(HttpStatus.CREATED_201);
                } else {
                    response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                }
                return personalItem;
            }
        }, jsonSerializer::serialize);

        server.delete("/personal/items/:id", (request, response) -> {
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
