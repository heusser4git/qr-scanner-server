package item.infra;

import item.model.PersonalItem;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;
import item.service.PersonalItemService;
import shared.service.JsonSerializer;

import java.sql.SQLException;

public class ScannerController {
    private PersonalItemService personalItemService;
    public ScannerController(Boolean isTest) {
        if(Boolean.TRUE.equals(isTest)) {
            personalItemService = new PersonalItemService(new PersonalItemInMemoryRepository(isTest));
        } else {
            try {
                personalItemService = new PersonalItemService(new PersonalItemSQLRepository(isTest));
            } catch (SQLException e) {
                Logger logger = LoggerFactory.getLogger(ScannerController.class);
                logger.error("SQLException while checking if DB is offline.", e);
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
