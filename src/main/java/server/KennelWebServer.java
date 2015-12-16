package server;

import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author Milton
 */
public class KennelWebServer {

	private static final int PORT_NUMBER = 8082;

	public static void main(String[] args) {
		Server server = new Server(PORT_NUMBER);
		try {
			WebAppContext context = new WebAppContext();
			context.setContextPath("/");
			context.setResourceBase(KennelWebServer.class.getResource("/webapp/").toURI().toASCIIString());
			ContainerInitializer initializer = new ContainerInitializer(new JettyJasperInitializer(), null);
			List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>() {
				{
					add(initializer);
				}
			};
			context.setAttribute("org.eclipse.jetty.containerInitializers", initializers);
			context.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
			context.addBean(new ServletContainerInitializersStarter(context), true);

			ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
			ServletHolder indexDefault = new ServletHolder("index", IndexServlet.class);
			ServletHolder counterDefault = new ServletHolder("counter", CounterJspServlet.class);

			context.addServlet(holderDefault, "/");
			context.addServlet(indexDefault, "/index");
			context.addServlet(counterDefault, "/counter");

			server.setHandler(context);

			server.start();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}