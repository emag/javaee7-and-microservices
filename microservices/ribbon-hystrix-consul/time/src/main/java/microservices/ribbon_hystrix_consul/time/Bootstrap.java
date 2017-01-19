package microservices.ribbon_hystrix_consul.time;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.topology.TopologyArchive;

public class Bootstrap {

  public static void main(String[] args) throws Exception {
    Swarm swarm = new Swarm(args);

    JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class, "time.war");
    archive.addResource(TimeResource.class);

    archive.as(TopologyArchive.class).advertise();

    swarm.start().deploy(archive);
  }

}
