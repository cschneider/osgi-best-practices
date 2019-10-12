package net.lr.tasklist.resource;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JSONRequired;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import net.lr.tasklist.model.Task;
import net.lr.tasklist.model.TaskService;

@Component(service = TaskResource.class)
@JaxrsResource
@Produces(MediaType.APPLICATION_JSON)
@JSONRequired
@Path("tasks")
public class TaskResource {
    @Reference
    TaskService taskService;
    
    @Context
    UriInfo uri;

    @GET
    @Path("{id}")
    public Response getTask(@PathParam("id") Integer id) {
        Task task = taskService.getById(id);
        return task == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(task).build();
    }

    @POST
    public Response addTask(Task task) {
        taskService.addOrUpdate(task);
        URI taskURI = uri.getRequestUriBuilder().path(TaskResource.class, "getTask").build(task.getId());
        return Response.created(taskURI).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Task> getTasks() {
        return taskService.getAll();
    }

    @PUT
    @Path("{id}")
    public void updateTask(@PathParam("id") Integer id, Task task) {
        if (!task.getId().equals(id)) {
            throw new IllegalStateException("Id from path and content must be the same");
        }
        taskService.addOrUpdate(task);
    }
    
    @DELETE
    @Path("{id}")
    public void deleteTask(@PathParam("id") Integer id) {
        taskService.delete(id);
    }

}
