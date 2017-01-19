package zipkin;

import com.github.kristofa.brave.Brave;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.urlconnection.URLConnectionSender;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class FirstController {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String first() {
    AsyncReporter<Span> asyncReporter = AsyncReporter.builder(
        URLConnectionSender.create("http://localhost:9411/api/v1/spans"))
        .build();

    Brave brave = new Brave.Builder("second-client")
      .reporter(asyncReporter)
      .build();

    Client client = ClientBuilder.newBuilder()
      .register(BraveTracingFeature.create(brave))
      .build();

    try {
      WebTarget target = client.target("http://localhost:8280");
      Response response = target.queryParam("message", "ONE").request().get();
      return response.readEntity(String.class);
    } finally {
      client.close();
    }
  }

}
