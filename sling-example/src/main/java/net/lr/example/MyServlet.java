package net.lr.example;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;


@SuppressWarnings("serial")
@Component(service = { Servlet.class })
@SlingServletResourceTypes(
	resourceTypes = "sling/markdown/file", 
	extensions = "html",
    methods= "GET")
public class MyServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
    	Resource res = request.getResource();
    	ValueMap values = res.adaptTo(ValueMap.class);
    	
    	response.setContentType("text/html");
    	PrintStream out = new PrintStream(response.getOutputStream());
    	out.println("<html>");
    	out.println("<head></head>");
    	out.println("<body>");
    	out.println("<h1>" + values.get("title") + "</h1>");
    	out.println("<p>" + values.get("jcr:description") + "</p>");
    	out.println("</body>");
    	out.println("</html>");
    }
}

