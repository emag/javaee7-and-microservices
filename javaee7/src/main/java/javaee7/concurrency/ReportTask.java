package javaee7.concurrency;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ReportTask implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(ReportTask.class.getName());

  @Inject
  private ReportService service;

  @Override
  public void run() {
    LOGGER.info("Start");

    LOGGER.info("service: " + service);

    service.report();

    try {
      // something takes a long time to complete
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    LOGGER.info("Done");
  }

}
