package ch.ibw.appl.restserver.item.infra;

import ch.ibw.appl.restserver.item.model.PersonalItem;
import ch.ibw.appl.restserver.item.service.PersonalItemService;
import org.eclipse.jetty.http.HttpStatus;
import spark.Service;
import ch.ibw.appl.restserver.item.shared.service.JsonSerializer;


public class ScannerController {
    private PersonalItemService personalItemService;
    public ScannerController(Boolean isTest) {
        if(Boolean.TRUE.equals(isTest)) {
            personalItemService = new PersonalItemService(new PersonalItemInMemoryRepository(isTest));
        } else {
            personalItemService = new PersonalItemService(new PersonalItemSQLRepository(isTest));
        }
    }

    public void createRoutes(Service server) {
        JsonSerializer jsonSerializer = new JsonSerializer();

        server.get("/scanner/items/:id", (request, response) -> {
            long id = Long.parseLong(request.params("id"));
            PersonalItem personalItem = personalItemService.getById(id);
            boolean itemFound = personalItem != null && personalItem.getId().equals(id);
            if(itemFound) {
                response.status(HttpStatus.OK_200);
                personalItem.setAnzahlEintritte(personalItem.getAnzahlEintritte()+1);
                personalItemService.update(personalItem);
                return personalItem;
            } else {
                response.status(HttpStatus.NOT_FOUND_404);
                return "";
            }
        }, jsonSerializer::serialize);

    }
}
