package javaee7.batch;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class MyCheckPoint implements Serializable {

  private AtomicLong lineNum = new AtomicLong();

  public void increment() {
    lineNum.getAndIncrement();
  }

  public long getLineNum() {
    return lineNum.get();
  }

}
