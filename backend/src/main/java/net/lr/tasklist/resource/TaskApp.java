package net.lr.tasklist.resource;

import java.util.Set;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

import com.codahale.metrics.MetricRegistry;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import net.lr.tasklist.model.TaskService;

@Component(service = Application.class)
@JaxrsApplicationBase("/tasks")
@JaxrsName(".default")
public class TaskApp extends Application {

    private TaskResource taskResource;
    private CollectorRegistry registry;
    
    @Activate
    public TaskApp(
            @Reference TaskService taskService,
            @Reference CollectorRegistry registry
            ) {
        MetricRegistry metricRegistry = new MetricRegistry();
        taskResource = new TaskResource(taskService, metricRegistry);
        registry.register(new DropwizardExports(metricRegistry));
    }
    
    @Override
    public Set<Object> getSingletons() {
        return Set.of(
                taskResource,
                new MetricsServlet(registry),
                new OpenApiResource());
    }

    
}
