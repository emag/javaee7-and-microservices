package javaee7.concurrency;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@ApplicationScoped
public class CalculationService {

  private static final Logger LOGGER = Logger.getLogger(CalculationService.class.getName());

  public int add(int a, int b) {
    LOGGER.info(String.format("Start: add(%d, %d)", a, b));
    sleep(5);

    int result = a + b;
    LOGGER.info(String.format("Done: add(%d, %d) = %d", a, b, result));
    return result;
  }

  public int square(int a) {
    LOGGER.info(String.format("Start: square(%d)", a));
    sleep(10);

    int result = a * a;
    LOGGER.info(String.format("Done: square(%d) = %d", a, result));
    return result;
  }

  private static void sleep(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
