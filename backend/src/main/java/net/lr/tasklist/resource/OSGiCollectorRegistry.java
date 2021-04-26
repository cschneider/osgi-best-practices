package net.lr.tasklist.resource;

import org.osgi.service.component.annotations.Component;

import io.prometheus.client.CollectorRegistry;

@Component(service = CollectorRegistry.class)
public class OSGiCollectorRegistry extends CollectorRegistry {

}
