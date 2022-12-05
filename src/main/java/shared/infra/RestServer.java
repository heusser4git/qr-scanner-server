package shared.infra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import item.infra.PersonalItemController;
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

        server.before((((request, response) -> {
//            final boolean clientWantsJSON = request.headers("Accept").contains("application/json");
//            if(!clientWantsJSON) {
//                server.halt(HttpStatus.NOT_ACCEPTABLE_406);
//            }
        })));

        server.before(((request, response) -> response.header("Access-Control-Allow-Origin", "*")));

        server.options("/*", ((request, response) -> {
            String requestHeadersAccessControl = request.headers("Access-Control-Request-Headers");
            if(requestHeadersAccessControl != null) {
                response.header("Access-Control-Allow-Headers", requestHeadersAccessControl);
            }
            String requestMethodAccessControl = request.headers("Access-Control-Request-Methods");
            if(requestMethodAccessControl != null) {
                response.header("Access-Control-Allow-Methods", requestMethodAccessControl);
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
                System.out.println("Error: " + exception.toString());
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
