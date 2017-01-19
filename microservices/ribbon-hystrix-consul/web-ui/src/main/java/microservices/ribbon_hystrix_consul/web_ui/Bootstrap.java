package microservices.ribbon_hystrix_consul.web_ui;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.topology.TopologyArchive;
import org.wildfly.swarm.undertow.WARArchive;

public class Bootstrap {

  public static void main(String... args) throws Exception {
    Swarm swarm = new Swarm(args);

    WARArchive archive = ShrinkWrap.create(WARArchive.class, "web-ui.war");
    archive.staticContent();
//    archive.addAllDependencies();

//    archive.as(TopologyArchive.class).advertise();

    swarm.start().deploy(archive);
  }

}
