package javaee7.concurrency;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class ReportService {

  @Resource
  private DataSource ds;

  public void report() {
    System.out.println(ds);
  }

}
