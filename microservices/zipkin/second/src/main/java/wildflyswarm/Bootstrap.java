package wildflyswarm;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.btm.ZipkinFraction;

public class Bootstrap {

  public static void main(String[] args) throws Exception {
    Swarm swarm = new Swarm(args);

    swarm.fraction(new ZipkinFraction("second")
      .reportAsync("http://localhost:9411/api/v1/spans"));

    JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
    archive.addPackage("zipkin");

    swarm.start().deploy(archive);
  }
}
