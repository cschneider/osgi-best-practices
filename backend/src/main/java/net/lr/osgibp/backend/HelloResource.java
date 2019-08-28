package net.lr.osgibp.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = HelloResource.class)
@JaxrsResource
public class HelloResource {

	@GET
	@Path("/hello")
	public String index() {
		return "Hello world";
	}
}
