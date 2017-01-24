package microservices.ribbon_hystrix_consul.time;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class TimeResource {

  private static final Logger LOGGER = Logger.getLogger(TimeResource.class);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get() {
    LOGGER.info("I was asked for the time");

    Map<String, Object> t = new HashMap<>();
    LocalDateTime now = LocalDateTime.now();

    t.put("Y", now.getYear());
    t.put("M", now.getMonthValue());
    t.put("D", now.getDayOfMonth());
    t.put("h", now.getHour());
    t.put("m", now.getMinute());
    t.put("s", now.getSecond());
    t.put("ms", now.getNano() / 1000 / 1000);

    return Response.ok(t, MediaType.APPLICATION_JSON_TYPE)
      .entity(t)
      .build();
  }

}
