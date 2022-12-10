package item.infra;

import item.model.PersonalItem;
import org.eclipse.jetty.http.HttpStatus;
import spark.Service;
import item.service.PersonalItemService;
import shared.service.JsonSerializer;

import java.sql.SQLException;

public class ScannerController {
    private PersonalItemService personalItemService;
    public ScannerController(Boolean isTest) {
        if(isTest) {
            personalItemService = new PersonalItemService(new PersonalItemInMemoryRepository(isTest));
        } else {
            try {
                personalItemService = new PersonalItemService(new PersonalItemSQLRepository(isTest));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createRoutes(Service server) {
        JsonSerializer jsonSerializer = new JsonSerializer();

        server.get("/scanner/items/:id", (request, response) -> {
            long id = Long.parseLong(request.params("id"));
            PersonalItem personalItem = personalItemService.getById(id);
            if(personalItem != null && personalItem.getId()==id) {
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
