package net.lr.osgibp.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service = HelloResource.class)
@JaxrsResource
@Path("/hello")
public class HelloResource {

	@GET
	public String index() {
		return "Hello world";
	}
}
