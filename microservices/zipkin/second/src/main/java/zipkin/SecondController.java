package zipkin;

import com.github.kristofa.brave.Brave;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.urlconnection.URLConnectionSender;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("/")
public class SecondController {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String second(@QueryParam("message") String message) {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException ignore) {}

    AsyncReporter<Span> asyncReporter = AsyncReporter.builder(
      URLConnectionSender.create("http://localhost:9411/api/v1/spans"))
      .build();

    Brave brave = new Brave.Builder("third-client")
      .reporter(asyncReporter)
      .build();

    Client client = ClientBuilder.newBuilder()
      .register(BraveTracingFeature.create(brave))
      .build();

    try {
      WebTarget target = client.target("http://localhost:8380");
      Response response = target.queryParam("message", message + " TWO").request().get();
      return response.readEntity(String.class);
    } finally {
      client.close();
    }
  }

}
