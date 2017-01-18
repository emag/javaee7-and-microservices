package javaee7.api;

import javaee7.concurrency.CalculationService;
import javaee7.concurrency.ReportTask;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/concurrency")
public class ConcurrencyController {

  @Resource
  private ManagedExecutorService executorService;

  @Inject
  private CalculationService calculationService;

  @POST
  @Path("/report")
  public String send() {
    executorService.execute(new ReportTask());

    return "Preparing the report...";
  }

  @GET
  @Path("/calc")
  @Produces(MediaType.APPLICATION_JSON)
  public CalculationResponse get(@QueryParam("a") int a, @QueryParam("b") int b) throws ExecutionException, InterruptedException {

    CompletableFuture<Integer> addition =
      CompletableFuture.supplyAsync(() -> calculationService.add(a, b), executorService);

    CompletableFuture<Integer> square =
      CompletableFuture.supplyAsync(() -> calculationService.square(a), executorService);

    long start = System.nanoTime();

    String result = addition
      .thenCombineAsync(square, (additionResult, squareResult) -> additionResult * squareResult)
      .thenApply(r -> String.format("*** %s ***", r))
      .get();

    long elapsedTimeInMilli = (System.nanoTime() - start) / 1000 / 1000;

    return new CalculationResponse(result, elapsedTimeInMilli + " msec.");

  }

}
