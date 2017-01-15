package javaee7.api;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/concurrency")
public class ConcurrencyController {

  @Resource
  private ManagedExecutorService executor;

  @GET
  public String get() {
    return executor.toString();
  }

}
