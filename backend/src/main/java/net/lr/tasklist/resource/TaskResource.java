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

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import net.lr.tasklist.model.Task;
import net.lr.tasklist.model.TaskService;

@OpenAPIDefinition(info = @Info(title = "Taskservice example", 
    contact = @Contact(name = "Christian Schneider"), license = @License(name = "Apache License V 2.0")))
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
    @Context
    UriInfo uriInfo;

    private TaskService taskService;
    private MetricRegistry metricRegistry;
    private Counter counter;
    
    public TaskResource(TaskService taskService, MetricRegistry metricRegistry) {
        this.taskService = taskService;
        this.metricRegistry = metricRegistry;
        this.counter = this.metricRegistry.counter("numGets");
    }

    @Operation(summary = "Get single task by id", description =  "Get single task by id")
    @GET
    @Path("{id}")
    public Response getTask(@PathParam("id") Integer id) {
        Task task = taskService.getById(id);
        return task == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(task).build();
    }

    @Operation(description =  "Add task")
    @POST
    public Response addTask(Task task) {
        taskService.addOrUpdate(task);
        URI taskURI = uriInfo.getRequestUriBuilder().path(TaskResource.class, "getTask").build(task.getId());
        return Response.created(taskURI).build();
    }

    @Operation(description =  "Get all tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Task> getTasks() {
        if (this.counter != null) {
            this.counter.inc();
        }
        return taskService.getAll();
    }

    @Operation(description =  "Change task")
    @PUT
    @Path("{id}")
    public void updateTask(@PathParam("id") Integer id, Task task) {
        if (!task.getId().equals(id)) {
            throw new IllegalStateException("Id from path and content must be the same");
        }
        taskService.addOrUpdate(task);
    }
    
    @Operation(description =  "Delete task")
    @DELETE
    @Path("{id}")
    public void deleteTask(@PathParam("id") Integer id) {
        taskService.delete(id);
    }

}
