package shared;

import org.junit.Rule;
import shared.infra.RestServer;
import shared.service.JsonSerializer;
import spark.servlet.SparkApplication;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FunctionalTest {

  public static class PersonalApplication implements SparkApplication {
    private final RestServer restServer;

    public PersonalApplication(Integer port) {
      restServer = new RestServer(String.valueOf(port), true);
    }

    @Override
    public void init() {
      restServer.run();
    }

    @Override
    public void destroy() {
      restServer.halt();
    }
  }

  @Rule
  public SparkServer<PersonalApplication> httpClient = new SparkServer<>(PersonalApplication.class, 7778);

  public HttpResponse<String> executeGet(String path, String acceptType) {
    HttpRequest.Builder method = httpClient.get(path);
    method.header("Accept", acceptType);
    return httpClient.execute(method);
  }

  public HttpResponse<String> executeGet(String path) {
    return executeGet(path, "application/json");
  }

  public HttpResponse<String> executePost(String path, Object body) {
    return executePost(path, body, "application/json");
  }

  public HttpResponse<String> executePost(String path, Object body, String acceptType) {
    HttpRequest.Builder method = httpClient.post(path, new JsonSerializer().serialize(body));
    method.header("Accept", acceptType);
    return httpClient.execute(method);
  }

  public HttpResponse<String> executeDelete(String path) {
    return executeDelete(path, "application/json");
  }

  public HttpResponse<String> executeDelete(String path, String acceptType) {
    HttpRequest.Builder method = httpClient.delete(path);
    method.header("Accept", acceptType);
    return httpClient.execute(method);
  }
}
