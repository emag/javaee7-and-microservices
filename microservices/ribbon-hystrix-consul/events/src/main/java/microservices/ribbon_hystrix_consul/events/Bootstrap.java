package microservices.ribbon_hystrix_consul.events;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.netflix.ribbon.RibbonArchive;

public class Bootstrap {

  public static void main(String[] args) throws Exception {
    Swarm swarm = new Swarm(args);

    JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class, "events.war");
    archive.addPackage(Bootstrap.class.getPackage());
    archive.addAllDependencies();

    archive.as(RibbonArchive.class).advertise();

    swarm.start().deploy(archive);
  }

}
