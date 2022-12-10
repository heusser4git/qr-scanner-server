package shared.infra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import item.infra.PersonalItemController;
import item.infra.ScannerController;
import item.service.ValidationError;
import org.eclipse.jetty.http.HttpStatus;
import spark.Service;

public class RestServer {
    private final String port;
    private final Boolean isTest;
    private Service server;

    public RestServer(String port, Boolean isTest) {
        this.port = port;
        this.isTest = isTest;
    }

    public void run() {
        server = Service.ignite();
        server.port(Integer.parseInt(port));

        new PersonalItemController(isTest).createRoutes(server);
        new ScannerController(isTest).createRoutes(server);

        server.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        server.before(((request, response) -> {
            // exclude .../image from requiring to accept application/json
            String acceptHeader = request.headers("Accept");
            if(acceptHeader==null) {
                System.out.println("no accept header set by client");
                server.halt(HttpStatus.NOT_ACCEPTABLE_406);
            }
            final boolean isOptions = request.requestMethod().equalsIgnoreCase("OPTIONS");
            if(!isOptions && !this.isTest){
                final boolean clientWantsJson = acceptHeader.contains("application/json");
                if(!clientWantsJson){
                    System.out.println("was not json");
                    server.halt(HttpStatus.NOT_ACCEPTABLE_406);
                }
            }
        }));


        server.options("/*", ((request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        }));


        server.afterAfter((((request, response) -> response.type("application/json"))));

        server.exception(RuntimeException.class, (exception, request, response)->{
            if(exception instanceof ValidationError) {
                String message = ((ValidationError) exception).message;
                JsonNode node = JsonNodeFactory.instance.objectNode().set("message", JsonNodeFactory.instance.textNode(message));
                response.body(node.toString());
                response.status(HttpStatus.UNPROCESSABLE_ENTITY_422);
            } else {
                response.body("");
                response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            }
        });

        server.notFound(((request, response) -> ""));

        server.awaitInitialization();
    }

    public void halt() {
        server.stop();
        server.awaitStop();
    }
}
