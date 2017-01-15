package javaee7.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named
public class MyProcessor implements ItemProcessor {

  @Override
  public Object processItem(Object item) throws Exception {
    return ((String) item).toUpperCase();
  }

}
