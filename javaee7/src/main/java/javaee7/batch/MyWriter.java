package javaee7.batch;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;

@Dependent
@Named
public class MyWriter implements ItemWriter {

  private BufferedWriter writer;

  @Inject
  @BatchProperty
  String output;

  @Override
  public void open(Serializable checkpoint) throws Exception {
    writer = new BufferedWriter(new FileWriter(output, (checkpoint != null)));
  }

  @Override
  public void close() throws Exception {
    writer.close();
  }

  @Override
  public void writeItems(List<Object> items) throws Exception {
    for (int i = 0; i < items.size(); i++) {
      writer.write((String) items.get(i));
      writer.newLine();
    }
  }

  @Override
  public Serializable checkpointInfo() throws Exception {
    return new MyCheckPoint();
  }

}
