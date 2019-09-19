package net.lr.example;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class RepoReader {

	@Reference
	ResourceResolverFactory resoureResolverFactory;
	
	@SuppressWarnings("deprecation")
	@Activate
	public void listResources() throws LoginException {
		ResourceResolver resourceResolver = resoureResolverFactory.getAdministrativeResourceResolver(null);
		Resource res = resourceResolver.getResource("/help");
		for (Resource child : res.getChildren()) {
			try { 
				printResource(child);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		resourceResolver.close();
	}

	private void printResource(Resource res) {
		System.out.println(res.getPath());
		ValueMap values = res.adaptTo(ValueMap.class);
		for (String key : values.keySet()) {
			System.out.println(" " + key + ":" + values.get(key));
		}
	}
}
