package javaee7.concurrency;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Stateless
public class ReportService {

  private static final Logger LOGGER = Logger.getLogger(ReportService.class.getName());

  @Resource
  private DataSource ds;

  public void report() {
    LOGGER.info("Start");

    try (Connection connection = ds.getConnection()) {

      TimeUnit.SECONDS.sleep(5);
      LOGGER.info("connection: " + connection);

    } catch (SQLException | InterruptedException e) {
      e.printStackTrace();
    }

    LOGGER.info("Done");
  }

}
