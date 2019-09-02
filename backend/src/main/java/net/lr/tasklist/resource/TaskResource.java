package net.lr.tasklist.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import org.osgi.util.converter.Converters;

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
        Task task = taskService.getTask(id);
        return task == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(toDTO(task)).build();
    }

	private TaskDTO toDTO(Task task) {
		return Converters.standardConverter().convert(task).sourceAsBean().to(TaskDTO.class);
	}
	
	private Task fromDTO(TaskDTO taskDTO) {
		return Converters.standardConverter().convert(taskDTO).targetAsBean().to(Task.class);
	}

    @POST
    public Response addTask(TaskDTO taskDTO) {
    	Task task = fromDTO(taskDTO);
        taskService.addTask(task);
        URI taskURI = uri.getRequestUriBuilder().path(TaskResource.class, "getTask").build(task.getId());
        return Response.created(taskURI).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskDTO> getTasks() {
        return taskService.getTasks().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @PUT
    @Path("{id}")
    public void updateTask(@PathParam("id") Integer id, TaskDTO taskDTO) {
    	Task task = fromDTO(taskDTO);
        task.setId(id);
        taskService.updateTask(task);
    }
    
    @DELETE
    @Path("{id}")
    public void deleteTask(@PathParam("id") Integer id) {
        taskService.deleteTask(id);
    }

}