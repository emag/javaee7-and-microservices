package javaee7.batch;

import javax.batch.api.Batchlet;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Dependent
@Named
public class MyBatchlet implements Batchlet {

  private static final Logger LOGGER = Logger.getLogger(MyBatchlet.class.getName());

  @Override
  public String process() throws Exception {
    LOGGER.info("process: Start");

    TimeUnit.SECONDS.sleep(10);

    LOGGER.info("process: Done");
    return "COMPLETED";
  }

  @Override
  public void stop() throws Exception {
    // no-op
  }

}
