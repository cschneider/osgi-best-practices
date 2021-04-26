package net.lr.tasklist.resource;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;

@Component(service=Servlet.class)
@HttpWhiteboardServletPattern("/metrics")
public class OSGIMetricsServlet extends MetricsServlet {

    private static final long serialVersionUID = 1L;
    
    @Activate
    public OSGIMetricsServlet(@Reference CollectorRegistry registry) {
        super(registry);
    }

}
