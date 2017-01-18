package javaee7.concurrency;

import javax.inject.Inject;

public class ReportTask implements Runnable {

  @Inject
  private ReportService service;

  @Override
  public void run() {
    service.report();
  }

}
