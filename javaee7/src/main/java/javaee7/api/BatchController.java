package javaee7.api;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@Path("/batch")
public class BatchController {

  private Map<Long, JobExecution> jobs = new ConcurrentHashMap<>();

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response jobs(@PathParam("id") Long id) {
    JobExecution execution = jobs.get(id);

    if (execution == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    String endTimeAsString;
    Date endTime = execution.getEndTime();
    if (endTime == null) {
      endTimeAsString = "---";
    } else {
      endTimeAsString = endTime.toString();
    }

    JsonObject response = Json.createObjectBuilder()
      .add("id", id)
      .add("name", execution.getJobName())
      .add("status", execution.getBatchStatus().toString())
      .add("create_time", execution.getCreateTime().toString())
      .add("end_time", endTimeAsString)
      .build();

    return Response.ok(response, MediaType.APPLICATION_JSON).build();
  }

  @POST
  public Response startJob(@Context UriInfo uriInfo) {
    String outputPath = Paths.get(System.getProperty("java.io.tmpdir"), "output.txt").toString();
    Properties jobParameters = new Properties();
    jobParameters.setProperty("output", outputPath);

    JobOperator job = BatchRuntime.getJobOperator();
    long id = job.start("simple_job", jobParameters);

    JobExecution execution = job.getJobExecution(id);
    jobs.put(id, execution);

    return Response
      .created(
        uriInfo.getAbsolutePathBuilder()
          .path(String.valueOf(id))
          .build())
      .build();
  }

}
