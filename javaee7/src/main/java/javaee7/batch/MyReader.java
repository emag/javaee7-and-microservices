package javaee7.batch;

import javax.batch.api.chunk.ItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Dependent
@Named
public class MyReader implements ItemReader {

  private MyCheckPoint myCheckPoint;
  private BufferedReader reader;

  @Override
  public void open(Serializable checkpoint) throws Exception {
    if (checkpoint == null) {
      myCheckPoint = new MyCheckPoint();
    } else {
      myCheckPoint = (MyCheckPoint) checkpoint;
    }

    reader = new BufferedReader(
      new InputStreamReader(getClass().getResourceAsStream("/input.txt"), StandardCharsets.UTF_8));

    for (long i = 0; i < myCheckPoint.getLineNum(); i++) {
      reader.readLine();
    }

  }

  @Override
  public void close() throws Exception {
    reader.close();
  }

  @Override
  public Object readItem() throws Exception {
    return reader.readLine();
  }

  @Override
  public Serializable checkpointInfo() throws Exception {
    return myCheckPoint;
  }

}
